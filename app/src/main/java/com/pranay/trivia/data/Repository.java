package com.pranay.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pranay.trivia.controller.AppController;
import com.pranay.trivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();
        String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
        String url1 = "https://opentdb.com/api.php?amount=50&type=boolean";
    public List<Question> getQuestions(final answerListAsyncResponse callBack){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, url, null,
                response -> {

                    for (int i = 0; i <response.length(); i++) {
                        try {

                            Question question = new Question(response.getJSONArray(i).get(0).toString(),response.getJSONArray(i).getBoolean(1));



                            questionArrayList.add(question);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if(null != callBack) callBack.processFinished(questionArrayList);


                }, error -> {

        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

            return null;
    }
}