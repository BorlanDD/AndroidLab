package ru.itis.android.borland.firsthomework.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.borland.firsthomework.R;
import ru.itis.android.borland.firsthomework.entities.Question;
import ru.itis.android.borland.firsthomework.entities.Test;

public class TestActivity extends AppCompatActivity {
    public static final int RIGHT_ANSWER_INDEX = 0;
    public static final String RB_ANSWER_PATTERN = "rb_answer";
    public static final String RB_ID_RESOURCE_TYPE = "id";
    public static final String SEPARATOR = ";";

    private TextView tvQuestionNumber;
    private TextView tvQuestionText;
    private RadioGroup rgAnswers;
    private List<RadioButton> rbAnswers;
    private Button btnAnswer;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
        test = new Test(getResources().getStringArray(R.array.array_questions),
                getResources().getStringArray(R.array.array_answers), SEPARATOR, RIGHT_ANSWER_INDEX);
        updateQuestion(test.getCurrentQuestion());
    }

    private void init() {
        tvQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        tvQuestionText = (TextView) findViewById(R.id.tv_question_text);
        rgAnswers = (RadioGroup) findViewById(R.id.rg_answers);

        rbAnswers = new ArrayList<>();
        int rbId;
        for (int i = 1; i <= rgAnswers.getChildCount(); i++) {
            rbId = getResources().getIdentifier(RB_ANSWER_PATTERN + i,
                    RB_ID_RESOURCE_TYPE,
                    this.getPackageName());
            rbAnswers.add((RadioButton) findViewById(rbId));
        }

        btnAnswer = (Button) findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rbChecked = (RadioButton) findViewById(rgAnswers.getCheckedRadioButtonId());
                if (rbChecked != null) {
                    Question currentQuestion = test.getCurrentQuestion();
                    currentQuestion.answer(rbChecked.getText().toString());
                    test.verifyCorrectness(currentQuestion);
                    updateQuestion();
                } else
                    Toast.makeText(TestActivity.this, R.string.tv_answer_not_checked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateQuestion(Question firstQuestion) {
        updateScreen(firstQuestion);
    }
    private void updateQuestion() {
        if (!test.isEnded()) {
            Question currentQuestion = test.getNextQuestion();
            updateScreen(currentQuestion);
        } else {
            openResult();
        }
    }
    private void updateScreen(Question currentQuestion) {
        tvQuestionNumber.setText("Вопрос " + (test.getCurrentIndex() + 1) + " из "
                + test.getQuestionsNumber());
        tvQuestionText.setText(currentQuestion.getText());
        rgAnswers.clearCheck();

        String[] answers = currentQuestion.getAnswers();
        shuffleQuestions(answers);
        for (int i = 0; i < answers.length; i++) {
            rbAnswers.get(i).setText(answers[i]);
        }
    }

    private void shuffleQuestions(String[] answers) {
        int toIndex, fromIndex;
        String tmp;
        for (int i = 0; i < answers.length; i++) {
            toIndex = (int) (Math.random()*answers.length);
            fromIndex = (int) (Math.random()*answers.length);
            tmp = answers[toIndex];
            answers[toIndex] = answers[fromIndex];
            answers[fromIndex] = tmp;
        }
    }

    private void openResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_QUESTIONS_NUMBER, test.getQuestionsNumber());
        intent.putExtra(ResultActivity.EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER,
                test.getRightAnsweredQuestionsNumber());
        startActivity(intent);
    }
}
