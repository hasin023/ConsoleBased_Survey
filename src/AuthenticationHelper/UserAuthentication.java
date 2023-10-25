package AuthenticationHelper;

import User.User;

import java.util.List;

public class UserAuthentication {
    public static User authenticateUser(List<User> users, String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
