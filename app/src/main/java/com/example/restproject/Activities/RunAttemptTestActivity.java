package com.example.restproject.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import Adapters.AnswerForAttemptAdapter;
import DTO.ForAttempt.AnswerForAttempt;
import DTO.ForAttempt.AttemptDTO;
import DTO.ForAttempt.AttemptToSend;
import DTO.ForAttempt.QuestionForAttempt;
import DTO.ForAttempt.QuestionToSend;
import DTO.TmpDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunAttemptTestActivity extends AppCompatActivity {

    long surveyId;
    String token;
    AttemptDTO attempt;
    Iterator<QuestionForAttempt> currentQuestionIterator;
    QuestionForAttempt currentQuestion;
    AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener answerClickListener;
    TreeSet<Long> pickedAnswers;
    AttemptToSend attemptToSend;
    Context context ;

    float numValue = 0;
    Long idToBlue = 0l;

    RecyclerView recyclerAnswerForAttemptList;
    NumberPicker numberPicker;
    TextView textViewInstruction;
    TextView textViewQuestion;

    Button attemptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_attempt_test);
        attemptToSend = new AttemptToSend();
        pickedAnswers = new TreeSet<>();

        surveyId = Long.parseLong( getIntent().getExtras().get("surveyId").toString());
        token = getIntent().getExtras().get("token").toString();

        recyclerAnswerForAttemptList = findViewById(R.id.answerForAttemptList);
        getAttempt(token,surveyId);
        context = this;

        numberPicker = findViewById(R.id.numberPicker);
        textViewQuestion= findViewById(R.id.textViewQuestion);
        textViewInstruction= findViewById(R.id.textViewInstruction);
        attemptButton = findViewById(R.id.attemptButton);

    }

    private void InitAttempt(){
        attemptToSend.setAttemptId(attempt.getId());
        attemptToSend.setSurveyId(attempt.getSurveyId());
        attemptToSend.setListQuestion(new ArrayList<QuestionToSend>());


        currentQuestionIterator = attempt.getSurveyFotAttempt().getQuestionForAttemptList().iterator();
        if (currentQuestionIterator.hasNext()){
            currentQuestion = currentQuestionIterator.next();
        }
        setTextViewsContenet();
       switch (currentQuestion.getTypeOfQuestion()){
           case("MUlTY"):


               initMultyQuestion();
               break;

           case("SINGLE"):

               initSingleQuestion(0);
               break;

           case("NUMERIC"):
               initNumericQuestion();

               break;


       }
    }







    public void onClickNextQuestion(View view){

        QuestionToSend questionToSend = new QuestionToSend();
        questionToSend.setQuestionId(currentQuestion.getId());
        questionToSend.setTypeOfQuestion(currentQuestion.getTypeOfQuestion());
        questionToSend.setListAnswerId(pickedAnswers);
        questionToSend.setNumValue(numberPicker.getValue());
        attemptToSend.getListQuestion().add(questionToSend);


        if (currentQuestionIterator.hasNext()){
            currentQuestion = currentQuestionIterator.next();
            setTextViewsContenet();
        switch (currentQuestion.getTypeOfQuestion()) {
            case ("MUlTY"):
                pickedAnswers = new TreeSet<>();
                initMultyQuestion();
                break;

            case ("SINGLE"):
                pickedAnswers = new TreeSet<>();
                initSingleQuestion(0);
                break;

            case ("NUMERIC"):
                initNumericQuestion();
                break;
        }


        }
        else {
            Gson gson = new Gson();
            System.out.println(gson.toJson(attemptToSend));
            sendAttempt(token,attemptToSend,this);
        }
    }



    private void initSingleQuestion(long idToBlue){
        recyclerAnswerForAttemptList.setVisibility(View.VISIBLE);
        numberPicker.setVisibility(View.INVISIBLE);
        ArrayList<AnswerForAttempt> answerForAttemptList = (ArrayList<AnswerForAttempt>) currentQuestion.getAnswerForAttemptList();
        answerClickListener = new AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener() {
            @Override
            public void OnAnswerForAttemptAdapterClick(AnswerForAttempt q, AnswerForAttemptAdapter.ViewHolder holder) {

                if (pickedAnswers.size() == 0){
                    pickedAnswers.add(q.getId());
                    holder.answerForAttemptText.setTextColor(Color.BLUE);



                    for (long id : pickedAnswers){
                        System.out.println(id);
                    }


                }else
                if(pickedAnswers.add(q.getId()) == false) {
                    holder.answerForAttemptText.setTextColor(Color.BLACK);
                    pickedAnswers.remove(q.getId());

                    for (long id : pickedAnswers){
                        System.out.println(id);
                    }

                }else
                if(pickedAnswers.size() >0){


                    pickedAnswers = new TreeSet<>();
                    pickedAnswers.add(q.getId());


                    for (long id : pickedAnswers){
                        System.out.println(id);
                    }


                    initSingleQuestion(q.getId());

                }

            }

        };

        AnswerForAttemptAdapter adapter = new AnswerForAttemptAdapter(this,answerForAttemptList,answerClickListener,idToBlue);
        recyclerAnswerForAttemptList.setAdapter(adapter);

    }

    private void initMultyQuestion(){
        recyclerAnswerForAttemptList.setVisibility(View.VISIBLE);
        numberPicker.setVisibility(View.INVISIBLE);
        ArrayList<AnswerForAttempt> answerForAttemptList = (ArrayList<AnswerForAttempt>) currentQuestion.getAnswerForAttemptList();
        answerClickListener = new AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener() {
            @Override
            public void OnAnswerForAttemptAdapterClick(AnswerForAttempt q, AnswerForAttemptAdapter.ViewHolder holder) {


                if (pickedAnswers.add(q.getId()) == true) {
                    holder.answerForAttemptText.setTextColor(Color.BLUE);

                } else {
                    pickedAnswers.remove(q.getId());
                    holder.answerForAttemptText.setTextColor(Color.BLACK);

                }
            }

        };
        AnswerForAttemptAdapter adapter = new AnswerForAttemptAdapter(this,answerForAttemptList,answerClickListener,0);
        recyclerAnswerForAttemptList.setAdapter(adapter);
    }
    private void initNumericQuestion(){
        numberPicker.setMinValue( (int) currentQuestion.getNumFrom());
        numberPicker.setMaxValue((int) currentQuestion.getNumTo() );


        recyclerAnswerForAttemptList.setVisibility(View.INVISIBLE);
        numberPicker.setVisibility(View.VISIBLE);
        for (AnswerForAttempt answerForAttempt : currentQuestion.getAnswerForAttemptList()){
            pickedAnswers.add(answerForAttempt.getId());
        }

       /* numberPicker.setMinValue( 0);
        numberPicker.setMaxValue(10 );*/

    }
    private void getAttempt(String token,long id) {

        Call<AttemptDTO> call1 = NetworkService.getInstance()
                .getJSONApi().startAttemptForSurvey("Bearer_" + token,id);
        call1.enqueue(new Callback<AttemptDTO>() {
            @Override
            public void onResponse(Call<AttemptDTO> call, Response<AttemptDTO> response) {
                AttemptDTO attemptDTO = response.body();
                if (attemptDTO != null) {
                    attempt  = attemptDTO;
                    InitAttempt();
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

    private void setTextViewsContenet(){
        textViewQuestion.setText(currentQuestion.getText());
        if (currentQuestion.getTypeOfQuestion().equals("NUMERIC")){
            textViewInstruction.setText("Выберите число от " +  currentQuestion.getNumFrom() +" до "+ currentQuestion.getNumTo() );
        }
        else if (currentQuestion.getTypeOfQuestion().equals("SINGLE")){
            textViewInstruction.setText("Выберите один из ответов" );
        }
        else if (currentQuestion.getTypeOfQuestion().equals("MUlTY")){

            textViewInstruction.setText("Выберите один или несколько ответов" );
        }
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Вы дейстивтельно хотите прервать выполнеие теста?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intetntMySurvey = new Intent(context, UserInfoActivity.class);
                        intetntMySurvey.putExtra("token",token);
                        startActivity(intetntMySurvey);
                    }
                });

        builder1.setNegativeButton(
                "Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
