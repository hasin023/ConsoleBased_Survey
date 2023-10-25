package UserManager;

import User.User;
import User.SurveyRespondent;

public class RespondentFactory extends UserFactory{
    @Override
    public User createUser(String username, String password) {
        return new SurveyRespondent(username, password);
    }
}
