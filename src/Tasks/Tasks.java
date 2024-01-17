package Tasks;

import Survey.Survey;
import User.SurveyCoordinator;
import User.SurveyRespondent;
import User.User;
import UserManager.UserAccountManager;
import UserManager.UserFactory;

import java.util.Scanner;

public class Tasks {

    public void showOperations() {
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("-------------------------------------");
        System.out.println("Enter choice:");
    }

    public void showUserTypes() {
        System.out.println("Welcome to the Survey Application!");
        System.out.println("-------------------------------------");
        System.out.println("Select user type:");
        System.out.println("1. Survey Coordinator");
        System.out.println("2. Survey Respondent");
        System.out.println("3. Exit");
        System.out.println("-------------------------------------");
        System.out.println("Enter choice:");
    }

    public void showCoordinatorOperations() {
        System.out.println("Please select an option:");
        System.out.println("1. Create survey");
        System.out.println("2. View surveys");
        System.out.println("3. Edit survey");
        System.out.println("4. Delete survey");
        System.out.println("5. View profile");
        System.out.println("6. Logout");
        System.out.println("-------------------------------------");
        System.out.println("Enter choice:");
    }

    public void showRespondentOperations() {
        System.out.println("Please select an option:");
        System.out.println("1. View survey");
        System.out.println("2. Take survey");
        System.out.println("3. View profile");
        System.out.println("4. Logout");
        System.out.println("-------------------------------------");
        System.out.println("Enter choice:");
    }

    public User registerTask(Scanner scanner, UserAccountManager userAccountManager, UserFactory userFactory) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        User loggedInUser = userAccountManager.registerUser(userFactory.createUser(username, password));

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
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        User loggedInUser = userAccountManager.login(username, password);

        if (loggedInUser != null) {
            loggedInUser.setLoggedIn(true);
            System.out.println("Login successful, WELCOME " + loggedInUser.getUsername() + "!");
            System.out.println("-------------------------------------");

        } else {
            System.out.println("Login failed.");
            System.out.println("-------------------------------------");
        }

        return loggedInUser;
    }


    public void handleCoordinateOperations(SurveyCoordinator loggedInUser, Scanner optionScanner, UserAccountManager userManager) {
        while (loggedInUser.isLoggedIn()) {
            this.showCoordinatorOperations();
            int optionChoice = optionScanner.nextInt();

            switch (optionChoice) {
                case 1:
                    Scanner surveyScanner = new Scanner(System.in);
                    Survey survey = loggedInUser.createSurvey(surveyScanner);
                    break;
                case 2:
                    loggedInUser.printAllSurveyTitles();
                    break;
                case 3:
                    // Edit survey
                    break;
                case 4:
                    // Delete survey
                    break;
                case 5:
                    loggedInUser.showUserProfile();
                    break;
                case 6:
                    userManager.logout(loggedInUser);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }


    public void handleRespondentOperations(SurveyRespondent loggedInUser, Scanner optionScanner, UserAccountManager userManager) {
        while (loggedInUser.isLoggedIn()) {
            this.showRespondentOperations();
            int optionChoice = optionScanner.nextInt();

            switch (optionChoice) {
                case 1:
                    // View survey
                    break;
                case 2:
                    // Take survey
                    break;
                case 3:
                    loggedInUser.showUserProfile();
                    break;
                case 4:
                    userManager.logout(loggedInUser);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
}
