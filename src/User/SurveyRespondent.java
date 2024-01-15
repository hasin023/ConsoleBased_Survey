package User;

import Response.UserResponse;
import Survey.Survey;
import TextFileHelper.SurveyResponseTextFileHandler;
import TextFileHelper.SurveyTextFileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SurveyRespondent extends User {
    private final SurveyResponseTextFileHandler responseTextFileHandler;

    public SurveyRespondent(String username, String password) {
        super(username, password);
        this.responseTextFileHandler = new SurveyResponseTextFileHandler("SurveyResponses");
    }

    @Override
    public void showUserProfile() {
        System.out.println("User Type: Survey Respondent");
        System.out.println("Username: " + getUsername());
        System.out.println("-------------------------------------");
    }


    public Survey chooseSurvey(List<Survey> openSurveys) {

    }

    public void takeSurvey(Survey survey) {

    }

    private boolean hasUserResponded(int surveyId, String username) {
        String responseFileName = "SurveyResponses" + File.separator + "Survey_ID_" + surveyId + "_Responses.txt";
        try {
            List<String> responseLines = Files.readAllLines(Paths.get(responseFileName));
            for (String line : responseLines) {
                if (line.startsWith("Respondent: " + username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking user response for " + responseFileName);
        }
        return false;
    }

    public void printAllOpenSurveys() {
        SurveyTextFileHandler surveyTextFileHandler = new SurveyTextFileHandler("Surveys");
        List<Survey> surveys = surveyTextFileHandler.loadOpenSurveysAllUsers();

        for (Survey survey : surveys) {
            if (survey.isOpen()) {
                System.out.println("Survey ID: " + survey.getId());
                System.out.println("Survey Title: " + survey.getTitle());
                System.out.println("Survey Created By: " + survey.getCreatedBy());
                System.out.println("-------------------------------------");
            }
        }
    }

    public List<Survey> getOpenSurveys() {
        SurveyTextFileHandler surveyTextFileHandler = new SurveyTextFileHandler("Surveys");
        return surveyTextFileHandler.loadOpenSurveysAllUsers();
    }
}
