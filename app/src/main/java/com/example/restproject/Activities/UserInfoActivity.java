package com.example.restproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import DTO.LoginReq;
import DTO.LoginResponse;
import DTO.UserResponse;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    TextView userView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userView = (TextView) findViewById(R.id.userView);
        String token = getIntent().getExtras().get("token").toString();
        getLogginedUserInfo(token);
    }


    private void getLogginedUserInfo(String token) {

        Call<UserResponse> call1 = NetworkService.getInstance()
                .getJSONApi().getLoggedUser("Bearer_" + token);

        call1.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();


                if (userResponse != null) {
                    userView.setText("Добрый день " + userResponse.firstName +" " +userResponse.lastName);

                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called User info", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}