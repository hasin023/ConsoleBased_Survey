package TextFileHelper;

import Response.UserResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SurveyResponseTextFileHandler {
    private final String responsesDirectory;

    public SurveyResponseTextFileHandler(String responsesDirectory) {
        this.responsesDirectory = responsesDirectory;
        File directory = new File(responsesDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveResponse(UserResponse response) {
        String filename = responsesDirectory + File.separator + "Survey_ID_" + response.getSurveyId() + "_Responses.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Respondent: " + response.getRespondentUsername());
            writer.newLine();
            writer.write("Responses: " + String.join(",", response.getResponses()));
            writer.newLine();
            writer.write("------------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving survey response.");
        }
    }
    
}
