package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.AnswerDTO;

public class AnswerAdapter  extends RecyclerView.Adapter<AnswerAdapter.ViewHolder>{
    public interface OnAnswerClickListener{
        void OnAnswerClick(AnswerDTO q);
    }

    public final AnswerAdapter.OnAnswerClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<AnswerDTO> answerDTOList;

    public AnswerAdapter(Context context, List<AnswerDTO> answerDTOList, AnswerAdapter.OnAnswerClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.answerDTOList = answerDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_answer, parent, false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.ViewHolder holder, int position) {
        AnswerDTO answerDTO = answerDTOList.get(position);
        holder.answerText.setText(answerDTO.getText());
        holder.numberOfTypes.setText( "Количество типов: " + answerDTO.getNumberOfTypes());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnAnswerClick(answerDTO);
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

        final TextView answerText,numberOfTypes;
        ViewHolder(View view){
            super(view);
            answerText = view.findViewById(R.id.answerForAttemptText);
            numberOfTypes = view.findViewById(R.id.numberOfTypes);
        }
    }
}