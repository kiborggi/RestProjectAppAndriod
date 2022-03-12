package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.AnswerDTO;
import DTO.ForAttempt.AnswerForAttempt;

public class AnswerForAttemptAdapter extends RecyclerView.Adapter<AnswerForAttemptAdapter.ViewHolder>{
    public interface OnAnswerForAttemptAdapterClickListener{
        void OnAnswerForAttemptAdapterClick(AnswerForAttempt q,AnswerForAttemptAdapter.ViewHolder holder);
    }

    public final AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<AnswerForAttempt> answerDTOList;
    private Long idToBlue;

    public AnswerForAttemptAdapter(Context context, List<AnswerForAttempt> answerDTOList, AnswerForAttemptAdapter.OnAnswerForAttemptAdapterClickListener onClickListener,long idToBlue) {
        this.onClickListener = onClickListener;
        this.answerDTOList = answerDTOList;
        this.inflater = LayoutInflater.from(context);
        this.idToBlue = idToBlue;
    }

    @Override
    public AnswerForAttemptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_answer_for_attempt, parent, false);
        return new AnswerForAttemptAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerForAttemptAdapter.ViewHolder holder, int position) {
        AnswerForAttempt answerDTO = answerDTOList.get(position);
        holder.answerForAttemptText.setText(answerDTO.getText());
        if (idToBlue == answerDTO.getId()){
            holder.answerForAttemptText.setTextColor(Color.BLUE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnAnswerForAttemptAdapterClick(answerDTO,holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (answerDTOList == null){
            return 0;
        }
        else {
            return answerDTOList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       public final TextView answerForAttemptText;
        ViewHolder(View view){
            super(view);
            answerForAttemptText = view.findViewById(R.id.answerForAttemptText);

        }
    }

}