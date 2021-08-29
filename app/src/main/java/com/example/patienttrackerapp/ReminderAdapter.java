package com.example.patienttrackerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patienttrackerapp.db.options.ReminderOptions;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.ReminderModel;
import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    Context context;
    Cursor cursor;

    public ReminderAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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

        if (!cursor.moveToPosition(position)) {
            return;
        }
       String title= cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_TITLE));
        holder.txtTitle.setText(title);
        long id=cursor.getLong(cursor.getColumnIndex(ReminderOptions._ID));
        holder.txtTitle.setTag(id);
        holder.itemView.setTag(id);
        String startedDate= cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_DATE));
        String startedTime= cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_TIME));
        holder.txtDateTime.setText(startedDate+" "+startedTime);
        Boolean repeat= Helper.stringToBoolean(cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_IS_REPEAT)));
        String interval= cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_REPEAT_INTERVAL));
        holder.txtRepeatInfo.setText(repeat?"Repeat " + interval:"No Repeat");
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
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

    public void swapCursor(Cursor newCursor){
        if(cursor!=null)
            cursor.close();
        cursor=newCursor;
        if(newCursor !=null)
            notifyDataSetChanged();
    }

}