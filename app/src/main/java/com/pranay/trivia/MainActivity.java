package com.pranay.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pranay.trivia.controller.AppController;
import com.pranay.trivia.data.Repository;
import com.pranay.trivia.data.answerListAsyncResponse;
import com.pranay.trivia.databinding.ActivityMainBinding;
import com.pranay.trivia.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        new Repository().getQuestions(questionArrayList ->
                binding.questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer())
                );


    }
}