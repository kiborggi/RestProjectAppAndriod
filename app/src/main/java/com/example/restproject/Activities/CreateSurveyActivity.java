package com.example.restproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restproject.R;

import DTO.CreateSurveyDTO;
import DTO.LoginReq;
import DTO.LoginResponse;
import DTO.SurveyDTO;
import Pojo.CommonMethod;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSurveyActivity extends AppCompatActivity {

    String token;
    EditText editName;
    EditText editDesc;
    CreateSurveyDTO createSurveyDTO;
    Intent editSurvey;
    Spinner spinner;
    String pickedType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);
        token = getIntent().getExtras().get("token").toString();
         editName = findViewById(R.id.nameOfSurvey);
         editDesc = findViewById(R.id.decriptionOfSurvey);

        spinner = (Spinner) findViewById(R.id.syrveyTypeSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.surveyTypes);

                pickedType = choose[selectedItemPosition];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.surveyTypes,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void onClickCreateSurvey(View view){
         createSurveyDTO = new CreateSurveyDTO();
        if (checkValidation() == true) {
            CreateSurveyDTO createSurveyDTO = new CreateSurveyDTO();
            createSurveyDTO.setName(editName.getText().toString());
            createSurveyDTO.setDescription(editDesc.getText().toString());
            createSurveyDTO.setType(pickedType);
            createSurvey(token,createSurveyDTO);
        }
    }




    public void createSurvey(String token, CreateSurveyDTO createSurveyDTO){
        Call<SurveyDTO> call1 =  NetworkService.getInstance()
                .getJSONApi().createSurvey("Bearer_" + token,createSurveyDTO);

        call1.enqueue(new Callback<SurveyDTO>() {
            @Override
            public void onResponse(Call<SurveyDTO> call, Response<SurveyDTO> response) {
                SurveyDTO responseSurvey = response.body();
                if (responseSurvey != null) {

                }

            }

            @Override
            public void onFailure(Call<SurveyDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public boolean checkValidation() {

        if (editName.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("Survey name Cannot be left blank", CreateSurveyActivity.this);
            return false;
        } else if (editDesc.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("Survey desc Cannot be left blank", CreateSurveyActivity.this);
            return false;
        }
        return true;
    }
}
