package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restproject.R;

import java.util.List;

import DTO.QuestionDTO;
import DTO.TypeDTO;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{

    public interface OnTypeClickListener{
        void OnTypeClick(TypeDTO q);
    }

    public final TypeAdapter.OnTypeClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<TypeDTO> typeDTOList;

    public TypeAdapter(Context context, List<TypeDTO> typeDTOList, TypeAdapter.OnTypeClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.typeDTOList = typeDTOList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_type, parent, false);
        return new TypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypeDTO typeDTO = typeDTOList.get(position);
        holder.questionText.setText(typeDTO.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnTypeClick(typeDTO);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (typeDTOList == null){
            return 0;
        }
        else {
            return typeDTOList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView questionText;
        ViewHolder(View view){
            super(view);
            questionText = view.findViewById(R.id.typeText);
        }
    }
}
