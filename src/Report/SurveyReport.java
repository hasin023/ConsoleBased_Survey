package Report;

import java.util.Map;

public class SurveyReport {
    private String respondentUsername;
    private Map<Integer, String> responses;

    public SurveyReport(String respondentUsername, Map<Integer, String> responses) {
        this.respondentUsername = respondentUsername;
        this.responses = responses;
    }

    public String getRespondentUsername() {
        return respondentUsername;
    }

    public Map<Integer, String> getResponses() {
        return responses;
    }
}
