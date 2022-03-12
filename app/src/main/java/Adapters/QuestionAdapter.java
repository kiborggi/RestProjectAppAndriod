package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.QuestionDTO;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{

    public interface OnQuestionClickListener{
        void OnQuestionClick(QuestionDTO q,QuestionAdapter.ViewHolder holder);
    }

    public final QuestionAdapter.OnQuestionClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<QuestionDTO> questionDTOList;

    public QuestionAdapter(Context context, List<QuestionDTO> surveyDTOList, QuestionAdapter.OnQuestionClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.questionDTOList = surveyDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_question, parent, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        QuestionDTO questionDTO = questionDTOList.get(position);
        holder.questionText.setText(questionDTO.getText());
        holder.numberOfAnswers.setText("Кол-во ответов: " + questionDTO.getNumberOfAnswers());
        holder.typeOfQuestion.setText("Тип: " + questionDTO.getTypeOfQuestion());
        if(questionDTO.getTypeOfQuestion().equals("NUMERIC")){
            holder.numberTo.setText("Макс: " + questionDTO.getNumTo());
            holder.numberFrom.setText("Мин: " + questionDTO.getNumFrom());
            holder.numberTo.setVisibility(View.VISIBLE);
            holder.numberFrom.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnQuestionClick(questionDTO, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (questionDTOList == null){
            return 0;
        }
        else {
            return questionDTOList.size();
        }
    }

    public void setSelected(){
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView questionText,numberOfAnswers,typeOfQuestion,numberTo,numberFrom;


        ViewHolder(View view){
            super(view);
            questionText = view.findViewById(R.id.textQuestion);
            numberOfAnswers = view.findViewById(R.id.numberOfAnswers);
            typeOfQuestion = view.findViewById(R.id.typeOfQuestion);
            numberTo= view.findViewById(R.id.numberTo);
            numberFrom=view.findViewById(R.id.numberFrom);
        }
    }
}
