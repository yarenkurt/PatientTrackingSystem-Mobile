package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AppointmentInfo;
import com.example.patienttrackerapp.models.Defaults;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  AppointmentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AppointmentAdapter _adapter;
    RecyclerView recyclerView ;
    String _token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        //region Initialization Items
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.appointment);
        //endregion

        GetAppointments();
        AccountDbManager _dbManager=new AccountDbManager(this);
        _token=_dbManager.getToken();
    }

    //region Menu Behaviours
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, HomePageActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.appointmentList:
                break;
            case R.id.logout:
                startActivity(new Intent(this,LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    //endregion

    //region Get All Appointments Method for Showing Them
    public void GetAppointments(){
        ArrayList<AppointmentInfo> appointmentList = new ArrayList<AppointmentInfo>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL +"/api/Appointments/MyAppointments",null,
                response -> {
                    try {
                        JSONArray appointments = response.getJSONArray("$values");
                        for (int i = 0; i < appointments.length(); i++) {
                            JSONObject app = appointments.getJSONObject(i);
                            AppointmentInfo appointment = new AppointmentInfo ();
                            appointment.id= app.getInt("id");
                            appointment.doctorName= app.getString("doctorName");
                            appointment.deptName= app.getString("departmentName");
                            appointment.date= Helper.jsonStringToDate(app.getString("date"));
                            appointment.expired= app.getBoolean("isExpired");
                            appointmentList.add(appointment);
                            _adapter=new AppointmentAdapter(recyclerView.getContext(),appointmentList);
                            recyclerView.setAdapter(_adapter);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("Error",error.toString())){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +_token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    //endregion
}