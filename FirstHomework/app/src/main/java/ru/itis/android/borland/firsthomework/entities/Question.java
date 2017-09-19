package ru.itis.android.borland.firsthomework.entities;

/**
 * Created by Borland on 19.09.2017.
 */

public class Question {
    private String text;
    private String[] answers;
    private String rightAnswer;
    private boolean isRight;

    public Question(String text, String[] answers, String rightAnswer) {
        this.text = text;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public void answer(String answer) {
        isRight = rightAnswer.equals(answer);
    }

    public boolean isRight() {
        return isRight;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }
}
