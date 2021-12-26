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
import DTO.AnswerTypeValueDTO;
import DTO.AnwserTypeValueToCreateDTO;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAnswerActivity extends AppCompatActivity {

    String token;
    long surveyId;
    long questionId;
    List<TypeDTO> typeDTOListOut;
    Spinner spinner;
    TextView answerTextEditAnswer;
    String [] data;
    RecyclerView recyclerViewAnswerTypeValue;
    long answerId;
    long pickedType;
    EditText editAnswerTypeValueValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_answer);

        recyclerViewAnswerTypeValue = findViewById(R.id.listOfTypes);
        editAnswerTypeValueValue = findViewById(R.id.editAnswerValueValue);


        answerTextEditAnswer = findViewById(R.id.answerTextEdit);
        answerTextEditAnswer.setText(getIntent().getExtras().get("answerText").toString());

        token = getIntent().getExtras().get("token").toString();
        answerId = Long.parseLong( getIntent().getExtras().get("answerId").toString() );
        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString() );

        spinner = (Spinner) findViewById(R.id.spinnerTypeForAnswer);
        getSurveyTypes(token,this,surveyId);
        getAnswerTypeValueOfAnswer(token,this,answerId);
    }


    public void onClickAddTypeToAnswer(View view){
        TypeDTO pickedTypeDto =  typeDTOListOut.get((int) pickedType);
        try {
            float valueForAnwserTypeValueDTO = Float.parseFloat(editAnswerTypeValueValue.getText().toString());
            AnwserTypeValueToCreateDTO anwserTypeValueToCreateDTO = new AnwserTypeValueToCreateDTO();
            anwserTypeValueToCreateDTO.setTypeID(pickedTypeDto.getId());
            anwserTypeValueToCreateDTO.setAnswerID(answerId);
            anwserTypeValueToCreateDTO.setValue(valueForAnwserTypeValueDTO);
            createAnswerTypeValue(token,this,anwserTypeValueToCreateDTO);
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
                    System.out.println("data length prev :  " + data.length);
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

    private void getAnswerTypeValueOfAnswer(String token, Context context, long answerId) {
        Call<List<AnswerTypeValueDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAnswerTypeValueOfAnswer("Bearer_" + token,answerId);
        call1.enqueue(new Callback<List<AnswerTypeValueDTO>>() {
            @Override
            public void onResponse(Call<List<AnswerTypeValueDTO>> call, Response<List<AnswerTypeValueDTO>> response) {


                List<AnswerTypeValueDTO> answerTypeValueDTOlist = response.body();

                if (answerTypeValueDTOlist != null) {
                    AnswerTypeValueAdapter.OnAnswerTypeValueClickListener questionClickListener = new AnswerTypeValueAdapter.OnAnswerTypeValueClickListener() {
                        @Override
                        public void onAnswerTypeValueClick(AnswerTypeValueDTO answerTypeValueDTO) {

                        }
                    };
                    AnswerTypeValueAdapter adapter = new AnswerTypeValueAdapter(context,answerTypeValueDTOlist,questionClickListener);
                    recyclerViewAnswerTypeValue.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AnswerTypeValueDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    private void createAnswerTypeValue(String token, Context context, AnwserTypeValueToCreateDTO anwserTypeValueToCreateDTO) {

        Call<AnswerTypeValueDTO> call1 = NetworkService.getInstance()
                .getJSONApi().createAnswerTypeValue("Bearer_" + token,anwserTypeValueToCreateDTO);
        call1.enqueue(new Callback<AnswerTypeValueDTO>() {
            @Override
            public void onResponse(Call<AnswerTypeValueDTO> call, Response<AnswerTypeValueDTO> response) {
                AnswerTypeValueDTO answerTypeValueDTO = response.body();
                if (answerTypeValueDTO != null) {
                    getAnswerTypeValueOfAnswer(token,context,answerTypeValueDTO.getAnswerID());
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<AnswerTypeValueDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }
}
