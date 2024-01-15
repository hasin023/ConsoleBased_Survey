package User;

import Report.SurveyReportGenerator;

import java.util.Scanner;

public abstract class User {
    private final String username;
    private final String password;
    private boolean loggedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean state) {
        loggedIn = state;
    }

    public abstract void showUserProfile();

    public void viewSurveyReports() {
        SurveyReportGenerator surveyReportGenerator = new SurveyReportGenerator();
        surveyReportGenerator.generateSurveyReports("SurveyResponses", "SurveyReports");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the survey you want to view the report for:");
        int surveyId = scanner.nextInt();
        String reportFileName = "Survey_ID_" + surveyId + "_Report.txt";
        String reportFile = "SurveyReports/" + reportFileName;

        surveyReportGenerator.viewSurveyReport(reportFile);
    }
}
