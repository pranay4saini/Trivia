package com.pranay.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.google.android.material.snackbar.Snackbar;

import com.pranay.trivia.Utils.Prefs;

import com.pranay.trivia.data.Repository;

import com.pranay.trivia.databinding.ActivityMainBinding;
import com.pranay.trivia.model.Question;
import com.pranay.trivia.model.Score;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private int ScoreCounter =0;
    private Score score;

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = new Score();
        prefs = new Prefs(MainActivity.this);





        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));


        questionList = new  Repository().getQuestions(questionArrayList ->{
                binding.questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                    updateCounter(questionArrayList);


                }
        );

        binding.nextButton.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();

        });

        binding.falseButton.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();
        });

        binding.trueButton.setOnClickListener(view ->{
            checkAnswer(true);
            updateQuestion();
        } );
    }

    private void checkAnswer(boolean userChoice) {
        boolean answer = questionList.get(currentQuestionIndex).isCorrect_answer();

        int snackMessageId;
        if(userChoice == answer){
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            addScore();
        }else{
            snackMessageId = R.string.incorrect_answer;
            shakeAnimation();
            deductScore();
        }

        Snackbar.make(binding.cardView,snackMessageId,Snackbar.LENGTH_SHORT)
                .show();
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.question_text_outof_formated), currentQuestionIndex, questionArrayList.size()));
    }

    private void updateQuestion() {

        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextview.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shakeanimation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void fadeAnimation(){

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);

        alphaAnimation.setDuration(150);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);


        binding.cardView.setAnimation(alphaAnimation);


        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void deductScore(){
        if(ScoreCounter>0){
            ScoreCounter -=100;
            score.setScore(ScoreCounter);
            binding.scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        }else {
            ScoreCounter =0;
            score.setScore(ScoreCounter);
        }
    }
    private void addScore(){
        ScoreCounter += 100;
        score.setScore(ScoreCounter);
        binding.scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        binding.highScoreTextView.setText(MessageFormat.format("Highest Score:  {0}",String.valueOf(prefs.getHighestScore())));
        super.onPause();
    }
}