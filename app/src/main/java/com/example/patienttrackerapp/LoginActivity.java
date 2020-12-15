package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button sign_in_btn;
    Button sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_in_btn = findViewById(R.id.signin);
        sign_up_btn.setOnClickListener(this);
        sign_up_btn = findViewById(R.id.signup);
        sign_up_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin:
                /////////
                break;

            case R.id.signup:
                Intent intentToSingUp = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intentToSingUp);
                break;
        }
    }
}