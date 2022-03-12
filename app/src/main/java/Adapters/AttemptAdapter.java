package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.Date;
import java.util.List;

import DTO.ForAttempt.AttemptDTO;
import DTO.TypeDTO;

public class AttemptAdapter extends RecyclerView.Adapter<AttemptAdapter.ViewHolder> {

    public interface OnAttemptClickListener {
        void OnAttemptClick(AttemptDTO q);
    }

    public final AttemptAdapter.OnAttemptClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<AttemptDTO> attemptDTOList;

    public AttemptAdapter(Context context, List<AttemptDTO> attemptDTOList, AttemptAdapter.OnAttemptClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.attemptDTOList = attemptDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AttemptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_attempts_of_survey, parent, false);
        return new AttemptAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AttemptAdapter.ViewHolder holder, int position) {
        AttemptDTO attemptDTO = attemptDTOList.get(position);
        holder.textViewAttemptListTextResult.setText("Результат : \n" + attemptDTO.getResultText());


        Date date1 = attemptDTO.getCreated();
        Date date2 = attemptDTO.getEnded();

    try {


        long milliseconds = date2.getTime() - date1.getTime();


        // 1000 миллисекунд = 1 секунда
        int seconds = (int) (milliseconds / (1000));


        // 60 000 миллисекунд = 60 секунд = 1 минута
        int minutes = (int) (milliseconds / (60 * 1000));


        // 3 600 секунд = 60 минут = 1 час
        int hours = (int) (milliseconds / (60 * 60 * 1000));


        // 24 часа = 1 440 минут = 1 день
        int days = (int) (milliseconds / (24 * 60 * 60 * 1000));

        holder.textViewAttemptListTime.setText(seconds + " с " + minutes + " м");
    }
    catch (Exception e){
        holder.textViewAttemptListTime.setText("");
    }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnAttemptClick(attemptDTO);
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewAttemptListTextResult, textViewAttemptListTime;

        ViewHolder(View view) {
            super(view);
            textViewAttemptListTextResult = view.findViewById(R.id.textViewAttemptListTextResult);
            textViewAttemptListTime = view.findViewById(R.id.textViewAttemptListTime);
        }
    }


    @Override
    public int getItemCount() {
        if (attemptDTOList == null) {
            return 0;
        } else {
            return attemptDTOList.size();
        }
    }

}