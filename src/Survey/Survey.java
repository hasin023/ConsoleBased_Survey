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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
    }


    public void addQuestion(Question question) {
        if (questions.size() < 10) {
            questions.add(question);
        } else {
            System.out.println("A survey can have a maximum of 10 questions.");
        }
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
        System.out.println("Survey Open: " + open);
        System.out.println("Survey Created By: " + createdBy);
        System.out.println("Survey Questions:");

        for (Question question : questions) {
            System.out.println("Question " + question.getId() + ": " + question.getText());
            System.out.println("Options: " + String.join(",", question.getOptions()));
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


    public void editTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new title for the survey:");
        String title = scanner.nextLine();
        setTitle(title);
        System.out.println("Survey title updated successfully.");
    }

}
