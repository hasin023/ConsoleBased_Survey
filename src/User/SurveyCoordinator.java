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


    public Survey createSurvey(Scanner scanner) { }

    private int getLastSurveyIdFromTextFile() {
        List<Survey> allSurveys = surveyTextFileHandler.loadSurveysFromAllUsers();

        if (allSurveys.isEmpty()) {
            return 0;
        } else {
            return allSurveys.getLast().getId();
        }
    }


    public void printAllSurveyTitles() {
        surveys = surveyTextFileHandler.loadUserSpecificSurveys(getUsername());

        if (surveys.isEmpty()) {
            System.out.println("No surveys found for user: " + getUsername());
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Surveys for user " + getUsername() + " =>");

            for (Survey survey : surveys) {
                System.out.println("(ID: " + survey.getId() + ")" + " Survey Title: " + survey.getTitle() + " (Open: " + survey.isOpen() + ")");
            }
            System.out.println("Available Surveys: " + surveys.size());
            System.out.println("-------------------------------------");
        }
    }


    public void openCloseSurvey() {
        printAllSurveyTitles();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the survey you want to open/close:");
        int surveyId = scanner.nextInt();
        String currentUser = getUsername();
        Survey survey = getSurveyByIdAndUser(surveyId, currentUser);

        if (survey != null) {
            if (survey.isOpen()) {
                closeSurvey(survey);
                System.out.println("Survey closed successfully.");
                System.out.println("-------------------------------------");
            } else {
                openSurvey(survey);
                System.out.println("Survey opened successfully.");
                System.out.println("-------------------------------------");
            }
        } else {
            System.out.println("Survey not found or you don't have permission to modify it.");
            System.out.println("-------------------------------------");
        }
    }

    private void openSurvey(Survey survey) {
        survey.setOpen(true);
        surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
    }

    public void closeSurvey(Survey survey) {
        survey.close();
        surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
    }

    private Survey getSurveyByIdAndUser(int surveyId, String currentUser) {
        List<Survey> surveys = surveyTextFileHandler.loadUserSpecificSurveys(currentUser);
        for (Survey survey : surveys) {
            if (survey.getId() == surveyId) {
                return survey;
            }
        }
        return null;
    }


}
