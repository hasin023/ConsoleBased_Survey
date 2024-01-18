package Survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Survey {
    private final int id;
    private String title;
    private final String createdBy;
    private boolean open;
    private List<Question> questions;

    public Survey(int id, String title, String createdBy) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.open = true;
        this.questions = new ArrayList<>();
    }

    public void close() {
        this.open = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public boolean isOpen() {
        return open;
    }

    public String getStatus() {
        if (open) {
            return "Open";
        } else {
            return "Closed";
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
    }

    public void editTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new title for survey -> ");
        String title = scanner.nextLine();
        setTitle(title);
        System.out.println("Survey title updated successfully.");
    }

    public void addQuestion(Question question) {
        if (questions.size() < 10) {
            questions.add(question);
        } else {
            System.out.println("A survey can have a maximum of 10 questions.");
        }
    }

    public void addQuestion() {
        Scanner scanner = new Scanner(System.in);
        if (questions.size() < 10) {
            System.out.println("Enter the question text ->");
            String questionText = scanner.nextLine().trim();

            if (!questionText.isEmpty()) {
                List<String> options = readOptions(scanner);
                addOptionedQuestion(questionText, options);

            } else {
                System.out.println("Question text cannot be empty.");
            }
        }
    }

    private List<String> readOptions(Scanner scanner) {
        List<String> options = new ArrayList<>();

        System.out.println("Enter the number of options ->");
        int numberOfOptions = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfOptions; i++) {
            System.out.println("Enter option " + i + ":");
            String option = scanner.nextLine().trim();
            options.add(option);
        }

        return options;
    }

    private void addOptionedQuestion(String questionText, List<String> options) {
        int lastQuestionId = getLastQuestionId();
        int newQuestionId = lastQuestionId + 1;

        Question question = new Question(newQuestionId, questionText, options);
        addQuestion(question);
    }

    private int getLastQuestionId() {
        int maxQuestionId = 0;

        for (Question question : questions) {
            int currentQuestionId = question.getId();
            if (currentQuestionId > maxQuestionId) {
                maxQuestionId = currentQuestionId;
            }
        }

        return maxQuestionId;
    }

    public void printSurveyDetails() {
        System.out.println("Survey Title: " + title);
        System.out.println("Survey ID: " + id);
        if (open) {
            System.out.println("Survey status: Open");
        } else {
            System.out.println("Survey status: Closed");
        }
        System.out.println("Survey Created By: " + createdBy);
        System.out.println("Survey Questions:");

        for (Question question : questions) {
            System.out.println("\tQuestion " + question.getId() + ": " + question.getText());
            System.out.println("\t\tOptions: " + String.join(",", question.getOptions()));
        }
    }

    private void printQuestionsWithIds() {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
        } else {
            System.out.println("List of Questions:");
            for (Question question : questions) {
                System.out.println("ID: " + question.getId() + " - " + question.getText());
            }
        }
    }

    private Question findQuestionById(int questionId) {
        for (Question question : questions) {
            if (question.getId() == questionId) {
                return question;
            }
        }
        return null;
    }

    public void editQuestion() {
        printQuestionsWithIds();

        if (questions.isEmpty()) {
            System.out.println("No questions to edit.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the question ->");
        int questionIdToEdit = scanner.nextInt();
        scanner.nextLine();

        Question questionToEdit = findQuestionById(questionIdToEdit);

        if (questionToEdit != null) {
            System.out.println("Enter the new question text ->");
            String questionText = scanner.nextLine();
            questionToEdit.setText(questionText);

            editOptionQuestion(questionToEdit, scanner);

            System.out.println("Question updated successfully ✨");
        } else {
            System.out.println("Question with ID " + questionIdToEdit + " not found in the current survey.");
        }
    }

    private void editOptionQuestion(Question optionQuestion, Scanner scanner) {
        System.out.println("Enter the new options ->");
        List<String> options = readOptions(scanner);
        optionQuestion.setOptions(options);
    }

    public void deleteQuestion(Scanner scanner) {
        printQuestionsWithIds();

        if (questions.isEmpty()) {
            System.out.println("No questions to delete.");
            return;
        }

        System.out.println("Enter the ID of the question ->");
        int questionIdToDelete = scanner.nextInt();
        scanner.nextLine();

        Question questionToDelete = findQuestionById(questionIdToDelete);

        if (questionToDelete != null) {
            questions.remove(questionToDelete);
            System.out.println("Question deleted successfully ❌");
        } else {
            System.out.println("Question with ID " + questionIdToDelete + " not found in the current survey.");
        }
    }

}
