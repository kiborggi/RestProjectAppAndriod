package com.example.restproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Adapters.SurveyAdapter;
import DTO.SurveyDTO;
import Service.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySurveyActivity extends AppCompatActivity {

    List<SurveyDTO> listSurvey;
    RecyclerView recyclerView;
    Intent intentCreateSurvey;
    String token;
    Intent editSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_survey);

        token = getIntent().getExtras().get("token").toString();
        intentCreateSurvey = new Intent(this,CreateSurveyActivity.class);
        intentCreateSurvey.putExtra("token",token);

        editSurvey = new Intent(this,EditSurveyActivity.class);
        editSurvey.putExtra("token",token);

        recyclerView = findViewById(R.id.listOfMyAttempts);
        getUserSurveys(token,this);
    }



    private void getUserSurveys(String token, Context context) {

        Call<List<SurveyDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAllSurveysOfUser("Bearer_" + token);
        call1.enqueue(new Callback<List<SurveyDTO>>() {
            @Override
            public void onResponse(Call<List<SurveyDTO>> call, Response<List<SurveyDTO>> response) {
                List<SurveyDTO> suerveyListResp = response.body();
                if (suerveyListResp != null) {
                    SurveyAdapter.OnSurveyClickListener surveyClickListener = new SurveyAdapter.OnSurveyClickListener() {
                        @Override
                        public void OnSurveyClick(SurveyDTO surveyDTO) {

                            editSurvey.putExtra("surveyId",surveyDTO.getId());
                            editSurvey.putExtra("surveyName",surveyDTO.getName());
                            editSurvey.putExtra("surveyDesc",surveyDTO.getDescription());
                            startActivity(editSurvey);

                        }
                    };
                    SurveyAdapter adapter = new SurveyAdapter(context,suerveyListResp,surveyClickListener);
                    recyclerView.setAdapter(adapter);



                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            // this method is called
                            // when the item is moved.
                            return false;
                        }


                        @Override
                        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                            View itemView = viewHolder.itemView;

                           Drawable background =   itemView.getBackground();

                            if (dX > 0) {
                                background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                            }
                             else {
                                background.setBounds(0, 0, 0, 0);
                            }

                            background.draw(c);
                        }
                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            // this method is called when we swipe our item to right direction.
                            // on below line we are getting the item at a particular position.
                            SurveyDTO deletedCourse = suerveyListResp.get(viewHolder.getAdapterPosition());

                            // below line is to get the position
                            // of the item at that position.
                            int position = viewHolder.getAdapterPosition();

                            // this method is called when item is swiped.
                            // below line is to remove item from our array list.
                            suerveyListResp.remove(viewHolder.getAdapterPosition());

                            // below line is to notify our item is removed from adapter.
                            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                            // below line is to display our snackbar with action.
                            Snackbar.make(recyclerView, deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // adding on click listener to our action of snack bar.
                                    // below line is to add our item to array list with a position.
                                    suerveyListResp.add(position, deletedCourse);

                                    // below line is to notify item is
                                    // added to our adapter class.
                                    adapter.notifyItemInserted(position);
                                }
                            }).show();
                        }
                        // at last we are adding this
                        // to our recycler view.
                    }).attachToRecyclerView(recyclerView);
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

    public void onClickCreateSurvey(View view){
        startActivity(intentCreateSurvey);
    }

    @Override
    public void onBackPressed() {
      Intent  intetntMySurvey = new Intent(this, UserInfoActivity.class);
      intetntMySurvey.putExtra("token",token);

      startActivity(intetntMySurvey);
    }
}
