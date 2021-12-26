package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.AnswerTypeValueDTO;
import DTO.QuestionDTO;

public class AnswerTypeValueAdapter extends RecyclerView.Adapter<AnswerTypeValueAdapter.ViewHolder>{


    public interface OnAnswerTypeValueClickListener{
        void onAnswerTypeValueClick(AnswerTypeValueDTO q);
    }

    public final AnswerTypeValueAdapter.OnAnswerTypeValueClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<AnswerTypeValueDTO> answerTypeValueDTOList;

    public AnswerTypeValueAdapter(Context context, List<AnswerTypeValueDTO> answerTypeValueDTOList, AnswerTypeValueAdapter.OnAnswerTypeValueClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.answerTypeValueDTOList = answerTypeValueDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AnswerTypeValueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_answer_type_value, parent, false);
        return new AnswerTypeValueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerTypeValueAdapter.ViewHolder holder, int position) {
        AnswerTypeValueDTO answerTypeValueDTO = answerTypeValueDTOList.get(position);
        holder.textAnswer.setText("Ответ: " + answerTypeValueDTO.ansewerText);
        holder.answerType.setText("Тип: " + answerTypeValueDTO.typeText);
        holder.answerTypeValue.setText("Значение: " + answerTypeValueDTO.getValue());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onAnswerTypeValueClick(answerTypeValueDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (answerTypeValueDTOList == null){
            return 0;
        }
        else {
            return answerTypeValueDTOList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textAnswer,answerType,answerTypeValue;
        ViewHolder(View view){
            super(view);
            textAnswer = view.findViewById(R.id.textAnswer);
            answerType = view.findViewById(R.id.answerType);
            answerTypeValue = view.findViewById(R.id.answerTypeValue);
        }
    }
}
