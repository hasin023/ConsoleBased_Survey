package TextFileHelper;

import Response.UserResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<UserResponse> loadResponsesForSurvey(int surveyId) {
        List<UserResponse> responses = new ArrayList<>();
        String filename = responsesDirectory + File.separator + "Survey_" + surveyId + "_Responses.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            UserResponse currentResponse = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Respondent: ")) {
                    if (currentResponse != null) {
                        responses.add(currentResponse);
                    }
                    String respondentUsername = line.substring(12);
                    currentResponse = new UserResponse(respondentUsername, surveyId, new ArrayList<>());
                } else if (line.startsWith("Responses: ")) {
                    if (currentResponse != null) {
                        String[] responsesArray = line.substring(11).split(",");
                        List<String> userResponses = Arrays.asList(responsesArray);
                        currentResponse.getResponses().addAll(userResponses);
                    }
                }
            }

            if (currentResponse != null) {
                responses.add(currentResponse);
            }
        } catch (IOException e) {
            System.out.println("Error loading survey responses for Survey ID: " + surveyId);
        }

        return responses;
    }
}
