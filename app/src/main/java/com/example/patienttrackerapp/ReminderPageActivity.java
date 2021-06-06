package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.patienttrackerapp.db.ReminderDbManager;
import com.example.patienttrackerapp.models.ReminderModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReminderPageActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ReminderDbManager _dbManager;
    ReminderAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);

        toolbar = findViewById(R.id.reminderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.reminderRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _dbManager = new ReminderDbManager(this);
        GetReminders();
    }

    public void addReminder(View view) {
        Intent intent = new Intent(ReminderPageActivity.this, AddReminderActivity.class);
        startActivity(intent);
        finish();
    }

    private void GetReminders() {
        ArrayList<ReminderModel> entities= _dbManager.getAll();
        _adapter = new ReminderAdapter(recyclerView.getContext(),entities);
        recyclerView.setAdapter(_adapter);
    }

}