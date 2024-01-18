package TextFileHelper;

import Survey.Question;
import Survey.Survey;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SurveyTextFileHandler {
    private final String surveysDirectory;
    private List<Survey> loadedSurveys;

    public SurveyTextFileHandler(String surveysDirectory) {
        this.surveysDirectory = surveysDirectory;
        this.loadedSurveys = new ArrayList<>();
        File directory = new File(surveysDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveSurvey(Survey survey, String createdBy) {
        String filename = surveysDirectory + File.separator + "All_Surveys.txt";

        List<Survey> existingSurveys = loadSurveysFromAllUsers();
        boolean surveyExists = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Survey existingSurvey : existingSurveys) {
                if (existingSurvey.getId() == survey.getId() && existingSurvey.getCreatedBy().equals(createdBy)) {
                    writeSurveyToFile(writer, survey, createdBy);
                    surveyExists = true;
                } else {
                    writeSurveyToFile(writer, existingSurvey, existingSurvey.getCreatedBy());
                }
            }

            if (!surveyExists) {
                writeSurveyToFile(writer, survey, createdBy);
            }

            System.out.println("Survey saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving survey.");
        }
    }

    private void writeSurveyToFile(BufferedWriter writer, Survey survey, String createdBy) throws IOException {
        writer.write("ID: " + survey.getId() + "-" + createdBy);
        writer.newLine();
        writer.write("Title: " + survey.getTitle());
        writer.newLine();
        writer.write("Open: " + survey.isOpen());
        writer.newLine();

        for (Question question : survey.getQuestions()) {
            writer.write("Question-" + question.getId() + ": " + question.getText());
            writer.newLine();
            writer.write("Options: " + String.join(",", question.getOptions()));
            writer.newLine();
        }

        writer.write("------------------------------");
        writer.newLine();
    }

    public List<Survey> loadUserSpecificSurveys(String currentUser) {
        List<Survey> userSurveys = new ArrayList<>();
        File surveysDirectory = new File(this.surveysDirectory);
        String allSurveysFileName = surveysDirectory + File.separator + "All_Surveys.txt";

        if (surveysDirectory.exists() && surveysDirectory.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(allSurveysFileName))) {
                Survey currentSurvey = null;
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID: ")) {
                        if (currentSurvey != null) {
                            userSurveys.add(currentSurvey);
                        }
                        String[] parts = line.substring(4).split("-");
                        int id = Integer.parseInt(parts[0]);
                        String createdBy = parts[1];
                        line = reader.readLine();
                        String title = line.substring(7);
                        currentSurvey = new Survey(id, title, createdBy);
                    } else if (line.startsWith("Open: ")) {
                        if (currentSurvey != null) {
                            boolean isOpen = Boolean.parseBoolean(line.substring(6));
                            currentSurvey.setOpen(isOpen);
                        }
                    } else if (line.startsWith("Question-")) {
                        int questionId = Integer.parseInt(line.substring(9, line.indexOf(':')));
                        String questionText = line.substring(line.indexOf(':') + 2);
                        line = reader.readLine();
                        String[] optionsArray = line.substring(9).split(",");
                        List<String> options = Arrays.asList(optionsArray);
                        Question question = new Question(questionId, questionText, options);
                        assert currentSurvey != null;
                        currentSurvey.addQuestion(question);
                    } else if (line.startsWith("----------------------------")) {
                        if (currentSurvey != null) {
                            userSurveys.add(currentSurvey);
                            currentSurvey = null;
                        }
                    }
                }

                if (currentSurvey != null) {
                    userSurveys.add(currentSurvey);
                }

            } catch (IOException e) {
                System.out.println("Error loading surveys for " + currentUser);
            }
        }

        userSurveys = userSurveys.stream()
                .filter(survey -> survey.getCreatedBy().equals(currentUser))
                .collect(Collectors.toList());

        return userSurveys;
    }

    public void deleteSurvey(Survey survey, String createdBy) {
        String filename = surveysDirectory + File.separator + "All_Surveys.txt";

        List<Survey> existingSurveys = loadSurveysFromAllUsers();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Survey existingSurvey : existingSurveys) {
                if (existingSurvey.getId() == survey.getId() && existingSurvey.getCreatedBy().equals(createdBy)) {
                    continue;
                }
                if (existingSurvey.getCreatedBy().equals(createdBy)) {
                    writeSurveyToFile(writer, existingSurvey, createdBy);
                } else {
                    writeSurveyToFile(writer, existingSurvey, existingSurvey.getCreatedBy());
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting survey.");
        }
    }

    public List<Survey> loadSurveysFromAllUsers() {
        List<Survey> allSurveys = new ArrayList<>();
        File surveysDirectory = new File(this.surveysDirectory);
        String allSurveysFileName = surveysDirectory + File.separator + "All_Surveys.txt";

        if (surveysDirectory.exists() && surveysDirectory.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(allSurveysFileName))) {
                Survey currentSurvey = null;
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID: ")) {
                        if (currentSurvey != null) {
                            allSurveys.add(currentSurvey);
                        }
                        String[] parts = line.substring(4).split("-");
                        int id = Integer.parseInt(parts[0]);
                        String createdBy = parts[1];
                        line = reader.readLine();
                        String title = line.substring(7);
                        currentSurvey = new Survey(id, title, createdBy);
                    } else if (line.startsWith("Open: ")) {
                        if (currentSurvey != null) {
                            boolean isOpen = Boolean.parseBoolean(line.substring(6));
                            currentSurvey.setOpen(isOpen);
                        }
                    } else if (line.startsWith("Question-")) {
                        int questionId = Integer.parseInt(line.substring(9, line.indexOf(':')));
                        String questionText = line.substring(line.indexOf(':') + 2);
                        line = reader.readLine();
                        String[] optionsArray = line.substring(9).split(",");
                        List<String> options = Arrays.asList(optionsArray);
                        Question question = new Question(questionId, questionText, options);
                        assert currentSurvey != null;
                        currentSurvey.addQuestion(question);
                    } else if (line.startsWith("----------------------------")) {
                        if (currentSurvey != null) {
                            allSurveys.add(currentSurvey);
                            currentSurvey = null;
                        }
                    }
                }

                if (currentSurvey != null) {
                    allSurveys.add(currentSurvey);
                }

            } catch (IOException e) {
                System.out.println("Error loading surveys from " + allSurveysFileName);
            }
        }

        return allSurveys;
    }

    public List<Survey> loadOpenSurveysAllUsers() {
        List<Survey> allSurveys = loadSurveysFromAllUsers();

        return allSurveys.stream()
                .filter(Survey::isOpen)
                .collect(Collectors.toList());
    }

    public Survey getSurveyById(int surveyId) {
        List<Survey> allSurveys = loadSurveysFromAllUsers();

        for (Survey survey : allSurveys) {
            if (survey.getId() == surveyId) {
                return survey;
            }
        }

        return null;
    }

}
