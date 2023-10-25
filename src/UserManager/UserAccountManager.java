package UserManager;

import AuthenticationHelper.UserAuthentication;
import TextFileHelper.UserFileHandler;
import User.User;

import java.util.List;

public class UserAccountManager {
    private final UserFileHandler fileHandler;
    private final UserFactory userFactory;
    private final List<User> users;

    public UserAccountManager(String fileName, UserFactory userFactory) {
        this.fileHandler = new UserFileHandler(fileName);
        if (fileHandler.fileExists() && fileHandler.fileHasContent()) {
            this.users = fileHandler.readUsersFromFile(userFactory);
        } else {
            this.users = fileHandler.createFile();
        }
        this.userFactory = userFactory;
    }

    public User registerUser(User user) {
        User newUser = userFactory.createUser(user.getUsername(), user.getPassword());
        users.add(user);
        fileHandler.writeUsersToFile(users);
        return newUser;
    }

    public User login(String username, String password) {
        return UserAuthentication.authenticateUser(users, username, password);
    }

    public void logout(User user) {
        user.setLoggedIn(false);
    }
}
