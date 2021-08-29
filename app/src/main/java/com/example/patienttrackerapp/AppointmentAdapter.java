package com.example.patienttrackerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AppointmentInfo;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    Context context;
    ArrayList<AppointmentInfo> appointmentList;

    //region Constructor
    public AppointmentAdapter(Context context, ArrayList<AppointmentInfo> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }
    //endregion

    //region Initialization Items and Setting Their Values

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.one_row_item, parent, false);
        return new AppointmentViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {

        AppointmentInfo app = appointmentList.get(position);
        if (app==null) return;

        int id = app.id;
        String doctor = app.doctorName;
        String dept = app.deptName;
        Date date = app.date;
        holder.txtTitle.setText(String.format("Appointment %d", position + 1));
        holder.itemView.setTag(id);
        holder.txtDoctorName.setText(doctor);
        holder.txtDeptName.setText(dept);
        holder.txtDate.setText(Helper.dateTimeToString(date));
        if (!app.expired)
            holder.soon.setVisibility(View.VISIBLE);
        else
            holder.done.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }


    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDoctorName, txtDeptName, txtDate;
        ImageButton done,soon;
        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.appointmentOrder);
            txtDoctorName = itemView.findViewById(R.id.appointmentDoctor);
            txtDeptName = itemView.findViewById(R.id.appointmentDepartment);
            txtDate = itemView.findViewById(R.id.appointmentTime);
            done = itemView.findViewById(R.id.done);
            soon = itemView.findViewById(R.id.soon);
        }
    }
    //endregion
}