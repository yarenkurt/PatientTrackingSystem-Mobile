package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.apiservices.AuthenticationService;
import com.example.patienttrackerapp.apiservices.VolleyCallBack;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.results.Result;
import com.example.patienttrackerapp.results.SuccessResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText identityNumber;
    ProgressBar progressBar;
    Button btn;
    AuthenticationService authenticationService;
    String _token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        identityNumber = findViewById(R.id.forgetPasswordIdentityNumber);
        progressBar = findViewById(R.id.loading);
        btn = findViewById(R.id.forgetPasswordButton);
        authenticationService = new AuthenticationService(this);

        AccountDbManager _dbManager = new AccountDbManager(this);
        _token = _dbManager.getToken();
    }

    public void sendPassword(View view) {


        String iNumber = identityNumber.getText().toString();
        if (iNumber.length() != 11) {
            Toast.makeText(this, "identity number must be 11 character", Toast.LENGTH_SHORT).show();
            return;
        }
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        findViewById(R.id.forgetPasswordButton).setVisibility(View.INVISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/Authentication/ForgotPassword?identityNumber=" + iNumber, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        boolean success = false;
                        String message = "";

                        try {
                            success = response.getBoolean("success");
                            message = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(ForgetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (success) {

                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            intent.putExtra("message",message);
                            startActivity(intent);
                            finish();
                        }
                        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                        findViewById(R.id.forgetPasswordButton).setVisibility(View.VISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(ForgetPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                findViewById(R.id.forgetPasswordButton).setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}