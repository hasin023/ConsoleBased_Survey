package User;

import Survey.Question;
import Survey.Survey;
import TextFileHelper.SurveyTextFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Report.SurveyReportGenerator;

public class SurveyCoordinator extends User {
    private List<Survey> surveys;
    private final SurveyTextFileHandler surveyTextFileHandler;

    public SurveyCoordinator(String username, String password) {
        super(username, password);
        this.surveys = new ArrayList<>();
        this.surveyTextFileHandler = new SurveyTextFileHandler("Surveys");
    }

    @Override
    public void showUserProfile() {
        System.out.println("User Type: Survey Coordinator");
        System.out.println("Username: " + getUsername());
        System.out.println("-------------------------------------");
    }

    public Survey createSurvey() {
        Scanner scanner = new Scanner(System.in);

        int id = getLastSurveyIdFromTextFile() + 1;
        String createdBy = getUsername();

        System.out.println("Enter the title of the survey ->");
        String title = scanner.nextLine();

        Survey survey = new Survey(id, title, createdBy);
        survey.setOpen(false);

        while (true) {
            survey.addQuestion();

            System.out.println("Do you want to add another question? (y/n) ->");
            String addAnother = scanner.nextLine().trim().toLowerCase();

            if (!"y".equals(addAnother)) {
                break;
            }
        }

        surveyTextFileHandler.saveSurvey(survey, createdBy);
        surveys.add(survey);

        System.out.println("-------------------------------------");
        System.out.println("Survey created successfully.");
        System.out.println("-------------------------------------");

        return survey;

    }

    private int getLastSurveyIdFromTextFile() {
        List<Survey> allSurveys = surveyTextFileHandler.loadSurveysFromAllUsers();

        if (allSurveys.isEmpty()) {
            return 0;
        } else {
            return allSurveys.getLast().getId();
        }
    }

    public void printAllSurveyTitles() {
        surveys = surveyTextFileHandler.loadUserSpecificSurveys(getUsername());

        if (surveys.isEmpty()) {
            System.out.println("No surveys found for user: " + getUsername());
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Surveys for user " + getUsername() + " =>");

            for (Survey survey : surveys) {
                System.out.println("(ID: " + survey.getId() + ")" + " Survey Title: " + survey.getTitle() + " (Status: "
                        + survey.getStatus() + ")");
            }
            System.out.println("Available Surveys: " + surveys.size());
            System.out.println("-------------------------------------");
        }
    }

    public void chooseSurvey() {
        printAllSurveyTitles();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the survey you want to choose:");
        int surveyId = scanner.nextInt();
        Survey survey = getSurveyById(surveyId);
        if (survey != null) {
            System.out.println("Survey chosen successfully.");
            System.out.println("-------------------------------------");
            handleSurveyOperations(survey, scanner);
        } else {
            System.out.println("Survey not found.");
            System.out.println("-------------------------------------");
        }
    }

    private Survey getSurveyById(int surveyId) {
        for (Survey survey : surveys) {
            if (survey.getId() == surveyId) {
                return survey;
            }
        }
        return null;
    }

    private void showSurveyOperations() {
        System.out.println("######################################");
        System.out.println("1. View survey");
        System.out.println("2. Edit Title");
        System.out.println("3. Add question");
        System.out.println("4. Edit question");
        System.out.println("5. Delete question");
        System.out.println("6. Save survey");
        System.out.println("7. Exit survey");
        System.out.println("######################################");
        System.out.println("Enter your option choice:");
    }

    private void handleSurveyOperations(Survey survey, Scanner scanner) {
        boolean editSurvey = true;
        if (survey.isOpen()) {
            System.out.println("Cannot perform operations on an open survey.");
            System.out.println("-------------------------------------");
            return;
        }

        while (!survey.isOpen() && editSurvey) {
            showSurveyOperations();
            int optionChoice = scanner.nextInt();

            switch (optionChoice) {
                case 1:
                    survey.printSurveyDetails();
                    break;
                case 2:
                    survey.editTitle();
                    break;
                case 3:
                    survey.addQuestion();
                    break;
                case 4:
                    survey.editQuestion();
                    break;
                case 5:
                    survey.deleteQuestion(scanner);
                    break;
                case 6:
                    surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
                    break;
                case 7:
                    editSurvey = false;
                    break;
                default:
                    System.out.println("Invalid option choice.");
                    System.out.println("-------------------------------------");
                    break;
            }
        }
    }

    public void openCloseSurvey() {
        printAllSurveyTitles();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the survey you want to open/close:");
        int surveyId = scanner.nextInt();
        String currentUser = getUsername();
        Survey survey = getSurveyByIdAndUser(surveyId, currentUser);

        if (survey != null) {
            if (survey.isOpen()) {
                closeSurvey(survey);
                System.out.println("Survey closed successfully.");
                System.out.println("-------------------------------------");
            } else {
                openSurvey(survey);
                System.out.println("Survey opened successfully.");
                System.out.println("-------------------------------------");
            }
        } else {
            System.out.println("Survey not found or you don't have permission to modify it.");
            System.out.println("-------------------------------------");
        }
    }

    private void openSurvey(Survey survey) {
        survey.setOpen(true);
        surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
    }

    public void closeSurvey(Survey survey) {
        survey.close();
        surveyTextFileHandler.saveSurvey(survey, survey.getCreatedBy());
    }

    private Survey getSurveyByIdAndUser(int surveyId, String currentUser) {
        List<Survey> surveys = surveyTextFileHandler.loadUserSpecificSurveys(currentUser);
        for (Survey survey : surveys) {
            if (survey.getId() == surveyId) {
                return survey;
            }
        }
        return null;
    }

    public void deleteSurvey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the survey you want to delete:");
        int surveyId = scanner.nextInt();
        Survey survey = getSurveyById(surveyId);

        if (survey != null) {
            surveys.remove(survey);
            surveyTextFileHandler.deleteSurvey(survey, survey.getCreatedBy());
            System.out.println("Survey deleted successfully.");
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Survey not found.");
            System.out.println("-------------------------------------");
        }
    }

    @Override
    public void viewSurveyReports() {
        SurveyReportGenerator surveyReportGenerator = new SurveyReportGenerator();
        surveyReportGenerator.generateSurveyReports("SurveyResponses", "SurveyReports");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the survey report ->");
        int surveyId = scanner.nextInt();
        String reportFileName = "Survey_ID_" + surveyId + "_Report.txt";
        String reportFile = "SurveyReports/" + reportFileName;

        List<Survey> surveys = surveyTextFileHandler.loadUserSpecificSurveys(getUsername());

        for (Survey survey : surveys) {
            if (survey.getId() == surveyId) {
                surveyReportGenerator.viewSurveyReport(reportFile);
                return;
            } else {
                System.out.println("Can not view survey report from another user.");
                System.out.println("-------------------------------------");
                break;
            }
        }

    }
}
