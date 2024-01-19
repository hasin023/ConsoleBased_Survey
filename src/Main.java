import Tasks.Tasks;
import User.SurveyCoordinator;
import User.SurveyRespondent;
import UserManager.CoordinatorFactory;
import UserManager.RespondentFactory;
import UserManager.UserAccountManager;
import UserManager.UserFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Tasks tasks = new Tasks();

        UserFactory coordinatorFactory = new CoordinatorFactory();
        UserFactory respondentFactory = new RespondentFactory();

        UserAccountManager coordinatorAccountManager = new UserAccountManager("coordinatorUsers.txt",
                coordinatorFactory);
        UserAccountManager respondentAccountManager = new UserAccountManager("respondentUsers.txt", respondentFactory);

        boolean session = true;
        Scanner userTypeScanner = new Scanner(System.in);
        Scanner loginRegister = new Scanner(System.in);
        Scanner optionScanner = new Scanner(System.in);

        while (session) {

            tasks.showUserTypes();
            int userTypeChoice = userTypeScanner.nextInt();

            if (userTypeChoice == 1) {

                tasks.showOperations();
                int loginRegisterChoice = loginRegister.nextInt();

                if (loginRegisterChoice == 1) {

                    SurveyCoordinator loggedInUser = (SurveyCoordinator) tasks.loginTask(userTypeScanner,
                            coordinatorAccountManager);
                    tasks.handleCoordinateOperations(loggedInUser, optionScanner, coordinatorAccountManager);

                } else if (loginRegisterChoice == 2) {

                    SurveyCoordinator loggedInUser = (SurveyCoordinator) tasks.registerTask(userTypeScanner,
                            coordinatorAccountManager, coordinatorFactory);
                    tasks.handleCoordinateOperations(loggedInUser, optionScanner, coordinatorAccountManager);

                } else if (loginRegisterChoice == 3) {
                    System.out.println("Exiting...");
                    session = false;
                }

            } else if (userTypeChoice == 2) {

                tasks.showOperations();
                int loginRegisterChoice = loginRegister.nextInt();

                if (loginRegisterChoice == 1) {

                    SurveyRespondent loggedInUser = (SurveyRespondent) tasks.loginTask(userTypeScanner,
                            respondentAccountManager);
                    tasks.handleRespondentOperations(loggedInUser, optionScanner, respondentAccountManager);

                } else if (loginRegisterChoice == 2) {

                    SurveyRespondent loggedInUser = (SurveyRespondent) tasks.registerTask(userTypeScanner,
                            respondentAccountManager, respondentFactory);
                    tasks.handleRespondentOperations(loggedInUser, optionScanner, respondentAccountManager);

                } else if (loginRegisterChoice == 3) {
                    System.out.println("Exiting...");
                    session = false;
                }

            } else {
                System.out.println("Exiting...");
                session = false;
            }

        }

    }
}