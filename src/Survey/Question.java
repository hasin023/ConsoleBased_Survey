package Survey;

import java.util.List;

public class Question {
    private final String text;
    private final List<String> options;
    private final int correctAnswerIndex;

    public Question(String text, List<String> options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getQuestion() {
        return text;
    }
}
