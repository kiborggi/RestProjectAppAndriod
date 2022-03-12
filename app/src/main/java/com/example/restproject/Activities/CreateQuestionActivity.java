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

import DTO.CreateQuestionDTO;
import DTO.QuestionDTO;
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
    Spinner spinner;
    String pickedType;
    EditText questionNumberFrom;
    EditText questionNumberTo;
    TextView textViewQuestionNumberFrom;
    TextView textViewQuestionNumberTo;

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
        spinner = (Spinner) findViewById(R.id.questionTypeSpinner);
         questionNumberFrom = (EditText) findViewById(R.id.questionNumberFrom);
         questionNumberTo= (EditText) findViewById(R.id.questionNumberTo);
         textViewQuestionNumberFrom = (TextView) findViewById(R.id.textViewQuestionNumberFrom);
         textViewQuestionNumberTo= (TextView) findViewById(R.id.textViewQuestionNumberTo);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.questionTypes);

                pickedType = choose[selectedItemPosition];
                if (pickedType.equals("NUMERIC")){
                    questionNumberFrom.setVisibility(View.VISIBLE);
                    questionNumberTo.setVisibility(View.VISIBLE);
                    textViewQuestionNumberFrom.setVisibility(View.VISIBLE);
                    textViewQuestionNumberTo.setVisibility(View.VISIBLE);
                }
                else{
                    questionNumberFrom.setVisibility(View.INVISIBLE);
                    questionNumberTo.setVisibility(View.INVISIBLE);
                    textViewQuestionNumberFrom.setVisibility(View.INVISIBLE);
                    textViewQuestionNumberTo.setVisibility(View.INVISIBLE);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.questionTypes,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    public void onClickAddQuestion(View view){
        createQuestionDTO = new CreateQuestionDTO();
        createQuestionDTO.setText(questionText.getText().toString());
        createQuestionDTO.setSurveyId(surveyId);
        createQuestionDTO.setTypeOfQuestion(pickedType);
        if (pickedType.equals("NUMERIC")){
            createQuestionDTO.setNumFrom(Float.parseFloat(questionNumberFrom.getText().toString()));
            createQuestionDTO.setNumTo(Float.parseFloat(questionNumberTo.getText().toString()));
        }
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
