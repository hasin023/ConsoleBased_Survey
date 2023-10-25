package Survey;

import java.util.ArrayList;
import java.util.List;

public class Survey {
    private String title;
    private String createdBy;
    private boolean open;
    private final List<Question> questions;

    public Survey(String title, String createdBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.open = true;
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        if (questions.size() < 10) {
            questions.add(question);
        } else {
            System.out.println("A survey can have a maximum of 10 questions.");
        }
    }

    public void close() {
        this.open = false;
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
}
