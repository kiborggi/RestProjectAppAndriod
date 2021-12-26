package com.example.restproject.Activities;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import Adapters.AnswerAdapter;
import Adapters.SurveyAdapter;
import Adapters.TypeAdapter;
import DTO.AnswerDTO;
import DTO.AnswerToCreateDTO;
import DTO.SurveyDTO;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuestionActivity extends AppCompatActivity {
    String token;
    long surveyId;
    long questionId;
    List<TypeDTO> typeDTOListA;
    Spinner spinner;
    RecyclerView recyclerView;
    Intent editAnswer;
    long sureyId;
    TextView questionName;

    EditText editTextAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        token = getIntent().getExtras().get("token").toString();

        recyclerView = findViewById(R.id.listOfAnswers);

        questionName = findViewById(R.id.editQuestionText);

        editTextAnswerText = findViewById(R.id.editTextAnswerText);

        if (getIntent().getExtras().get("questionText").toString() != null){
            String questionText = getIntent().getExtras().get("questionText").toString();
            questionName.setText(questionText);
        }
        else {
            questionName.setText("no text");
        }



        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString() );

        editAnswer = new Intent(this, EditAnswerActivity.class);

        editAnswer.putExtra("token", token);

        questionId = getIntent().getExtras().getLong("questionId");

        getAnswersOfQuestion(token,this,questionId);

    }

    public void onClickAddAnswer(View view){
        AnswerToCreateDTO answerToCreateDTO = new AnswerToCreateDTO();
        answerToCreateDTO.setQuestionId(questionId);
        answerToCreateDTO.setText(editTextAnswerText.getText().toString());
        createAnswer(token,this,answerToCreateDTO);
        getAnswersOfQuestion(token,this,questionId);
    }


    private void createAnswer(String token, Context context, AnswerToCreateDTO answerToCreateDTO) {

        Call<AnswerDTO> call1 = NetworkService.getInstance()
                .getJSONApi().createAnswerForQuestion("Bearer_" + token,answerToCreateDTO);
        call1.enqueue(new Callback<AnswerDTO>() {
            @Override
            public void onResponse(Call<AnswerDTO> call, Response<AnswerDTO> response) {
                AnswerDTO answerDTO = response.body();
                if (answerDTO != null) {
                }
            }

            @Override
            public void onFailure(Call<AnswerDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    private void getAnswersOfQuestion(String token, Context context,long questionId) {

        Call<List<AnswerDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAnswersOfQuestion("Bearer_" + token,questionId);
        call1.enqueue(new Callback<List<AnswerDTO>>() {
            @Override
            public void onResponse(Call<List<AnswerDTO>> call, Response<List<AnswerDTO>> response) {
                List<AnswerDTO> answerDTOList = response.body();
                if (answerDTOList != null) {
                    AnswerAdapter.OnAnswerClickListener answerClickListener = new AnswerAdapter.OnAnswerClickListener() {
                        @Override
                        public void OnAnswerClick(AnswerDTO answerDTO) {
                            System.out.println("put answerID  = " + answerDTO.getId());
                            editAnswer.putExtra("answerId",answerDTO.getId());
                            editAnswer.putExtra("surveyId",surveyId);
                            editAnswer.putExtra("answerText",answerDTO.getText());
                            startActivity(editAnswer);

                        }
                    };
                    AnswerAdapter adapter = new AnswerAdapter(context,answerDTOList,answerClickListener);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AnswerDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }






    }

