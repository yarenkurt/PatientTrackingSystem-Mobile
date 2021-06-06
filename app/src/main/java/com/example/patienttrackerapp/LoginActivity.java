package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.patienttrackerapp.apiservices.AuthenticationService;
import com.example.patienttrackerapp.apiservices.VolleyCallBack;
import com.example.patienttrackerapp.results.Result;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText userName,password;
    private Button btn_login;
    ProgressBar progressBar;
    AuthenticationService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declaration of items
        userName=findViewById(R.id.loginIdentityNumber);
        password=findViewById(R.id.loginPassword);
        btn_login=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.loading);

        Intent intent = getIntent();
        if (intent.hasExtra("message"))
        {
            String message=intent.getStringExtra("message");
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        //Behavior of button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Login();
            }
        });

        service=new AuthenticationService(getApplicationContext());
    }
    private void Login(){
        progressBar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.INVISIBLE);
        String mUserName= userName.getText().toString().trim();
        String mPassword= password.getText().toString().trim();
        if(mUserName.isEmpty()){
            userName.setError("Please insert identity number!");
            userName.requestFocus();
            return;
        }
        if(mPassword.isEmpty()){
            password.setError("Please insert password!");
            password.requestFocus();
            return;
        }

        JSONObject object = new JSONObject();
        try {
            object.put("userName",mUserName);
            object.put("password",mPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        service.Login(object, new VolleyCallBack() {
                    @Override
                    public void onSuccess(Result result) {
                        Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Result result) {
                        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                        findViewById(R.id.login_btn).setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, result.Message, Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }



}