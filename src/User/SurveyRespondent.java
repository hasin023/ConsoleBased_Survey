// SurveyRespondent.java
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

import Report.SurveyReportGenerator;

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
        if (openSurveys.isEmpty()) {
            System.out.println("No open surveys available.");
            System.out.println("-------------------------------------");
            return null;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Open Surveys:");
        for (int i = 0; i < openSurveys.size(); i++) {
            System.out.println((i + 1) + "." + openSurveys.get(i).getTitle());
        }

        System.out.println("Enter the number of the survey you want to take:");
        int surveyIndex = scanner.nextInt();

        if (surveyIndex >= 1 && surveyIndex <= openSurveys.size()) {
            System.out.println("-------------------------------------");
            return openSurveys.get(surveyIndex - 1);
        } else {
            System.out.println("Invalid survey choice.");
            System.out.println("-------------------------------------");
            return null;
        }
    }

    // public void takeSurvey(Survey survey) {
    // if (survey == null) {
    // System.out.println("No survey selected.");
    // System.out.println("-------------------------------------");
    // return;
    // }

    // if (!survey.isOpen()) {
    // System.out.println("Cannot take a closed survey.");
    // System.out.println("-------------------------------------");
    // return;
    // }

    // Scanner scanner = new Scanner(System.in);

    // if (hasUserResponded(survey.getId(), getUsername())) {
    // System.out.println("You have already responded to this survey. Cannot respond
    // again.");
    // System.out.println("-------------------------------------");
    // return;
    // }

    // List<String> responses = new ArrayList<>();

    // for (int i = 0; i < survey.getQuestions().size(); i++) {
    // System.out.println("Question: " + survey.getQuestions().get(i).getText());
    // for (int j = 0; j < survey.getQuestions().get(i).getOptions().size(); j++) {
    // System.out.println(j + 1 + ". " +
    // survey.getQuestions().get(i).getOptions().get(j));
    // }

    // System.out.println("Enter the option of your answer:");
    // int answerIndex = scanner.nextInt();
    // scanner.nextLine();

    // if (answerIndex >= 1 && answerIndex <=
    // survey.getQuestions().get(i).getOptions().size()) {
    // responses.add(survey.getQuestions().get(i).getOptions().get(answerIndex -
    // 1));
    // } else {
    // System.out.println("Invalid choice. Skipping this question.");
    // }
    // }

    // UserResponse userResponse = new UserResponse(getUsername(), survey.getId(),
    // responses);
    // responseTextFileHandler.saveResponse(userResponse);
    // System.out.println("Survey response saved successfully.");
    // System.out.println("-------------------------------------");
    // }

    public void takeSurvey(Survey survey) {
        if (survey == null) {
            System.out.println("No survey selected.");
            System.out.println("-------------------------------------");
            return;
        }

        if (!survey.isOpen()) {
            System.out.println("Cannot take a closed survey.");
            System.out.println("-------------------------------------");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        if (hasUserResponded(survey.getId(), getUsername())) {
            System.out.println("You have already responded to this survey. Cannot respond again.");
            System.out.println("-------------------------------------");
            return;
        }

        List<String> responses = new ArrayList<>();

        for (int i = 0; i < survey.getQuestions().size(); i++) {
            System.out.println("Question: " + survey.getQuestions().get(i).getText());
            for (int j = 0; j < survey.getQuestions().get(i).getOptions().size(); j++) {
                System.out.println(j + 1 + ". " + survey.getQuestions().get(i).getOptions().get(j));
            }

            System.out.println("Enter your answer (-1 to exit) ->");
            int answerIndex = scanner.nextInt();
            scanner.nextLine();

            if (answerIndex == 0) {
                System.out.println("Question skipped.");
                continue;
            } else if (answerIndex == -1) {
                System.out.println("Survey exited. Responses saved for the answered questions.");
                break;
            } else if (answerIndex >= 1 && answerIndex <= survey.getQuestions().get(i).getOptions().size()) {
                responses.add(survey.getQuestions().get(i).getOptions().get(answerIndex - 1));
            } else {
                System.out.println("Invalid choice. Skipping this question.");
            }
        }

        UserResponse userResponse = new UserResponse(getUsername(), survey.getId(), responses);
        responseTextFileHandler.saveResponse(userResponse);
        System.out.println("Survey response saved successfully.");
        System.out.println("-------------------------------------");
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
            System.out.println("Taking Survey -> ");
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

    @Override
    public void viewSurveyReports() {
        SurveyReportGenerator surveyReportGenerator = new SurveyReportGenerator();
        surveyReportGenerator.generateSurveyReports("SurveyResponses", "SurveyReports");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the survey report ->");
        int surveyId = scanner.nextInt();
        String reportFileName = "Survey_ID_" + surveyId + "_Report.txt";
        String reportFile = "SurveyReports/" + reportFileName;

        surveyReportGenerator.viewSurveyReport(reportFile);
    }
}
