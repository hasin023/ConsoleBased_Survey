package Response;

import java.util.ArrayList;
import java.util.List;

import Survey.Survey;
import TextFileHelper.SurveyTextFileHandler;

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

    public List<Integer> getQuestionIds() {
        List<Integer> questionIds = new ArrayList<>();
        SurveyTextFileHandler surveyTextFileHandler = new SurveyTextFileHandler("Surveys");
        Survey survey = surveyTextFileHandler.getSurveyById(surveyId);

        if (survey != null) {
            for (int i = 0; i < responses.size(); i++) {
                int questionId = survey.getQuestions().get(i).getId();
                questionIds.add(questionId);
            }
        }

        return questionIds;
    }
}
