package com.pranay.trivia.data;

import com.pranay.trivia.model.Question;

import java.util.ArrayList;

public interface answerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
