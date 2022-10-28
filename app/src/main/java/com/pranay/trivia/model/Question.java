package com.pranay.trivia.model;

import androidx.annotation.NonNull;

public class Question {

    private String answer;
    private boolean correct_answer;


    public Question() {
    }

    public Question(String answer, boolean correct_answer) {
        this.answer = answer;
        this.correct_answer = correct_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(boolean correct_answer) {
        this.correct_answer = correct_answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", correct_answer=" + correct_answer +
                '}';
    }
}
