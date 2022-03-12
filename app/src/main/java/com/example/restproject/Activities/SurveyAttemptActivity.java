package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import Adapters.AttemptAdapter;
import Adapters.SurveyAdapter;
import DTO.ForAttempt.AttemptDTO;
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
    Intent runTestAttmptTetstIntentt;
    RecyclerView listOfMyAttempts;
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

        listOfMyAttempts = findViewById(R.id.listOfMyAttempts);
        runTestAttmptTetstIntentt = new Intent(this,RunAttemptTestActivity.class);

        runTestAttmptTetstIntentt.putExtra("token",token);
        runTestAttmptTetstIntentt.putExtra("surveyId",surveyId);

        getSurvey(token,this,surveyId);

    }




    public void onClockStartNewAttempt(View view){
        startActivity(runAttemptIntent);
    }


    public void onClickTestStartAttempt(View view){
        startActivity(runTestAttmptTetstIntentt);
    }








    private void getSurvey(String token,Context context,long id) {

        Call<SurveyDTO> call1 = NetworkService.getInstance()
                .getJSONApi().getSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<SurveyDTO>() {
            @Override
            public void onResponse(Call<SurveyDTO> call, Response<SurveyDTO> response) {
                SurveyDTO surveyDTO = response.body();
                if (surveyDTO != null) {
                    surveyAttemptName.setText(surveyDTO.getName());
                    surveyAttemptDesc.setText(surveyDTO.getDescription());
                    getAttempts(token,context,surveyDTO.getId());
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


    private void getAttempts(String token, Context context,long surveyId) {

        Call<List<AttemptDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAttemptsOfSurvey("Bearer_" + token,surveyId);
        call1.enqueue(new Callback<List<AttemptDTO>>() {
            @Override
            public void onResponse(Call<List<AttemptDTO>> call, Response<List<AttemptDTO>> response) {
                List<AttemptDTO> attemptDTOList = response.body();
                if (attemptDTOList != null) {
                    AttemptAdapter.OnAttemptClickListener attemptClickListener = new AttemptAdapter.OnAttemptClickListener() {
                        @Override
                        public void OnAttemptClick(AttemptDTO q) {

                        }


                    };

                    AttemptAdapter adapter = new AttemptAdapter(context,attemptDTOList,attemptClickListener);
                    listOfMyAttempts.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AttemptDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }


    }

