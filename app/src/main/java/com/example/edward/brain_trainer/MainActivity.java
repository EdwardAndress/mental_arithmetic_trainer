package com.example.edward.brain_trainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button playAgainButton;
    ArrayList<Integer> answers;
    int locationOfCorrectAnswer;
    int correctAnswer;
    int incorrectAnswer;
    TextView scoreTextView;
    TextView timerTextView;
    TextView result;
    int score = 0;
    int highScore = 0;
    int questionsAsked = 0;
    TextView sumTextView;
    TextView highScoreTextView;
    Boolean gameInProgress;
    RelativeLayout gameLayout;


    Button button0;
    Button button1;
    Button button2;
    Button button3;

    public void generateQuestion() {

        Random rand = new Random();
        answers = new ArrayList<Integer>();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        correctAnswer = a + b;
        locationOfCorrectAnswer = rand.nextInt(4);

        for (int i=0; i<4; i++) {
            if ( i == locationOfCorrectAnswer) {
                answers.add(correctAnswer);
            } else {

                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == correctAnswer) {
                    correctAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

    public void playAgain(View view) {
        gameInProgress = TRUE;
        score = 0;
        questionsAsked = 0;
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        result.setText("Choose an answer");
        playAgainButton.setVisibility(View.INVISIBLE);

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                gameInProgress = FALSE;
                playAgainButton.setVisibility(View.VISIBLE);
                if (score > highScore) {
                    highScore = score;
                    highScoreTextView.setText("Today's highest score: " + Integer.toString(highScore));
                    result.setText("NEW HIGH SCORE!!");
                } else {
                    result.setText("Game over");
                }
            }
        }.start();
    }

    public void chooseAnswer(View view) {

        String choiceLocation = view.getTag().toString();
        String correctAnswerLocation = Integer.toString(locationOfCorrectAnswer);

        if (gameInProgress == TRUE) {
            if (choiceLocation.equals(correctAnswerLocation)) {
                score++;
                result.setText("Correct!");
            } else {
                result.setText("Incorrect!");
            }

            questionsAsked++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questionsAsked));
            generateQuestion();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        sumTextView = findViewById(R.id.sumTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        button0 = findViewById(R.id.answerButton0);
        button1 = findViewById(R.id.answerButton1);
        button2 = findViewById(R.id.answerButton2);
        button3 = findViewById(R.id.answerButton3);
        result = findViewById(R.id.resultText);
        highScoreTextView = findViewById(R.id.highScoreTextView);
        gameLayout = findViewById(R.id.gameLayout);

        generateQuestion();

    }
}
