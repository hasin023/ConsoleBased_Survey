package Response;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private final String respondentUsername;
    private final int surveyId;
    private List<String> responses;

    public UserResponse(String respondentUsername, int surveyId, List<String> responses) {
        this.respondentUsername = respondentUsername;
        this.surveyId = surveyId;
        this.responses = responses;
    }

    public String getRespondentUsername() {
        return respondentUsername;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public List<String> getResponses() {
        return responses;
    }

    public int getUserId() {
        try {
            return Integer.parseInt(respondentUsername.substring(respondentUsername.length() - 1));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing user id from username.");
            return -1;
        }
    }

    public List<Integer> getQuestionIds() {
        List<Integer> questionIds = new ArrayList<>();
        for (int i = 1; i <= responses.size(); i++) {
            questionIds.add(i);
        }
        return questionIds;
    }
}
