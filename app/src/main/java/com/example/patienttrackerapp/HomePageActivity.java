package com.example.patienttrackerapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AccountModel;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.TokenInfo;
import com.example.patienttrackerapp.results.ErrorResult;
import com.example.patienttrackerapp.results.SuccessResult;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, LocationListener {
    Button emergency_btn;
    Button daily_form_btn;
    Button set_reminder_btn;
    TextView name, appointment, advice, scoreText;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String _token;
    LocationManager _locationManager;
    String _provider;
    Location _location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        name = findViewById(R.id.textPatientName);
        appointment = findViewById(R.id.appointment);
        advice = findViewById(R.id.advices);
        scoreText = findViewById(R.id.scoreText);


        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        //INITIALIZING BUTTONS AND SETTING  CLICK LISTENERS
        emergency_btn = findViewById(R.id.emergency_btn);
        emergency_btn.setOnClickListener(this);

        daily_form_btn = findViewById(R.id.daily_form_btn);
        daily_form_btn.setOnClickListener(this);

        set_reminder_btn = findViewById(R.id.reminder_btn);
        set_reminder_btn.setOnClickListener(this);

        AccountDbManager dbManager = new AccountDbManager(this);

        if (!dbManager.isAuthenticated()) {
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        _token = dbManager.getToken();
        AccountModel account = dbManager.get();
        if (account != null) {
            name.setText(account.fullName);
        }
        GetMyTotalScore();
        Appointments();
        Advices();
        InitLocation();
    }


    public void Appointments() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/Appointments/Closest", null,
                response -> {
                    try {
                        String doctorName = response.getString("doctorName");
                        String date = response.getString("date");
                        String message = String.format("Next Appointment : \n%s %s", doctorName, Helper.dateTimeToString(Helper.jsonStringToDate(date)));
                        appointment.setText(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("Error", error.toString())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void Advices() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/Advices/MyAdvices", null,
                response -> {
                    try {
                        JSONArray appointments = new JSONArray(response.getString("$values"));
                        StringBuilder advicesText = new StringBuilder();
                        for (int i = 0; i < appointments.length(); i++) {
                            JSONObject obj = appointments.getJSONObject(i);
                            advicesText.append(i + 1).append(". ").append(obj.getString("description")).append("\n");
                        }
                        advice.setText(advicesText.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Error", error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void GetMyTotalScore() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/PatientAnswers/MyTotalScore", null,
                (JSONObject response) -> {
                    try {
                        Double score = response.getDouble("score");
                        scoreText.setText(score.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> scoreText.setText(error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    /// MENU ITEM SELECTION
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
                break;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.appointmentList:
                startActivity(new Intent(this, AppointmentsActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    //ACCORDING TO ID OF BUTTON, DO DOME ACTIONS
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emergency_btn:
                emergency();
                break;
            case R.id.daily_form_btn:
                Intent intentToForm = new Intent(HomePageActivity.this, FormActivity.class);
                startActivity(intentToForm);
                break;

            case R.id.reminder_btn:
                Intent intentToReminder = new Intent(HomePageActivity.this, ReminderPageActivity.class);
                startActivity(intentToReminder);
                break;
        }
    }

    private void emergency() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        double latutide = _locationManager.getLastKnownLocation(_provider).getLatitude();
        double longitude = _locationManager.getLastKnownLocation(_provider).getLongitude();

        JSONObject object = new JSONObject();
        try {
            object.put("latutide",latutide);
            object.put("longitude",longitude);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Defaults.BASE_URL + "/api/Patients/SOS?latitude="+ latutide +"&longitude="+longitude
                , null,
                response -> {
                    try {
                        if (response.getBoolean("success")){
                            Toast.makeText(this, "Your emergency status is sent to your relatives.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Error", error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void InitLocation() {
        _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        _provider = _locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = _locationManager.getLastKnownLocation(_provider);
        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(this, "location is not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        //Toast.makeText(this, Double.toString(latitude), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        _locationManager.requestLocationUpdates(_provider, 400, 100, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(this, "Provider Enable", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, "Provider Disable", Toast.LENGTH_SHORT).show();
    }
}
