package com.example.restproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restproject.R;

import DTO.CreateQuestionDTO;
import DTO.CreateTypeDTO;
import DTO.QuestionDTO;
import DTO.RegisterReq;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTypeActivity extends AppCompatActivity {
    EditText typeText;
    long surveyId;
    String token;
    CreateTypeDTO createTypeDTO;
    Intent editSurvey;
    String surveyName;
    String surveyDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_type);
        typeText = (EditText) findViewById(R.id.editTypeText);
        token = getIntent().getStringExtra("token");
        surveyId = getIntent().getLongExtra("surveyId",0);
        editSurvey = new Intent(this,EditSurveyActivity.class);
         surveyName = getIntent().getExtras().get("surveyName").toString();
         surveyDesc= getIntent().getExtras().get("surveyDesc").toString();

    }

    public void onClickAddType (View view){
        createTypeDTO = new CreateTypeDTO();
        createTypeDTO.setSurveyId(surveyId);
        createTypeDTO.setText(typeText.getText().toString());
        createType(createTypeDTO,token);

    }


    private void createType(CreateTypeDTO createTypeDTO, String token) {

        Call<TypeDTO> call1 =  NetworkService.getInstance()
                .getJSONApi().createTypeForSurvey("Bearer_" + token,createTypeDTO);

        call1.enqueue(new Callback<TypeDTO>() {
            @Override
            public void onResponse(Call<TypeDTO> call, Response<TypeDTO> response) {
                TypeDTO typeReq = response.body();
                if (typeReq != null) {
                    editSurvey.putExtra("token",token);
                    editSurvey.putExtra("surveyName",surveyName);
                    editSurvey.putExtra("surveyDesc",surveyDesc);
                    editSurvey.putExtra("surveyId",createTypeDTO.getSurveyId());
                    startActivity(editSurvey);
                }

            }

            @Override
            public void onFailure(Call<TypeDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }


}
