package TextFileHelper;

import Response.UserResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String filename = responsesDirectory + File.separator + "Survey_ID_" + response.getSurveyId()
                + "_Responses.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            List<String> responses = response.getResponses();
            List<Integer> questionIds = response.getQuestionIds();
            Map<Integer, String> questionIdToResponseMap = new HashMap<>();

            for (int i = 0; i < questionIds.size(); i++) {
                int questionId = questionIds.get(i) + 1;
                String responseText = (i < responses.size()) ? responses.get(i) : "Skipped";
                questionIdToResponseMap.put(questionId, responseText);
            }

            writer.write("Respondent: " + response.getRespondentUsername());
            writer.newLine();
            for (int questionId : questionIdToResponseMap.keySet()) {
                writer.write("Question-" + questionId + ": " + questionIdToResponseMap.get(questionId));
                writer.newLine();
            }

            writer.write("------------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving survey response.");
        }
    }

}
