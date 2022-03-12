package com.example.restproject.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import Adapters.AnswerTypeValueAdapter;
import Adapters.SurveyResultAdapter;
import DTO.AnswerTypeValueDTO;
import DTO.AnwserTypeValueToCreateDTO;
import DTO.SurveyResult;
import DTO.SurveyResultToReceive;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSurveyResults extends AppCompatActivity {


    String token;
    long surveyId;
    List<TypeDTO> typeDTOListOut;
    Spinner spinner;
    TextView answerTextEditAnswer;
    String [] data;
    RecyclerView listOfSurveyResult;
    long answerId;
    long pickedType;
    EditText editAnswerTypeValueFrom;
    EditText editAnswerTypeValueTo;
    EditText editTextResultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey_result);

        listOfSurveyResult = findViewById(R.id.listOfSurveyResult);
        editAnswerTypeValueFrom = findViewById(R.id.editAnswerTypeValueFrom);
        editAnswerTypeValueTo = findViewById(R.id.editAnswerTypeValueTo);
        editTextResultText = findViewById(R.id.editTextResultText);

        token = getIntent().getExtras().get("token").toString();
        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString() );

        spinner = (Spinner) findViewById(R.id.spinnerTypeForRes);
        getSurveyResults(token,this,surveyId);
        getSurveyTypes(token,this,surveyId);
    }

    public void onClockAddRes(View view){
        TypeDTO pickedTypeDto =  typeDTOListOut.get((int) pickedType);
        try {
            float valueForResultValueFrom = Float.parseFloat(editAnswerTypeValueFrom.getText().toString());
            float valueForResultValueTo = Float.parseFloat(editAnswerTypeValueTo.getText().toString());
            SurveyResult surveyResult = new SurveyResult();
            surveyResult.setSurveyId(surveyId);
            surveyResult.setText(editTextResultText.getText().toString());
            surveyResult.setTypeId(pickedTypeDto.getId());
            surveyResult.setValueFrom(valueForResultValueFrom);
            surveyResult.setValueTo(valueForResultValueTo);
            addSurveyResult(token,this,surveyResult);
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }











    public void setSpinnerdata(String[] data,List<TypeDTO> list){
        this.typeDTOListOut = list;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                pickedType = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
    private void getSurveyTypes(String token, Context context, long id) {

        Call<List<TypeDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getTypesOfSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<List<TypeDTO>>() {
            @Override
            public void onResponse(Call<List<TypeDTO>> call, Response<List<TypeDTO>> response) {
                List<TypeDTO> typeDTOList = response.body();
                if (typeDTOList != null) {

                    data = new String[typeDTOList.size()];
                    int i=0;
                    for (TypeDTO item : typeDTOList){
                        data[i] = item.getText();
                        i++;
                    }
                    setSpinnerdata(data,typeDTOList);

                }
                else{
                    data = new String[]{};
                }
            }

            @Override
            public void onFailure(Call<List<TypeDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    private void getSurveyResults(String token, Context context, long surveyId) {
        Call<List<SurveyResultToReceive>> call1 = NetworkService.getInstance()
                .getJSONApi().getResultsOfSurvey("Bearer_" + token,surveyId);
        call1.enqueue(new Callback<List<SurveyResultToReceive>>() {
            @Override
            public void onResponse(Call<List<SurveyResultToReceive>> call, Response<List<SurveyResultToReceive>> response) {


                List<SurveyResultToReceive> surveyResultToReceiveList = response.body();
                System.out.println(surveyResultToReceiveList.size());
                if (surveyResultToReceiveList != null) {
                    SurveyResultAdapter.OnSurveyResultListener questionClickListener = new SurveyResultAdapter.OnSurveyResultListener() {
                        @Override
                        public void OnSurevyResultClick(SurveyResultToReceive answerTypeValueDTOlist) {

                        }
                    };
                    SurveyResultAdapter adapter = new SurveyResultAdapter(context,surveyResultToReceiveList,questionClickListener);
                    listOfSurveyResult.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SurveyResultToReceive>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    private void addSurveyResult(String token, Context context, SurveyResult surveyResult) {

        Call<SurveyResult> call1 = NetworkService.getInstance()
                .getJSONApi().addSurveyResult("Bearer_" + token,surveyResult);
        call1.enqueue(new Callback<SurveyResult>() {
            @Override
            public void onResponse(Call<SurveyResult> call, Response<SurveyResult> response) {
                SurveyResult surveyResult1 = response.body();
                if (surveyResult1 != null) {
                    getSurveyResults(token,context,surveyResult1.getSurveyId());
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<SurveyResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }
}
