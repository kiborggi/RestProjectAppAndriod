package Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import DTO.SurveyDTO;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder>{


    public interface OnSurveyClickListener{
        void OnSurveyClick(SurveyDTO surveyDTO);
    }

    public final OnSurveyClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<SurveyDTO> surveyDTOList;

    public SurveyAdapter(Context context, List<SurveyDTO> surveyDTOList, OnSurveyClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.surveyDTOList = surveyDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_survey, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyAdapter.ViewHolder holder, int position) {
        SurveyDTO surveyDTO = surveyDTOList.get(position);
        Drawable drawable = LoadImage("https://image.flaticon.com/icons/png/512/18/18436.png");

        holder.nameView.setText(surveyDTO.getName());
        holder.descritionView.setText(surveyDTO.getDescription());
        holder.descritionView.setText(surveyDTO.getDescription());

        holder.ownerNameView.setText(surveyDTO.getOwnerName());
        holder.numberOfAttmeptView.setText(Long.toString( surveyDTO.getNumberOfAttempts()));
        holder.numberOfQuestionView.setText(Long.toString( surveyDTO.getNumberOfQuestions()));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnSurveyClick(surveyDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (surveyDTOList == null){
            return 0;
        }
        else {
            return surveyDTOList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, descritionView,ownerNameView,numberOfAttmeptView,numberOfQuestionView;
        ViewHolder(View view){
            super(view);
            flagView = view.findViewById(R.id.imageView);
            nameView = view.findViewById(R.id.name);
            descritionView = view.findViewById(R.id.descrition);
            ownerNameView= view.findViewById(R.id.textViewOwnerName);
            numberOfAttmeptView= view.findViewById(R.id.textViewNumberOfAttempts);
            numberOfQuestionView= view.findViewById(R.id.textViewNumberOfQuestion);

        }
    }


    public Drawable LoadImage(String url)
    {

        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable b = Drawable.createFromStream(is, url);
            return b;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }

    }
}