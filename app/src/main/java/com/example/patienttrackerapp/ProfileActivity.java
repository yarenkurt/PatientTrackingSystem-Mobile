package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.patienttrackerapp.apiservices.PatientService;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.PatientInfo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView _fullName, _identityNumber, _gsm, _disease, _age, _weightAndHeight;
    String _token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);
        navigationView.bringToFront();

        _fullName = findViewById(R.id.fullName);
        _identityNumber = findViewById(R.id.identityNumber);
        _gsm = findViewById(R.id.phoneNumber);
        _disease = findViewById(R.id.disease);
        _age = findViewById(R.id.age);
        _weightAndHeight = findViewById(R.id.weight_height);
        AccountDbManager _dbManager = new AccountDbManager(this);
        _token = _dbManager.getToken();
        Get();

    }

    private void Get() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Defaults.BASE_URL + "/api/Patients/MyProfile", null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            double weight = response.getDouble("weight");
                            int height = response.getInt("height");
                            int age = response.getInt("age");

                            _fullName.setText(response.getString("firstName") + " " + response.getString("lastName"));
                            _identityNumber.setText(response.getString("identityNumber"));
                            _gsm.setText(response.getString("gsm"));
                            _age.setText(Integer.toString(age));
                            _weightAndHeight.setText(weight + " / " + height);
                            JSONObject obj = new JSONObject(response.getString("diseases"));
                            JSONArray diseases = new JSONArray(obj.getString("$values"));
                            _disease.setText(diseases.toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .replace(",", "\n")
                                    .replace("\"", "")
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.appointmentList:
                startActivity(new Intent(this, AppointmentsActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
        }
        onBackPressed();

        return true;
    }

    public void backHome(View view) {
        Intent intent = new Intent(ProfileActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}