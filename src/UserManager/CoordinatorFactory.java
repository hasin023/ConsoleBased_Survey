package UserManager;

import User.User;
import User.SurveyCoordinator;

public class CoordinatorFactory extends UserFactory{
    @Override
    public User createUser(String username, String password) {
        return new SurveyCoordinator(username, password);
    }
}
