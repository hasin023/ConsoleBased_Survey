package TextFileHelper;

import Report.SurveyReport;
import User.User;
import Survey.Survey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportTextFileHandler {
    private final String reportsDirectory;

    public ReportTextFileHandler(String reportsDirectory) {
        this.reportsDirectory = reportsDirectory;
    }

    public SurveyReport loadSurveyReport(Survey survey) {
        String filename = reportsDirectory + File.separator + survey.getTitle() + "_Report.txt";
        return null;
    }

    public void saveSurveyReport(SurveyReport surveyReport) {
        String filename = reportsDirectory + File.separator + surveyReport.getSurvey().getTitle() + "_Report.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Survey Title: " + surveyReport.getSurvey().getTitle());
            writer.newLine();

            for (User user : surveyReport.getUserResponses().keySet()) {
                writer.write("User: " + user.getUsername());
                writer.newLine();
                writer.write("Responses: " + String.join(", ", surveyReport.getUserResponses().get(user)));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
