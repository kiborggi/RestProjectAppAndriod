package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import Adapters.SurveyAdapter;
import DTO.SurveyDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySurveyActivity extends AppCompatActivity {

    List<SurveyDTO> listSurvey;
    RecyclerView recyclerView;
    Intent intentCreateSurvey;
    String token;
    Intent editSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);

        token = getIntent().getExtras().get("token").toString();
        intentCreateSurvey = new Intent(this,CreateSurveyActivity.class);
        intentCreateSurvey.putExtra("token",token);

        editSurvey = new Intent(this,EditSurveyActivity.class);
        editSurvey.putExtra("token",token);

        recyclerView = findViewById(R.id.listOfMyAttempts);
        getUserSurveys(token,this);
    }



    private void getUserSurveys(String token, Context context) {

        Call<List<SurveyDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAllSurveysOfUser("Bearer_" + token);
        call1.enqueue(new Callback<List<SurveyDTO>>() {
            @Override
            public void onResponse(Call<List<SurveyDTO>> call, Response<List<SurveyDTO>> response) {
                List<SurveyDTO> suerveyListResp = response.body();
                if (suerveyListResp != null) {
                    SurveyAdapter.OnSurveyClickListener surveyClickListener = new SurveyAdapter.OnSurveyClickListener() {
                        @Override
                        public void OnSurveyClick(SurveyDTO surveyDTO) {

                            editSurvey.putExtra("surveyId",surveyDTO.getId());
                            editSurvey.putExtra("surveyName",surveyDTO.getName());
                            editSurvey.putExtra("surveyDesc",surveyDTO.getDescription());
                            startActivity(editSurvey);

                        }
                    };
                    SurveyAdapter adapter = new SurveyAdapter(context,suerveyListResp,surveyClickListener);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SurveyDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    public void onClickCreateSurvey(View view){
        startActivity(intentCreateSurvey);
    }

    @Override
    public void onBackPressed() {
      Intent  intetntMySurvey = new Intent(this, UserInfoActivity.class);
      intetntMySurvey.putExtra("token",token);

      startActivity(intetntMySurvey);
    }
}
