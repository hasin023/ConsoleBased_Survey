package Report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SurveyReportGenerator {

    public void generateSurveyReports(String responsesDirectory, String reportsDirectory) {
        try {
            Path responsesPath = Paths.get(responsesDirectory);
            Path reportsPath = Paths.get(reportsDirectory);

            Files.createDirectories(reportsPath);

            Files.list(responsesPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("Survey_ID_"))
                    .forEach(responseFile -> {
                        String reportFileName = responseFile.getFileName().toString().replace("Responses", "Report");
                        generateSurveyReport(responseFile.toString(), reportsPath.resolve(reportFileName).toString());
                    });

        } catch (IOException e) {
            System.out.println("Error generating reports");
        }
    }

    private void generateSurveyReport(String responseFile, String reportFile) {
        try {
            List<String> responses = Files.readAllLines(Paths.get(responseFile));
            Set<String> distinctUsers = new HashSet<>();
            Map<String, Integer> optionCount = new HashMap<>();
            Map<String, Map<String, Integer>> questionOptionCount = new HashMap<>();

            for (String response : responses) {
                if (response.startsWith("Respondent:")) {
                    String respondent = response.split(":")[1].trim();
                    distinctUsers.add(respondent);
                } else if (response.startsWith("Question-")) {
                    String[] parts = response.split(":");
                    String questionId = parts[0].trim();
                    String option = parts[1].trim();

                    optionCount.put(option, optionCount.getOrDefault(option, 0) + 1);

                    questionOptionCount
                            .computeIfAbsent(questionId, k -> new HashMap<>())
                            .put(option, questionOptionCount.getOrDefault(questionId, new HashMap<>())
                                    .getOrDefault(option, 0) + 1);
                }
            }

            double totalOptions = optionCount.size();

            Map<String, Double> optionPercentages = calculateOptionPercentages(optionCount, totalOptions);
            Map<String, Map<String, Double>> questionOptionPercentages = new HashMap<>();

            for (Map.Entry<String, Map<String, Integer>> questionEntry : questionOptionCount.entrySet()) {
                String questionId = questionEntry.getKey();
                Map<String, Integer> questionOptionCountMap = questionEntry.getValue();
                double totalQuestionOptions = questionOptionCountMap.values().stream().mapToInt(Integer::intValue)
                        .sum();
                questionOptionPercentages.put(questionId,
                        calculateOptionPercentages(questionOptionCountMap, totalQuestionOptions));
            }

            generateDetailedReport(distinctUsers.size(), questionOptionPercentages, reportFile);

        } catch (IOException e) {
            System.out.println("Error generating report for " + responseFile);
        }
    }

    private void generateDetailedReport(int distinctUsers, Map<String, Map<String, Double>> questionOptionPercentages,
            String reportFile) {
        try {
            StringBuilder reportContent = new StringBuilder();
            TreeMap<String, Map<String, Double>> sortedReport = new TreeMap<>(questionOptionPercentages);

            reportContent.append("Distinct Users: ").append(distinctUsers).append("\n");

            for (Map.Entry<String, Map<String, Double>> entry : sortedReport.entrySet()) {
                for (Map.Entry<String, Double> optionEntry : entry.getValue().entrySet()) {
                    String questionId = entry.getKey();
                    String option = optionEntry.getKey();
                    double percentage = optionEntry.getValue();

                    reportContent.append(questionId).append(", Option (").append(option)
                            .append(") : ").append(String.format("%.2f", percentage)).append("%\n");
                }
            }

            reportContent.append("--------------------------------------------\n");

            Files.write(Paths.get(reportFile), reportContent.toString().getBytes());

        } catch (IOException e) {
            System.out.println("Error generating report for " + reportFile);
        }
    }

    private Map<String, Double> calculateOptionPercentages(Map<String, Integer> optionCount, double totalOptions) {
        Map<String, Double> optionPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : optionCount.entrySet()) {
            double percentage = (entry.getValue() / totalOptions) * 100;
            optionPercentages.put(entry.getKey(), percentage);
        }

        double sum = optionPercentages.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sum > 100) {
            double scale = 100 / sum;
            for (Map.Entry<String, Double> entry : optionPercentages.entrySet()) {
                entry.setValue(entry.getValue() * scale);
            }
        }

        return optionPercentages;
    }

    public void viewSurveyReport(String reportFile) {
        try {
            List<String> reportLines = Files.readAllLines(Paths.get(reportFile));

            TreeMap<Integer, List<String>> sortedReport = new TreeMap<>();
            boolean isDistinctUsersPrinted = false;

            for (int i = 0; i < reportLines.size(); i++) {
                String line = reportLines.get(i);

                if (line.startsWith("Distinct Users")) {
                    if (!isDistinctUsersPrinted) {
                        System.out.println(line);
                        isDistinctUsersPrinted = true;
                    }
                } else if (line.startsWith("Question")) {
                    System.out.println(line);
                } else if (line.startsWith("Option")) {
                    String[] parts = line.split(":");
                    String[] questionParts = parts[0].split(", ");
                    int questionId = Integer.parseInt(questionParts[1].trim());

                    sortedReport.computeIfAbsent(questionId, k -> new ArrayList<>()).add(line);
                }
            }

            for (Map.Entry<Integer, List<String>> entry : sortedReport.entrySet()) {
                System.out.println("Question-" + entry.getKey() + ":");
                entry.getValue().forEach(System.out::println);
            }

            System.out.println("--------------------------------------------");

        } catch (IOException e) {
            System.out.println("No responses recieved for this survey yet.");
        }
    }

}
