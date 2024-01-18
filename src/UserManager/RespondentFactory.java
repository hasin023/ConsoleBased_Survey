package UserManager;

import User.SurveyRespondent;
import User.User;

public class RespondentFactory extends UserFactory{
    @Override
    public User createUser(String username, String password) {
        return new SurveyRespondent(username, password);
    }
}
