package com.example.restproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restproject.R;

import Pojo.CommonMethod;
import DTO.LoginReq;
import DTO.LoginResponse;
import Service.NetworkService;
import retrofit2.*;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText userName;
    EditText userPassword;
    Intent intentUser ;
    Intent intentReg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.editTextTextPassword);
        intentUser = new Intent(this, UserInfoActivity.class);
        TextView textView = (TextView) findViewById(R.id.text_view_id);
        intentUser = new Intent(this, UserInfoActivity.class);
        intentReg = new Intent(this, RegistrationActivity.class);
    }

    public void onClick(View view){
        String userNameStr = userName.getText().toString();
        String userPassStr = userPassword.getText().toString();
        if (checkValidation()){

                loginRetrofit2Api(userNameStr,userPassStr);
        }
    }
    public void onClinkReg(View view) {

        startActivity(intentReg);
    }


        private void loginRetrofit2Api(String userId, String password) {
        final LoginReq login = new LoginReq(userId, password);
        Call<LoginResponse> call1 =  NetworkService.getInstance()
                .getJSONApi().login(login);

        call1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();


                if (loginResponse != null) {
                    System.out.println(loginResponse.token);
                    intentUser.putExtra("token",loginResponse.token);
                    startActivity(intentUser);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }









    public boolean checkValidation() {

        if (userName.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("UserId Cannot be left blank", MainActivity.this);
            return false;
        } else if (userPassword.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("password Cannot be left blank", MainActivity.this);
            return false;
        }
        return true;
    }
}