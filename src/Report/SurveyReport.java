package Report;

import Survey.Question;
import Survey.Survey;
import User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyReport {
    private final Survey survey;
    private final List<Question> questions;
    private final Map<User, List<String>> userResponses;

    public SurveyReport(Survey survey, List<Question> questions, List<User> respondents) {
        this.survey = survey;
        this.questions = questions;
        userResponses = new HashMap<>();
        for (User user : respondents) {
            userResponses.put(user, new ArrayList<>());
        }
    }

    public Survey getSurvey() {
        return survey;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Map<User, List<String>> getUserResponses() {
        return userResponses;
    }

    public void addUserResponse(User user, List<String> responses) {
        userResponses.get(user).addAll(responses);
    }

    public List<String> getQuestionResponses(Question question) {
        List<String> responses = new ArrayList<>();
        for (User user : userResponses.keySet()) {
            List<String> userResponse = userResponses.get(user);
            int questionIndex = questions.indexOf(question);
            if (questionIndex >= 0 && questionIndex < userResponse.size()) {
                responses.add(userResponse.get(questionIndex));
            } else {
                responses.add("No response");
            }
        }
        return responses;
    }
}
