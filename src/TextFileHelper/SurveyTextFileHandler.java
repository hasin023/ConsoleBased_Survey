package TextFileHelper;

import Survey.Question;
import Survey.Survey;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyTextFileHandler {
    private final String surveysDirectory;

    public SurveyTextFileHandler(String surveysDirectory) {
        this.surveysDirectory = surveysDirectory;
        File directory = new File(surveysDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveSurvey(Survey survey, String createdBy) {
        String filename = surveysDirectory + File.separator + createdBy + "_Surveys.txt";
        boolean fileExists = new File(filename).exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            writer.write("Created By: " + createdBy);
            writer.newLine();
            writer.write("Title: " + survey.getTitle());
            writer.newLine();
            writer.write("Open: " + survey.isOpen());
            writer.newLine();
            for (Question question : survey.getQuestions()) {
                writer.write("Question: " + question.getText());
                writer.newLine();
                writer.write("Options: " + String.join(",", question.getOptions()));
                writer.newLine();
//                writer.write("Correct Answer: " + question.getCorrectAnswerIndex());
//                writer.newLine();
            }
            writer.write("------------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Survey> loadSurveys(String createdBy) {
        List<Survey> loadedSurveys = new ArrayList<>();
        File surveysDirectory = new File(this.surveysDirectory);

        if (surveysDirectory.exists() && surveysDirectory.isDirectory()) {
            File[] surveyFiles = surveysDirectory.listFiles((dir, name) -> name.startsWith(createdBy + "_") && name.endsWith("_Surveys.txt"));
            if (surveyFiles != null) {
                for (File surveyFile : surveyFiles) {
                    Survey currentSurvey = null;
                    try (BufferedReader reader = new BufferedReader(new FileReader(surveyFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Title: ")) {
                                if (currentSurvey != null) {
                                    loadedSurveys.add(currentSurvey);
                                }
                                String title = line.substring(7);
                                currentSurvey = new Survey(title, createdBy);
                            } else if (line.startsWith("Open: ")) {
                                if (currentSurvey != null) {
                                    boolean isOpen = Boolean.parseBoolean(line.substring(6));
                                    currentSurvey.setOpen(isOpen);
                                }
                            } else if (line.startsWith("Question: ")) {
                                String questionText = line.substring(10);
                                List<String> options = new ArrayList<>();
//                                int correctAnswerIndex = 0;
                                while ((line = reader.readLine()) != null && !line.startsWith("Options: ")) {
                                    if (line.startsWith("Options: ")) {
                                        String[] optionsArray = line.substring(9).split(",");
                                        options.addAll(List.of(optionsArray));
                                    }
                                }
                                Question question = new Question(questionText, options);
                                assert currentSurvey != null;
                                currentSurvey.addQuestion(question);
                            }
                        }
                        if (currentSurvey != null) {
                            loadedSurveys.add(currentSurvey);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return loadedSurveys;
    }


}
