package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import Adapters.AnswerForAttemptAdapter;
import Adapters.AnswerTypeValueAdapter;
import Adapters.QuestionAdapter;
import DTO.ForAttempt.AnswerForAttempt;
import DTO.ForAttempt.AttemptDTO;
import DTO.ForAttempt.AttemptToSend;
import DTO.ForAttempt.QuestionForAttempt;
import DTO.ForAttempt.QuestionToSend;
import DTO.QuestionDTO;
import DTO.SurveyDTO;
import DTO.TmpDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunAttemptActivity extends AppCompatActivity {
    long surveyId;
    String token;

    TextView textViewQuestionText;

    AttemptDTO attemptDTO;
    ArrayList<QuestionForAttempt> questionList;
    Iterator<QuestionForAttempt> questionIterator;
    QuestionForAttempt qurrentQuestion;
    Button runAttemptButton;
    AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener answerClickListener;
    RecyclerView recyclerAnswerForAttemptList;
    AttemptToSend attemptToSend;
    TreeSet<Long> pickedAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_attempt);

         textViewQuestionText = findViewById(R.id.textViewQuestionText);
        recyclerAnswerForAttemptList = findViewById(R.id.answerForAttemptList);

        attemptToSend = new AttemptToSend();

        runAttemptButton = findViewById(R.id.runAttemptButton);
        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString());
        token = getIntent().getExtras().get("token").toString();
        getSurvey(token,surveyId);
    }



    public void Init(AttemptDTO a){
        this.attemptDTO = a;
        attemptToSend.setAttemptId(a.getId());
        attemptToSend.setSurveyId(a.getSurveyId());
        attemptToSend.setListQuestion(new ArrayList<QuestionToSend>());

         this.questionList = (ArrayList<QuestionForAttempt>) a.getSurveyFotAttempt().getQuestionForAttemptList();
         this.questionIterator = questionList.iterator();
         pickedAnswers = new TreeSet<>();
         if(questionIterator.hasNext()) {
             qurrentQuestion = questionIterator.next();
         }

        textViewQuestionText.setText(qurrentQuestion.getText());



        ArrayList<AnswerForAttempt> answerForAttemptList = (ArrayList<AnswerForAttempt>) qurrentQuestion.getAnswerForAttemptList();
        answerClickListener = new AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener() {
            @Override
            public void OnAnswerForAttemptAdapterClick(AnswerForAttempt q, AnswerForAttemptAdapter.ViewHolder holder) {
                    if (pickedAnswers.add(q.getId()) == true){
                            holder.answerForAttemptText.setTextColor(Color.BLUE);
                        holder.answerForAttemptText.setTypeface(null, Typeface.BOLD);
                    }
                    else{
                    pickedAnswers.remove(q.getId());
                    holder.answerForAttemptText.setTextColor(Color.BLACK);
                        holder.answerForAttemptText.setTypeface(null, Typeface.NORMAL);
                }
            }
        };
        AnswerForAttemptAdapter adapter = new AnswerForAttemptAdapter(this,answerForAttemptList,answerClickListener);
        recyclerAnswerForAttemptList.setAdapter(adapter);

    }


    public void onClickInit(View view){
        QuestionToSend questionToSend = new QuestionToSend();

        questionToSend.setQuestionId(qurrentQuestion.getId());

        questionToSend.setListAnswerId( pickedAnswers);

        attemptToSend.getListQuestion().add(questionToSend);
        for (Long l : pickedAnswers){
            System.out.println(l);
        }

        pickedAnswers = new TreeSet<>();
        if (questionIterator.hasNext()){
            qurrentQuestion = questionIterator.next();
        }
        else{
            runAttemptButton.setText("Завершить");
            Gson gson = new Gson();
            System.out.println(gson.toJson(attemptToSend));
            sendAttempt(token,attemptToSend,this);
        }

        textViewQuestionText.setText(qurrentQuestion.getText());
        ArrayList<AnswerForAttempt> answerForAttemptList = (ArrayList<AnswerForAttempt>) qurrentQuestion.getAnswerForAttemptList();
        answerClickListener = new AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener() {
            @Override
            public void OnAnswerForAttemptAdapterClick(AnswerForAttempt q, AnswerForAttemptAdapter.ViewHolder holder) {
                if (pickedAnswers.add(q.getId()) == true){
                    holder.answerForAttemptText.setTextColor(Color.BLUE);
                }
                else{
                    pickedAnswers.remove(q.getId());
                    holder.answerForAttemptText.setTextColor(Color.BLACK);
                }
            }

        };

        AnswerForAttemptAdapter adapter = new AnswerForAttemptAdapter(this,answerForAttemptList,answerClickListener);
        recyclerAnswerForAttemptList.setAdapter(adapter);

    }


    private void getSurvey(String token,long id) {

        Call<AttemptDTO> call1 = NetworkService.getInstance()
                .getJSONApi().startAttemptForSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<AttemptDTO>() {
            @Override
            public void onResponse(Call<AttemptDTO> call, Response<AttemptDTO> response) {
                AttemptDTO attemptDTO = response.body();
                if (attemptDTO != null) {
                    Init(attemptDTO);
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<AttemptDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void sendAttempt(String token, AttemptToSend attemptToSend, Context context) {

        Call<TmpDTO> call1 = NetworkService.getInstance()
                .getJSONApi().receiveAttemptResults("Bearer_" + token,attemptToSend);
        call1.enqueue(new Callback<TmpDTO>() {
            @Override
            public void onResponse(Call<TmpDTO> call, Response<TmpDTO> response) {
                TmpDTO res = response.body();
                if (res != null) {
                    System.out.println(res.getText());
                    Intent showResIntent = new Intent(context,ShowResultActivity.class);
                    showResIntent.putExtra("token",token);
                    showResIntent.putExtra("res",res.getText());
                    startActivity(showResIntent);
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<TmpDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }


}
