package Survey;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final int id;
    private String text;
    private List<String> options;

    public Question(int id, String text, List<String> options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public void setText(String questionText) {
        this.text = questionText;
    }
}
