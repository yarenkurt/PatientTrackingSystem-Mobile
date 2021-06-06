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
import com.example.patienttrackerapp.models.AppointmentInfo;
import com.example.patienttrackerapp.models.Defaults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentService {
    private static final String BASE_URL= Defaults.BASE_URL+"/api/Appointments";
    private final Context _context;
    private final AccountDbManager _dbManager;
    public AppointmentService(Context context){
        _context=context;
        _dbManager=new AccountDbManager(context);
    }


    public void GetAllAppointments(int patientId){
        ArrayList<AppointmentInfo> appointmentList = new ArrayList<AppointmentInfo>();
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL+"/ByPatient",null,
                response -> {
                    try {
                        JSONArray appointments = response.getJSONArray("$values");
                        for (int i = 0; i < appointments.length(); i++) {
                            JSONObject app = appointments.getJSONObject(i);
                            AppointmentInfo appointment = new AppointmentInfo ();
                            appointment.doctorName= app.getString("doctorName");
                            appointment.deptName= app.getString("departmentName");
                            appointment.date= Helper.jsonStringToDate(app.getString("date"));

                            appointmentList.add(appointment);

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("Error",error.toString())){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +  _dbManager.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
