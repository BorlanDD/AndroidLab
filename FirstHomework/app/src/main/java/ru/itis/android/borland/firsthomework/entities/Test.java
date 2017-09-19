package ru.itis.android.borland.firsthomework.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borland on 19.09.2017.
 */

public class Test {
    protected List<Question> questions;
    protected int currentIndex;
    protected int rightAnsweredQuestionsNumber;

    public Test(String[] questions, String[] answers, String separator, int rightAnswerIndex) {
        this.questions = new ArrayList<>();
        rightAnsweredQuestionsNumber = 0;
        currentIndex = 0;
        initQuestionList(questions, answers, separator, rightAnswerIndex);
    }

    private void initQuestionList(String[] questions, String[] answers, String separator, int rightAnswerIndex) {
        String[] currentQuestionAnswers;
        for (int i = 0; i < questions.length; i++) {
            currentQuestionAnswers = answers[i].split(separator);
            this.questions.add(new Question(questions[i],
                    currentQuestionAnswers,
                    currentQuestionAnswers[rightAnswerIndex]));
        }
    }

    public boolean isEnded() {
        return (currentIndex == questions.size() - 1);
    }

    public Question getNextQuestion() {
        return questions.get(++currentIndex);
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public int getCurrentIndex() { return currentIndex; }

    public int getQuestionsNumber() { return questions.size(); }

    public int getRightAnsweredQuestionsNumber() {
        return rightAnsweredQuestionsNumber;
    }

    public void verifyCorrectness(Question currentQuestion) {
        if (currentQuestion.isRight()) rightAnsweredQuestionsNumber++;
    }
}
