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
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.DiseaseInfo;
import com.example.patienttrackerapp.models.PatientInfo;
import com.example.patienttrackerapp.models.TokenInfo;
import com.example.patienttrackerapp.results.ErrorResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatientService {
    private static String BASE_URL= Defaults.BASE_URL+"/api";
    private Context _context;
    private final AccountDbManager _dbManager;
    public PatientService(Context context){
        _context=context;
        _dbManager=new AccountDbManager(context);
    }

    public void MyProfile(){
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL+"/Patients/MyProfile",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String patientId = response.getString("id");
                            PatientInfo.identityNumber = response.getString("identityNumber");
                            PatientInfo.email = response.getString("email");
                            PatientInfo.firstName = response.getString("firstName");
                            PatientInfo.lastName = response.getString("lastName");
                            PatientInfo.gsm = response.getString("gsm");
                            String age = response.getString("age");
                            String weight = response.getString("weight");
                            String height = response.getString("height");
                            String healthScore = response.getString("healthScore");

                            PatientInfo.id = Helper.stringToInt(patientId);
                            PatientInfo.age = Helper.stringToInt(age);
                            PatientInfo.weight = Helper.stringToDouble(weight);
                            PatientInfo.height = Helper.stringToInt(height);
                            PatientInfo.healthScore=Helper.stringToDouble(healthScore);

                        } catch (JSONException e) {
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
    public void UpdatePatient(JSONObject object,final VolleyCallBack callBack){
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, BASE_URL+"/Patients", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean success = response.getBoolean("success");

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
    public void GetPatientDisease(int patientId){
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL+"/PatientDiseases/ByPatient/?patientId="+patientId,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray diseases = response.getJSONArray("$values");
                            for (int i = 0; i < diseases.length(); i++) {
                                JSONObject app = diseases.getJSONObject(i);
                                DiseaseInfo diseaseInfo = new DiseaseInfo ();
                                diseaseInfo.description= app.getString("description");
                                diseaseInfo.deptName= app.getString("departmentName");
                                diseaseInfo.deptId= Helper.stringToInt(app.getString("departmentId"));
                                diseaseInfo.id= Helper.stringToInt(app.getString("id"));

                                PatientInfo.patientDiseases.add(diseaseInfo);

                            }

                        } catch (JSONException e) {
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
}
