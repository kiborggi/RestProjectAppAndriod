package com.example.restproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DTO.LoginReq;
import DTO.LoginResponse;
import DTO.RegisterReq;
import Pojo.CommonMethod;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    EditText firstnameInput;
    EditText secondNameInput;
    EditText emailInput;
    Intent intentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        firstnameInput = (EditText) findViewById(R.id.firstnameInput);
        secondNameInput = (EditText) findViewById(R.id.secondNameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        intentUser = new Intent(this, UserInfoActivity.class);

    }

    public void onClickRegistr(View view){
        if (checkValidation()){
            RegisterReq registerReq = new RegisterReq();
            registerReq.username =usernameInput.getText().toString() ;
            registerReq.password = passwordInput.getText().toString() ;
            registerReq.firstName =firstnameInput.getText().toString()  ;
            registerReq.lastName = secondNameInput.getText().toString() ;
            registerReq.email =emailInput.getText().toString()  ;
            System.out.println("---------");
            regRetrofit2Api(registerReq);
        }
    }


    private void regRetrofit2Api(RegisterReq registerReq) {

        Call<LoginResponse> call1 =  NetworkService.getInstance()
                .getJSONApi().register(registerReq);
        System.out.println("fdsgssdfgsdfg");
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

        if (usernameInput.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("username Cannot be left blank", RegistrationActivity.this);
            return false;
        } else if (passwordInput.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("password Cannot be left blank", RegistrationActivity.this);
            return false;
        }
        else if (firstnameInput.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("name Cannot be left blank", RegistrationActivity.this);
            return false;
        } else if (secondNameInput.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("secname Cannot be left blank", RegistrationActivity.this);
            return false;
        }
        else if (emailInput.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("email Cannot be left blank", RegistrationActivity.this);
            return false;
        }


        return true;
    }
}