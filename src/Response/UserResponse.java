package Response;

import java.util.List;

public class UserResponse {
    private String respondentUsername;
    private int surveyId;
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
}
