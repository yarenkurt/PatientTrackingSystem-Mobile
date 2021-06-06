package com.example.patienttrackerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.MyQuestionModel;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {
    Context context;
    ArrayList<MyQuestionModel> FormList;

    public FormAdapter(Context context, ArrayList<MyQuestionModel> FormList) {
        this.context = context;
        this.FormList = FormList;
    }


    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.one_row_form, parent, false);

        return new FormViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {

        MyQuestionModel question = FormList.get(position);
        if (question == null) return;
        holder.questionOrder.setText(Integer.toString(position + 1));
        holder.editQuestion.setTag(question.Id);
        holder.question.setText(question.Question);
        holder.insertQuestion.setTag(question.QuestionId);

        if (question.Answer.equals("")){
            holder.insertQuestion.setVisibility(View.VISIBLE);
            holder.answer.setText("Answer selection has not done yet.");
        }else
        {
            //holder.editQuestion.setVisibility(View.VISIBLE);
            holder.insertQuestion.setVisibility(View.INVISIBLE);
            holder.answer.setText(question.Answer);
        }
    }

    @Override
    public int getItemCount() {
        return FormList.size();
    }


    public static class FormViewHolder extends RecyclerView.ViewHolder {
        TextView questionOrder, question,answer;
        ImageButton editQuestion,insertQuestion;
        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            questionOrder = itemView.findViewById(R.id.questionOrder);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            editQuestion = itemView.findViewById(R.id.editQuestion);
            insertQuestion = itemView.findViewById(R.id.insertQuestion);
            editQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),InsertOrUpdateActivity.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("id", Helper.stringToInt(v.getTag().toString()));
                    v.getContext().startActivity(intent);
                }
            });
            insertQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),InsertOrUpdateActivity.class);
                    intent.putExtra("type","insert");
                    intent.putExtra("id", Helper.stringToInt(v.getTag().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}