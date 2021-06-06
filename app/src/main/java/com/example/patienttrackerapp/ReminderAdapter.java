package com.example.patienttrackerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.patienttrackerapp.models.ReminderModel;
import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    Context context;
    ArrayList<ReminderModel> ReminderList;

    public ReminderAdapter(Context context, ArrayList<ReminderModel> ReminderList) {
        this.context = context;
        this.ReminderList = ReminderList;
    }


    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.one_row_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {

        ReminderModel app = ReminderList.get(position);
        if (app==null) return;

        holder.txtTitle.setText(app.title);
        holder.txtTitle.setTag(app.id);
        holder.txtDateTime.setText(app.startedDate+" "+app.startedTime);
        holder.txtRepeatInfo.setText(app.repeat?"Repeat " + app.interval:"No Repeat");
    }

    @Override
    public int getItemCount() {
        return ReminderList.size();
    }


    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDateTime, txtRepeatInfo;
        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.recycle_title);
            txtDateTime = itemView.findViewById(R.id.recycle_date_time);
            txtRepeatInfo = itemView.findViewById(R.id.recycle_repeat_info);
        }
    }
}