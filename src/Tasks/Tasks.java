package Tasks;

import Survey.Survey;
import TextFileHelper.UserFileHandler;
import User.SurveyCoordinator;
import User.SurveyRespondent;
import User.User;
import UserManager.UserAccountManager;
import UserManager.UserFactory;

import java.util.List;
import java.util.Scanner;

import AuthenticationHelper.PasswordHashing;
import AuthenticationHelper.PasswordScanner;

public class Tasks {
    public void showUserTypes() {
        System.out.println("Welcome to SurveyFeedback!");
        System.out.println("######################################");
        System.out.println("Select user type:");
        System.out.println("1. Survey Coordinator");
        System.out.println("2. Survey Respondent");
        System.out.println("3. Exit");
        System.out.println("######################################");
        System.out.println("Enter choice:");
    }

    public void showOperations() {
        System.out.println("######################################");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("######################################");
        System.out.println("Enter choice:");
    }

    public void showCoordinatorOperations() {
        System.out.println("######################################");
        System.out.println("Please select an option:");
        System.out.println("1. Create survey");
        System.out.println("2. View surveys");
        System.out.println("3. Open/Close survey");
        System.out.println("4. Edit survey");
        System.out.println("5. View reports");
        System.out.println("6. Delete survey");
        System.out.println("7. View profile");
        System.out.println("8. Logout");
        System.out.println("######################################");
        System.out.println("Enter choice:");
    }

    public void showRespondentOperations() {
        System.out.println("######################################");
        System.out.println("Please select an option:");
        System.out.println("1. View surveys");
        System.out.println("2. Take survey");
        System.out.println("3. View reports");
        System.out.println("4. View profile");
        System.out.println("5. Logout");
        System.out.println("######################################");
        System.out.println("Enter choice:");
    }

    public User registerTask(Scanner scanner, UserAccountManager userAccountManager, UserFactory userFactory) {
        System.out.println("Enter username:");
        String username = scanner.next();
        String password = PasswordScanner.readPassword();

        String hashedPassword = PasswordHashing.hashPassword(password);

        UserFileHandler respondentUserFileHandler = new UserFileHandler("respondentUsers.txt");
        List<User> users = respondentUserFileHandler.readUsersFromFile(userFactory);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists, please try again.");
                System.out.println("-------------------------------------");
                return null;
            }
        }

        UserFileHandler coordinateUserFileHandler = new UserFileHandler("coordinatorUsers.txt");
        List<User> users2 = coordinateUserFileHandler.readUsersFromFile(userFactory);
        for (User user : users2) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists, please try again.");
                System.out.println("-------------------------------------");
                return null;
            }
        }

        User loggedInUser = userAccountManager.registerUser(userFactory.createUser(username, hashedPassword));

        if (loggedInUser != null) {
            loggedInUser.setLoggedIn(true);
            System.out.println("Registration successful, WELCOME " + loggedInUser.getUsername() + "!");
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Registration failed.");
            System.out.println("-------------------------------------");
        }

        return loggedInUser;
    }

    public User loginTask(Scanner scanner, UserAccountManager userAccountManager) {
        if (scanner == null || userAccountManager == null) {
            System.out.println("Error: Scanner or UserAccountManager is null.");
            System.out.println("-------------------------------------");
            return null;
        }

        System.out.println("Enter username:");
        String username = scanner.next();
        String password = PasswordScanner.readPassword();

        String hashedPassword = PasswordHashing.hashPassword(password);

        User loggedInUser = userAccountManager.login(username, hashedPassword);

        if (loggedInUser != null && PasswordHashing.checkPassword(password, loggedInUser.getPassword())) {
            loggedInUser.setLoggedIn(true);
            System.out.println("Login successful, WELCOME " + loggedInUser.getUsername() + "!");
            System.out.println("-------------------------------------");
            return loggedInUser;
        }

        System.out.println("Login failed.");
        System.out.println("-------------------------------------");
        return null;
    }

    public void handleCoordinateOperations(SurveyCoordinator loggedInUser, Scanner optionScanner,
            UserAccountManager userManager) {
        while (loggedInUser.isLoggedIn()) {
            this.showCoordinatorOperations();
            int optionChoice = optionScanner.nextInt();

            switch (optionChoice) {
                case 1:
                    Survey survey = loggedInUser.createSurvey();
                    break;
                case 2:
                    loggedInUser.printAllSurveyTitles();
                    break;
                case 3:
                    loggedInUser.openCloseSurvey();
                    break;
                case 4:
                    loggedInUser.chooseSurvey();
                    break;
                case 5:
                    loggedInUser.viewSurveyReports();
                    break;
                case 6:
                    loggedInUser.deleteSurvey();
                    break;
                case 7:
                    loggedInUser.showUserProfile();
                    break;
                case 8:
                    userManager.logout(loggedInUser);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    public void handleRespondentOperations(SurveyRespondent loggedInUser, Scanner optionScanner,
            UserAccountManager userManager) {
        while (loggedInUser.isLoggedIn()) {
            this.showRespondentOperations();
            int optionChoice = optionScanner.nextInt();

            switch (optionChoice) {
                case 1:
                    loggedInUser.printAllOpenSurveys();
                    break;
                case 2:
                    List<Survey> openSurveys = loggedInUser.getOpenSurveys();
                    Survey selectedSurvey = loggedInUser.chooseSurvey(openSurveys);
                    loggedInUser.takeSurvey(selectedSurvey);
                    break;
                case 3:
                    loggedInUser.viewSurveyReports();
                    break;
                case 4:
                    loggedInUser.showUserProfile();
                    break;
                case 5:
                    userManager.logout(loggedInUser);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
}
