package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import Adapters.QuestionAdapter;
import Adapters.SurveyAdapter;
import Adapters.TypeAdapter;
import DTO.QuestionDTO;
import DTO.SurveyDTO;
import DTO.TypeDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSurveyActivity extends AppCompatActivity {

    String token;
    long surveyId;
    List<QuestionDTO> questionDTOList;
    List<TypeDTO> typeDTOList;
    RecyclerView recyclerViewQuestion;
    RecyclerView recyclerViewType;
    TextView surveyName;
    TextView surveyDesc;
    Intent createQuestionIntent;
    Intent createTypeIntent;
    Intent editQuestionIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey);
        token = getIntent().getExtras().get("token").toString();
        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString() );

        surveyName = (TextView) findViewById(R.id.surveyName);
        surveyDesc =(TextView) findViewById(R.id.surveyDesc);
        surveyName.setText(getIntent().getExtras().get("surveyName").toString());
        surveyDesc.setText(getIntent().getExtras().get("surveyDesc").toString());

        createQuestionIntent = new Intent(this,CreateQuestionActivity.class);
        createTypeIntent = new Intent(this,CreateTypeActivity.class);
        editQuestionIntent = new Intent(this, EditQuestionActivity.class);

        recyclerViewQuestion = findViewById(R.id.listQuestion);
        recyclerViewType = findViewById(R.id.typeList);

        getSurveyQuestions(token,this,surveyId);
        getSurveyTypes(token,this,surveyId);
    }


    private void getSurveyQuestions(String token, Context context, long surveyId) {
        Call<List<QuestionDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getQuestionsOfSurvey("Bearer_" + token,surveyId);
        call1.enqueue(new Callback<List<QuestionDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionDTO>> call, Response<List<QuestionDTO>> response) {


                List<QuestionDTO> questionDTOList = response.body();

                if (questionDTOList != null) {
                    QuestionAdapter.OnQuestionClickListener questionClickListener = new QuestionAdapter.OnQuestionClickListener() {
                        @Override
                        public void OnQuestionClick(QuestionDTO q,QuestionAdapter.ViewHolder holder) {


                            editQuestionIntent.putExtra("token",token);
                            System.out.println("puting in editQuestionIntent SurvId  = " + surveyId);
                            editQuestionIntent.putExtra("surveyId",surveyId);
                            editQuestionIntent.putExtra("questionId",q.getId());
                            System.out.println("Put  questionText " + q.getText());
                            editQuestionIntent.putExtra("questionText",q.getText());
                            startActivity(editQuestionIntent);

                        }
                    };
                    QuestionAdapter adapter = new QuestionAdapter(context,questionDTOList,questionClickListener);
                    recyclerViewQuestion.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<QuestionDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }





    private void getSurveyTypes(String token, Context context,long id) {

        Call<List<TypeDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getTypesOfSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<List<TypeDTO>>() {
            @Override
            public void onResponse(Call<List<TypeDTO>> call, Response<List<TypeDTO>> response) {
                List<TypeDTO> typeDTOList = response.body();
                if (typeDTOList != null) {
                    TypeAdapter.OnTypeClickListener surveyClickListener = new TypeAdapter.OnTypeClickListener() {
                        @Override
                        public void OnTypeClick(TypeDTO typeDTO) {


                        }
                    };
                    TypeAdapter adapter = new TypeAdapter(context,typeDTOList,surveyClickListener);
                    recyclerViewType.setAdapter(adapter);
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

    public void onCLickAddQuestion(View view){
        createQuestionIntent.putExtra("token",token);
        createQuestionIntent.putExtra("surveyId",surveyId);
        createQuestionIntent.putExtra("surveyName",getIntent().getExtras().get("surveyName").toString());
        createQuestionIntent.putExtra("surveyDesc",getIntent().getExtras().get("surveyDesc").toString());

        startActivity(createQuestionIntent);
    }
    public void onClickAddType(View view){
        createTypeIntent.putExtra("token",token);
        createTypeIntent.putExtra("surveyId",surveyId);
        createTypeIntent.putExtra("surveyName",getIntent().getExtras().get("surveyName").toString());
        createTypeIntent.putExtra("surveyDesc",getIntent().getExtras().get("surveyDesc").toString());

        startActivity(createTypeIntent);
    }

    public void onClickEditSurveyResults(View view){
        Intent editResults = new Intent(this,EditSurveyResults.class);
        editResults.putExtra("token",token);
        editResults.putExtra("surveyId",surveyId);
        startActivity(editResults);
    }

}
