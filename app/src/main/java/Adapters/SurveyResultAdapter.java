package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.SurveyResultToReceive;

public class SurveyResultAdapter extends RecyclerView.Adapter<SurveyResultAdapter.ViewHolder>{
    public interface OnSurveyResultListener{
        void OnSurevyResultClick(SurveyResultToReceive q);
    }

    public final SurveyResultAdapter.OnSurveyResultListener onClickListener;
    private final LayoutInflater inflater;
    private final List<SurveyResultToReceive> surveyResultList;

    public SurveyResultAdapter(Context context, List<SurveyResultToReceive> surveyResultList, SurveyResultAdapter.OnSurveyResultListener onClickListener) {
        this.onClickListener = onClickListener;
        this.surveyResultList = surveyResultList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SurveyResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_survey_result, parent, false);
        return new SurveyResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyResultAdapter.ViewHolder holder, int position) {
        SurveyResultToReceive surveyResult = surveyResultList.get(position);
        holder.surveyResultText.setText(surveyResult.getText());
        holder.surveyResultValueFrom.setText("При знач. от:" + surveyResult.getValueFrom());
        holder.surveyResultValueTo.setText("При знач. до:" +surveyResult.getValueTo());
        holder.surveyResultTypeText.setText("Тип: " + surveyResult.getTypeText());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnSurevyResultClick(surveyResult);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (surveyResultList == null){
            return 0;
        }
        else {
            return surveyResultList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView surveyResultText,surveyResultValueFrom,surveyResultValueTo,surveyResultTypeText;
        ViewHolder(View view){
            super(view);
            surveyResultText = view.findViewById(R.id.surveyResultText);
            surveyResultValueFrom = view.findViewById(R.id.surveyResultValueFrom);
            surveyResultValueTo = view.findViewById(R.id.surveyResultValueTo);
            surveyResultTypeText = view.findViewById(R.id.surveyResultTypeText);
        }
    }
}