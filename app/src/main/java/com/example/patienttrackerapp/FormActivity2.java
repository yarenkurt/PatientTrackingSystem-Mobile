package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FormActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button ans1;
    Button ans2;
    Button ans3;
    Button ans4;
    Button ans5;
    Button save_btn;

    int totalScore = 0;
    int q_ind = 0;

    TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        ans1 = findViewById(R.id.ans_1); ans1.setOnClickListener(this);
        ans2 = findViewById(R.id.ans_2); ans2.setOnClickListener(this);
        ans3 = findViewById(R.id.ans_3); ans3.setOnClickListener(this);
        ans4 = findViewById(R.id.ans_4); ans4.setOnClickListener(this);
        ans5 = findViewById(R.id.ans_5); ans5.setOnClickListener(this);
        save_btn = findViewById(R.id.save_btn); save_btn.setOnClickListener(this);

        save_btn.setVisibility(View.INVISIBLE);

        question = findViewById(R.id.question);
        question.setText(FormQuestions.questions[q_ind]);
        q_ind++;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ans_1:
                totalScore += Integer.parseInt(ans1.getText().toString());
                changeQuestion();
                break;

            case R.id.ans_2:
                totalScore += Integer.parseInt(ans2.getText().toString());
                changeQuestion();
                break;


            case R.id.ans_3:
                totalScore += Integer.parseInt(ans3.getText().toString());
                changeQuestion();
                break;

            case R.id.ans_4:
                totalScore += Integer.parseInt(ans4.getText().toString());
                changeQuestion();
                break;

            case R.id.ans_5:
                totalScore += Integer.parseInt(ans5.getText().toString());
                changeQuestion();
                break;
        }

    }


    public void changeQuestion(){
        question.setText(FormQuestions.questions[q_ind]);
        if(q_ind == 9){
            save_btn.setVisibility(View.VISIBLE);
            System.out.println("Total Score : "+totalScore);
        }
        else{
            question.setText(FormQuestions.questions[q_ind]);
            q_ind++;
            System.out.println(q_ind);
        }
    }


}