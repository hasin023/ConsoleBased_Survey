package User;

import Report.SurveyReport;
import Survey.Survey;
import TextFileHelper.ReportTextFileHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SurveyRespondent extends User{
    private final Set<Survey> completedSurveys;
    private final ReportTextFileHandler reportTextFileHandler;

    public SurveyRespondent(String username, String password) {
        super(username, password);
        this.completedSurveys = new HashSet<>();
        this.reportTextFileHandler = new ReportTextFileHandler("src/TextFileHelper/ReportTextFile.txt");
    }

    @Override
    public void showUserProfile() {
        System.out.println("User Type: Survey Respondent");
        System.out.println("Username: " + getUsername());
        System.out.println("-------------------------------------");
    }

    public boolean hasCompletedSurvey(Survey survey) {
        return completedSurveys.contains(survey);
    }

    public void completeSurvey(Survey survey, List<String> responses) {
        if (!completedSurveys.contains(survey)) {
            survey.close();
            completedSurveys.add(survey);
            SurveyReport surveyReport = new SurveyReport(survey, survey.getQuestions(), List.of(this));
            surveyReport.addUserResponse(this, responses);
            reportTextFileHandler.saveSurveyReport(surveyReport);
        }
    }

    public void viewSurveyStatistics(Survey survey) {
        if (completedSurveys.contains(survey)) {
            SurveyReport surveyReport = reportTextFileHandler.loadSurveyReport(survey);
            System.out.println("Survey Title: " + surveyReport.getSurvey().getTitle());
            System.out.println("Number of Responses: " + surveyReport.getUserResponses().size());
            System.out.println("Questions:");
            for (int i = 0; i < surveyReport.getQuestions().size(); i++) {
                System.out.println("Question " + (i + 1) + ": " + surveyReport.getQuestions().get(i).getQuestion());
                System.out.println("Responses:");
                for (String response : surveyReport.getQuestionResponses(surveyReport.getQuestions().get(i))) {
                    System.out.println(response);
                }
            }
        } else {
            System.out.println("You have not completed this survey.");
        }
    }
}
