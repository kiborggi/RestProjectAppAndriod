package com.example.restproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.restproject.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapters.SurveyAdapter;
import DTO.SurveyDTO;
import DTO.UserResponse;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Adapters.SurveyAdapter.*;

public class UserInfoActivity extends AppCompatActivity {
    TextView userView;
    List<SurveyDTO> listSurvey;
    SwipeableRecyclerView recyclerView;
    Intent intetntMySurvey;
    String token;
    Intent surveyAttempt;
    Context context ;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        context = this;
        setContentView(R.layout.activity_user_info);
 
        intetntMySurvey = new Intent(this, MySurveyActivity.class);
        surveyAttempt = new Intent(this,SurveyAttemptActivity.class);
        userView = (TextView) findViewById(R.id.userView);
         token = getIntent().getExtras().get("token").toString();
        surveyAttempt.putExtra("token",token);
        getLogginedUserInfo(token);
        recyclerView = findViewById(R.id.listOfMyAttempts);
        getAllSurveys(token,this);





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

    private void getAllSurveys(String token, Context context) {

        Call<List<SurveyDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAllSurveys("Bearer_" + token);
        call1.enqueue(new Callback<List<SurveyDTO>>() {
            @Override
            public void onResponse(Call<List<SurveyDTO>> call, Response<List<SurveyDTO>> response) {
                List<SurveyDTO> suerveyListResp = response.body();
                if (suerveyListResp != null) {
                    SurveyAdapter.OnSurveyClickListener surveyClickListener = new OnSurveyClickListener() {
                        @Override
                        public void OnSurveyClick(SurveyDTO surveyDTO) {
                            surveyAttempt.putExtra("surveyId",surveyDTO.getId());

                            startActivity(surveyAttempt);
                        }
                    };

                    SurveyAdapter adapter = new SurveyAdapter(context,suerveyListResp,surveyClickListener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setListener(new SwipeLeftRightCallback.Listener() {
                        @Override
                        public void onSwipedLeft(int position) {
                            SurveyDTO deletedSurvey =  suerveyListResp.remove(position);
                            adapter.notifyDataSetChanged();
                            Snackbar.make(recyclerView, deletedSurvey.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // adding on click listener to our action of snack bar.
                                    // below line is to add our item to array list with a position.
                                    suerveyListResp.add(position, deletedSurvey);

                                    // below line is to notify item is
                                    // added to our adapter class.
                                    adapter.notifyItemInserted(position);
                                }

                            }).show();
                        }

                        @Override
                        public void onSwipedRight(int position) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SurveyDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });
    }

    public void onClickMySurvey(View view){
        intetntMySurvey.putExtra("token",token);

        startActivity(intetntMySurvey);

    }
    @Override
    public void onBackPressed() {
       Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}