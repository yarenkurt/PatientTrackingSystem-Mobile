package com.example.patienttrackerapp.apiservices;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AnswerInfo;
import com.example.patienttrackerapp.models.AppointmentInfo;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.MultipleChoiceQuestionInfo;
import com.example.patienttrackerapp.models.NumericQuestion;
import com.example.patienttrackerapp.models.PatientInfo;
import com.example.patienttrackerapp.models.TokenInfo;
import com.example.patienttrackerapp.results.ErrorResult;
import com.example.patienttrackerapp.results.SuccessResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormService {
    private static String BASE_URL= Defaults.BASE_URL+"/api";
    private Context _context;
    private final AccountDbManager _dbManager;
    public FormService(Context context){
        _context=context;
        _dbManager=new AccountDbManager(context);
    }

    public void GetAllQuestions(int patientId){
        ArrayList<MultipleChoiceQuestionInfo> questionList = new ArrayList<MultipleChoiceQuestionInfo>();
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL+"/PatientQuestions",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray questions = response.getJSONArray("$values");
                            for (int i = 0; i < questions.length(); i++) {
                                JSONObject question = questions.getJSONObject(i);
                                if(Helper.stringToInt(question.getString("questionType"))==1){
                                    MultipleChoiceQuestionInfo mcQuestion = new MultipleChoiceQuestionInfo();
                                    mcQuestion.description = question.getString("description");
                                    mcQuestion.id = Helper.stringToInt(question.getString("id"));
                                    PatientInfo.mcQuestions.add(mcQuestion);
                                }else{
                                    NumericQuestion numericQuestion = new NumericQuestion();
                                    numericQuestion.id=Helper.stringToInt(question.getString("id"));
                                    numericQuestion.description=question.getString("description");
                                    PatientInfo.numericQuestions.add(numericQuestion);
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _dbManager.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void GetQuestionChoices(int questionId){
        ArrayList<MultipleChoiceQuestionInfo> questionList = new ArrayList<MultipleChoiceQuestionInfo>();
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL+"/Answers/Answers?questionId="+questionId,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray choices = response.getJSONArray("$values");
                            for (int i = 0; i < choices.length(); i++) {
                                JSONObject choice = choices.getJSONObject(i);
                                AnswerInfo answerInfo = new AnswerInfo();
                                answerInfo.id = Helper.stringToInt(choice.getString("id"));
                                answerInfo.description = choice.getString("description");
                                answerInfo.score = Helper.stringToDouble(choice.getString("score"));

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +  _dbManager.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


    public void InsertPatientAnswer(JSONObject object,final VolleyCallBack callBack){
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL+"/PatientAnswers", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(Helper.stringToBoolean(response.getString("success")))
                            {
                                JSONObject obj = new JSONObject(response.getString("data"));

                                callBack.onSuccess(new SuccessResult());
                            }else{
                                callBack.onError(new ErrorResult(response.getString("message")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError(new ErrorResult(e.getMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
                callBack.onError(new ErrorResult("Service unavailable"));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
