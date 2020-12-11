package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    Button btn;
    TextView textView;
    RadioGroup rg;
    int result;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

       toolbar = (Toolbar) findViewById(R.id.toolbarform);
        setSupportActionBar(toolbar);

        rg = (RadioGroup) findViewById(R.id.answers1);
        id = rg.getCheckedRadioButtonId();
        if(id == -1){
            Toast.makeText(FormActivity.this,"No Items Are Selected",Toast.LENGTH_SHORT);
        }
        else {
            switch (id){
                case R.id.answer1 : result+=1;
                case R.id.answer2 : result+=2;
                case R.id.answer3 : result+=3;
                case R.id.answer4 : result+=4;
                case R.id.answer5 : result+=5;
            }
        }

        btn = (Button) findViewById(R.id.saveButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormActivity.this,"Your answers are saved",Toast.LENGTH_SHORT);
            }
        });

    }
}