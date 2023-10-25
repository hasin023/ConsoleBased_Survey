package UserManager;

import User.User;

public abstract class UserFactory {
    public abstract User createUser(String username, String password);
}
