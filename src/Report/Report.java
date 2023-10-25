package Report;

import User.User;
import Survey.Survey;
import Survey.Question;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<SurveyReport> surveyReports;

    public Report() {
        surveyReports = new ArrayList<>();
    }

    public void generateSurveyReport(Survey survey, List<Question> questions, List<User> respondents) {
        SurveyReport surveyReport = new SurveyReport(survey, questions, respondents);
        surveyReports.add(surveyReport);
    }

    public List<SurveyReport> getSurveyReports() {
        return surveyReports;
    }
}
