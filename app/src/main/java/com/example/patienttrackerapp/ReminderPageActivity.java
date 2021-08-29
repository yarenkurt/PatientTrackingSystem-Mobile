package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patienttrackerapp.db.ReminderDbManager;
import com.example.patienttrackerapp.models.ReminderModel;
import com.example.patienttrackerapp.results.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

        //region Inıtialization of Toolbar and RecyclerView
        toolbar = findViewById(R.id.reminderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.reminderRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //endregion

        _dbManager = new ReminderDbManager(this);
        GetReminders();

        //region Reminder Delete Behaviour
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ReminderPageActivity.this);
                alert.setTitle("Item Is Deleting, Are You Sure?");
                alert.setPositiveButton("Yes", (dialog, which) -> {
                    int id = Integer.parseInt(String.valueOf(viewHolder.itemView.getTag()));
                    Result result = _dbManager.delete(id);
                    if (!result.Success)
                        Toast.makeText(ReminderPageActivity.this, result.Message, Toast.LENGTH_SHORT).show();
                    _adapter.swapCursor(_dbManager.getAllByCursor());
                    Snackbar.make(viewHolder.itemView, result.Message, Snackbar.LENGTH_LONG).setAction(
                            result.Message, null).show();
                });
                alert.setNegativeButton("No", (dialog, which) ->
                        {
                            Toast.makeText(ReminderPageActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                            _adapter.swapCursor(_dbManager.getAllByCursor());
                        }
                );
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);
        //endregion

    }

    //region Directing AddReminderPage
    public void addReminder(View view) {
        Intent intent = new Intent(ReminderPageActivity.this, AddReminderActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion

    //region Showing All Reminders Method
    private void GetReminders() {
        _adapter = new ReminderAdapter(recyclerView.getContext(),_dbManager.getAllByCursor());
        recyclerView.setAdapter(_adapter);
    }
    //endregion


}