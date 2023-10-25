package User;

import Survey.Question;
import Survey.Survey;
import TextFileHelper.SurveyTextFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SurveyCoordinator extends User {
    private List<Survey> surveys;
    private final SurveyTextFileHandler surveyTextFileHandler;

    public SurveyCoordinator(String username, String password) {
        super(username, password);
        this.surveys = new ArrayList<>();
        this.surveyTextFileHandler = new SurveyTextFileHandler("Surveys");
    }

    @Override
    public void showUserProfile() {
        System.out.println("User Type: Survey Coordinator");
        System.out.println("Username: " + getUsername());
        System.out.println("-------------------------------------");
    }


    public Survey createSurvey(Scanner scanner) {
        String createdBy = getUsername();
        System.out.println("Enter the title of the survey:");
        String title = scanner.nextLine();

        System.out.println("Is the survey open (true/false)?");
        boolean isOpen = Boolean.parseBoolean(scanner.nextLine());

        Survey survey = new Survey(title, createdBy);
        survey.setOpen(isOpen);

        while (true) {
            System.out.println("Add a question to the survey (Enter 'q' to finish adding questions):");
            String questionText = scanner.nextLine();
            if (questionText.equalsIgnoreCase("q")) {
                break;
            }

            List<String> options = new ArrayList<>();
            int correctAnswerIndex = 0;

            while (true) {
                System.out.println("Enter an option for the question (Enter 'q' to finish adding options):");
                String option = scanner.nextLine();
                if (option.equalsIgnoreCase("q")) {
                    break;
                }

                options.add(option);

                System.out.println("Is this the correct answer for the question (true/false)?");
                boolean isCorrect = Boolean.parseBoolean(scanner.nextLine());
                if (isCorrect) {
                    correctAnswerIndex = options.size() - 1;
                }
            }

            Question question = new Question(questionText, options, correctAnswerIndex);
            survey.addQuestion(question);
        }
        surveyTextFileHandler.saveSurvey(survey, createdBy);
        surveys.add(survey);
        System.out.println("-------------------------------------");
        System.out.println("Survey created successfully.");
        System.out.println("-------------------------------------");
        return survey;
    }


    public void printAllSurveyTitles() {
        surveys = surveyTextFileHandler.loadSurveys(getUsername());

        if (surveys.isEmpty()) {
            System.out.println("No surveys found for user: " + getUsername());
            System.out.println("-------------------------------------");
        } else {
            int surveyCount = 0;
            for (Survey survey : surveys) {
                System.out.println("Survey Title: " + survey.getTitle());
                surveyCount++;
            }
            System.out.println("Total Surveys for user " + getUsername() + ": " + surveyCount);
            System.out.println("-------------------------------------");
        }
    }


    public void editSurvey(Survey survey, String newTitle) {
        if (survey.isOpen()) {
            survey.setTitle(newTitle);
        } else {
            System.out.println("Cannot edit a closed survey.");
        }
    }

    public void deleteSurvey(Survey survey) {
        if (survey.isOpen()) {
            surveys.remove(survey);
        } else {
            System.out.println("Cannot delete a closed survey.");
        }
    }

    public void closeSurvey(Survey survey) {
        survey.close();
        surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
    }

    public List<Survey> getOpenSurveys() {
        List<Survey> openSurveys = new ArrayList<>();
        for (Survey survey : surveys) {
            if (survey.isOpen()) {
                openSurveys.add(survey);
            }
        }
        return openSurveys;
    }
}
