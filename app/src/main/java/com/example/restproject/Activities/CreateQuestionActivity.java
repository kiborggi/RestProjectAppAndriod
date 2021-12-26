package com.example.restproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restproject.R;

import DTO.CreateQuestionDTO;
import DTO.LoginResponse;
import DTO.QuestionDTO;
import DTO.RegisterReq;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuestionActivity extends AppCompatActivity {
    EditText questionText;
    CreateQuestionDTO createQuestionDTO;
    long surveyId;
    String token;
    Intent editSurvey;
    String surveyName;
    String surveyDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        token = getIntent().getStringExtra("token");
        questionText = (EditText) findViewById(R.id.editQuestionText);
        surveyId = getIntent().getLongExtra("surveyId",0);
        editSurvey = new Intent(this,EditSurveyActivity.class);
        surveyName = getIntent().getExtras().get("surveyName").toString();
        surveyDesc= getIntent().getExtras().get("surveyDesc").toString();


    }

    public void onClickAddQuestion(View view){
        createQuestionDTO = new CreateQuestionDTO();
        createQuestionDTO.setText(questionText.getText().toString());
        createQuestionDTO.setSurveyId(surveyId);
        createQuestion(createQuestionDTO,token);
    }


    private void createQuestion(CreateQuestionDTO questionDTO, String token) {

        Call<QuestionDTO> call1 =  NetworkService.getInstance()
                .getJSONApi().createQuestion("Bearer_" + token,questionDTO);

        call1.enqueue(new Callback<QuestionDTO>() {
            @Override
            public void onResponse(Call<QuestionDTO> call, Response<QuestionDTO> response) {
                QuestionDTO qestResp = response.body();
                if (qestResp != null) {
                     editSurvey.putExtra("token",token);
                    editSurvey.putExtra("surveyName",surveyName);
                    editSurvey.putExtra("surveyDesc",surveyDesc);
                    editSurvey.putExtra("surveyId",questionDTO.getSurveyId());
                    startActivity(editSurvey);
                }

            }

            @Override
            public void onFailure(Call<QuestionDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}
