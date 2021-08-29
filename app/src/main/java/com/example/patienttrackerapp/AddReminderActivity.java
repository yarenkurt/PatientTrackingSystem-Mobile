package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.patienttrackerapp.db.ReminderDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.ReminderModel;
import com.example.patienttrackerapp.reminders.DatePickerFragment;
import com.example.patienttrackerapp.reminders.TimePickerFragment;
import com.example.patienttrackerapp.results.Result;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private String array_spinner[];
    ReminderDbManager _dbManager;
    Spinner _spinner;
    EditText title;
    TextView date, time;
    Switch _switch;
    RelativeLayout reminder_repeat_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        //region Items Initialization
        title = findViewById(R.id.reminder_title);
        date = findViewById(R.id.set_date);
        time = findViewById(R.id.set_time);
        reminder_repeat_no=findViewById(R.id.reminder_repeat_no);
        _switch = findViewById(R.id.repeat_switch);
        _spinner = findViewById(R.id.spinner);
        reminder_repeat_no.setVisibility(View.INVISIBLE);
        array_spinner = new String[3];
        array_spinner[0] = "8 Hours";
        array_spinner[1] = "12 Hours";
        array_spinner[2] = "24 Hours";
        //endregion
        //region Selection Date and Time
        date.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        time.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });
        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  reminder_repeat_no.setVisibility(View.VISIBLE);
              }
              else{
                  reminder_repeat_no.setVisibility(View.INVISIBLE);
              }
          }
         });
        //endregion

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        _spinner.setAdapter(adapter);
        _dbManager = new ReminderDbManager(this);
    }

    //region Reminder Methods
    public void Save(View view) {

        String titleValue = title.getText().toString();
        String dateValue = date.getText().toString();
        String timeValue = time.getText().toString();
        Date nextRunDate = Helper.stringToDateTime(dateValue + " " + timeValue);
        boolean repeat = _switch.isChecked();
        int interval = Helper.stringToInt(_spinner.getSelectedItem().toString().replace(" Hours", ""));


        Result result = _dbManager.insert(new ReminderModel(
                0,
                titleValue,
                dateValue,
                timeValue,
                repeat,
                interval,
                Helper.dateTimeToString(nextRunDate)
        ));
        if (!result.Success) {
            Toast.makeText(this, result.Message, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(AddReminderActivity.this, ReminderPageActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String _day = Integer.valueOf(dayOfMonth).toString().length() > 1 ? Integer.valueOf(dayOfMonth).toString() : "0" + Integer.valueOf(dayOfMonth).toString();
        String _month = Integer.valueOf(month).toString().length() > 1 ? Integer.valueOf(month).toString() : "0" + Integer.valueOf(month).toString();
        String _year = Integer.valueOf(year).toString();

        String result = _year + "-" + _month + "-" + _day;
        date.setText(result);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String _hour = Integer.valueOf(hourOfDay).toString().length() > 1 ? Integer.valueOf(hourOfDay).toString() : "0" + Integer.valueOf(hourOfDay).toString();
        String _minute = Integer.valueOf(minute).toString().length() > 1 ? Integer.valueOf(minute).toString() : "0" + Integer.valueOf(minute).toString();
        time.setText(_hour + ":" + _minute);


    }
    //endregion

    public void setAlarm(Calendar alarmCalendar){
        Toast.makeText(getApplicationContext(),"Alarm is set",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getBaseContext(),AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(),1,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),pendingIntent);
    }
}