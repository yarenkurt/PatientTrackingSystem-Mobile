package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Toolbar toolbar;
    Button saveFormBtn;

    RadioGroup answer1;
    RadioGroup answer2;
    RadioGroup answer3;
    RadioGroup answer4;
    RadioGroup answer5;
    RadioGroup answer6;
    RadioGroup answer7;
    RadioGroup answer8;
    RadioGroup answer9;
    RadioGroup answer10;


    int totalScore = 0;
    int checkedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

       toolbar = (Toolbar) findViewById(R.id.form_toolbar);
        setSupportActionBar(toolbar);

        answer1 = (RadioGroup) findViewById(R.id.answer1);
        answer2 = (RadioGroup) findViewById(R.id.answer2);
        answer3 = (RadioGroup) findViewById(R.id.answer3);
        answer4 = (RadioGroup) findViewById(R.id.answer4);
        answer5 = (RadioGroup) findViewById(R.id.answer5);
        answer6 = (RadioGroup) findViewById(R.id.answer6);
        answer7 = (RadioGroup) findViewById(R.id.answer7);
        answer8 = (RadioGroup) findViewById(R.id.answer8);
        answer9 = (RadioGroup) findViewById(R.id.answer9);
        answer10 = (RadioGroup) findViewById(R.id.answer10);


        answer1.setOnCheckedChangeListener(this);
        answer2.setOnCheckedChangeListener(this);
        answer3.setOnCheckedChangeListener(this);
        answer4.setOnCheckedChangeListener(this);
        answer5.setOnCheckedChangeListener(this);
        answer6.setOnCheckedChangeListener(this);
        answer7.setOnCheckedChangeListener(this);
        answer8.setOnCheckedChangeListener(this);
        answer9.setOnCheckedChangeListener(this);
        answer10.setOnCheckedChangeListener(this);


        saveFormBtn = (Button) findViewById(R.id.saveButton);
        saveFormBtn.setOnClickListener(this);

        saveFormBtn.getText();
        RadioButton btn = findViewById(R.id.a1);
        btn.getText();

    }

    //RECALCULATES TOTATL SCORE ACCORDING TO CHECKED RADIO BUTTON'S VALUE OF RADIO GROUP////
    public void getScore(int checkedButton){
        if(checkedButton == -1){
            Toast.makeText(FormActivity.this,"No Items Are Selected",Toast.LENGTH_SHORT);
        }
        else {
            RadioButton btn = findViewById(checkedButton);
            totalScore += Integer.parseInt(btn.getText().toString());
            System.out.println("Current Total : "+totalScore);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.answer1 :
                checkedButton = answer1.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer2 :
                checkedButton = answer2.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer3 :
                checkedButton = answer3.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer4 :
                checkedButton = answer4.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer5 :
                checkedButton = answer5.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer6 :
                checkedButton = answer6.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer7 :
                checkedButton = answer7.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer8 :
                checkedButton = answer8.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer9 :
                checkedButton = answer9.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;

            case R.id.answer10 :
                checkedButton = answer10.getCheckedRadioButtonId();
                getScore(checkedButton);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(FormActivity.this,"Your answers are saved",Toast.LENGTH_LONG).show();

    }
}