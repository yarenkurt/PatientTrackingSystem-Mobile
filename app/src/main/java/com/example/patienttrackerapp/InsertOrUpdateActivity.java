package com.example.patienttrackerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.MyQuestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsertOrUpdateActivity extends AppCompatActivity {

    private String array_spinner[];
    String _token;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText numeric;
    TextView question;
    Spinner multiple;
    Button save, cancel;
    String type;
    String questionType;
    Integer id;
    Integer questionId;
    Double score[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountDbManager _dbManager = new AccountDbManager(this);
        _token = _dbManager.getToken();
        createAnswerDialog();
    }

    public void createAnswerDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View answerPopUpView = getLayoutInflater().inflate(R.layout.activity_insert_or_update, null);
        question = answerPopUpView.findViewById(R.id.question);
        numeric = answerPopUpView.findViewById(R.id.numeric);
        multiple = answerPopUpView.findViewById(R.id.multiple);
        save = answerPopUpView.findViewById(R.id.saveButton);
        cancel = answerPopUpView.findViewById(R.id.cancelButton);

        type = getIntent().getStringExtra("type");
        id = getIntent().getIntExtra("id", 0);
        //Insertse id den yola çıkarak bütün ihtiyaç olan bilgileri alacak. O questionın answerlarını getirecek.
        getInsertData();
        dialogBuilder.setView(answerPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 setInsertData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertOrUpdateActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getInsertData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/PatientQuestions/Questions/" + id, null,
                (JSONObject response) -> {
                    try {
                        questionId = response.getInt("questionId");
                        question.setText(response.getString("description"));
                        questionType = response.getString("questionType");
                        if (questionType.toLowerCase().equals("multiple")) {
                            JSONObject answerPool = response.getJSONObject("answerPools");
                            JSONArray answers = answerPool.getJSONArray("$values");
                            array_spinner = new String[answers.length()];
                            score = new Double[answers.length()];
                            for (int i = 0; i < answers.length(); i++) {
                                JSONObject q = answers.getJSONObject(i);
                                if (q != null) {
                                    score[i] = q.getDouble("score");
                                    array_spinner[i] = q.getString("description");
                                }
                            }
                            ArrayAdapter adapter = new ArrayAdapter(this,
                                    android.R.layout.simple_spinner_item, array_spinner);
                            multiple.setAdapter(adapter);
                            multiple.setVisibility(View.VISIBLE);
                        } else {
                            numeric.setVisibility(View.VISIBLE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(InsertOrUpdateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setInsertData() {
        String answer ="";
        if (questionType.toLowerCase().equals("multiple")) {
            answer = multiple.getSelectedItem().toString();

        }else{
            answer = numeric.getText().toString();
        }

        JSONObject object = new JSONObject();
        try {
            object.put("questionId",questionId);
            object.put("score",10);
            object.put("answerDescription",answer);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Defaults.BASE_URL + "/api/PatientAnswers/InsertPatientAnswer", object,
                (JSONObject response) -> {
                    try {
                        if (Helper.stringToBoolean(response.getString("success"))) {
                           Intent intent = new Intent(InsertOrUpdateActivity.this,FormActivity.class);
                           startActivity(intent);

                        } else {
                            Toast.makeText(InsertOrUpdateActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(InsertOrUpdateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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



}