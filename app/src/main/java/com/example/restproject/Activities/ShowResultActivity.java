package com.example.restproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restproject.R;

public class ShowResultActivity extends AppCompatActivity {
    TextView textViewShowResult;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        textViewShowResult = findViewById(R.id.textViewShowResult);
        String res = getIntent().getExtras().get("res").toString();
        textViewShowResult.setText(res);
        token = getIntent().getExtras().get("token").toString();
    }

    public void onClickGoHome(View view){
        Intent goHome = new Intent(this,UserInfoActivity.class);
        goHome.putExtra("token",token);
        startActivity(goHome);
    }
}
