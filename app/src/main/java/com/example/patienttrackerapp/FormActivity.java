package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AppointmentInfo;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.MyQuestionModel;
import com.example.patienttrackerapp.models.ReminderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String _token;
    ArrayList<MyQuestionModel> Questions;
    FormAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        toolbar=findViewById(R.id.formToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.formRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Questions = new ArrayList<>();

        AccountDbManager _dbManager = new AccountDbManager(this);
        _token = _dbManager.getToken();
        getMyQuestions();
    }
    private void getMyQuestions() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/PatientQuestions/MyQuestions", null,
                (JSONObject response) -> {

                    try {

                        JSONArray questions = response.getJSONArray("$values");
                        for (int i = 0; i < questions.length(); i++) {
                            JSONObject q = questions.getJSONObject(i);
                            if (q != null) {
                                MyQuestionModel item = new MyQuestionModel();
                                item.Id = q.getInt("id");
                                item.QuestionId = q.getInt("questionId");
                                item.Question = q.getString("question");
                                item.Answer = q.getString("answer");
                                Questions.add(item);
                                _adapter=new FormAdapter(recyclerView.getContext(),Questions);
                                recyclerView.setAdapter(_adapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(FormActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void save(View view) {
        Intent intent = new Intent(FormActivity.this,HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}