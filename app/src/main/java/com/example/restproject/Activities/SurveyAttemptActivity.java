package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restproject.R;

import java.util.List;

import DTO.LoginReq;
import DTO.LoginResponse;
import DTO.SurveyDTO;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyAttemptActivity extends AppCompatActivity {

    long surveyId;
    TextView surveyAttemptName;
    TextView surveyAttemptDesc;
    String token;
    Intent runAttemptIntent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_attempt);
        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString());
        surveyAttemptName = findViewById(R.id.surveyAttemptName);
        surveyAttemptDesc =findViewById(R.id.surveyAttemptDesc);
        token = getIntent().getExtras().get("token").toString();
        runAttemptIntent = new Intent(this,RunAttemptActivity.class);
        runAttemptIntent.putExtra("token",token);
        runAttemptIntent.putExtra("surveyId",surveyId);
        getSurvey(token,surveyId);
    }




    public void onClockStartNewAttempt(View view){
        startActivity(runAttemptIntent);
    }











    private void getSurvey(String token,long id) {

        Call<SurveyDTO> call1 = NetworkService.getInstance()
                .getJSONApi().getSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<SurveyDTO>() {
            @Override
            public void onResponse(Call<SurveyDTO> call, Response<SurveyDTO> response) {
                SurveyDTO surveyDTO = response.body();
                if (surveyDTO != null) {
                    surveyAttemptName.setText(surveyDTO.getName());
                    surveyAttemptDesc.setText(surveyDTO.getDescription());
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<SurveyDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }


    }

