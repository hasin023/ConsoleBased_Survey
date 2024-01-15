package User;

import Survey.Survey;

import java.util.HashSet;
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

}
