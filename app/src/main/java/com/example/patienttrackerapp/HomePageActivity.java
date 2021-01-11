package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    Button emergency_btn;
    Button daily_form_btn;
    Button set_reminder_btn;

    public static final int REQUEST_CALL=1;
    public static final int REQUEST_SMS=2;
    public static final int REQUEST_LOCATION=3;


    private FusedLocationProviderClient fusedLocationClient;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*Toolbar toolbar = findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);*/

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);


       //INITIALIZING BUTTONS AND SETTIN CLICK LISTENERS
        emergency_btn = findViewById(R.id.emergency_btn);
        emergency_btn.setOnClickListener(this);

        daily_form_btn = findViewById(R.id.daily_form_btn);
        daily_form_btn.setOnClickListener(this);

        set_reminder_btn = findViewById(R.id.reminder_btn);
        set_reminder_btn.setOnClickListener(this);

        //INITIALIZING FUSED LOCATION API
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    //ACCORDING TO ID OF BUTTON, DO DOME ACTIONS
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emergency_btn:
                getCurrentLocation();
                callEmergentNumber();

            case R.id.daily_form_btn:
                Intent intentToForm = new Intent(HomePageActivity.this,FormActivity2.class);
                startActivity(intentToForm);

            case R.id.reminder_btn:
                ///////////
        }
    }


    //GETS THE USER'S CURRENT LOCATION AND CALLS SENDING SMS FUNCTION INCLUDING THIS LOCATION//////
    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Address currentLocation = getAddress(location.getLatitude(),location.getLongitude());
                                address=currentLocation.getThoroughfare()+"\n"+currentLocation.getSubLocality()+"\n"+currentLocation.getLocality()+"\n"+currentLocation.getAdminArea();
                                System.out.println("ADDRESS IS SEND  ");
                                sendSmsToEmergentNumber(address);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomePageActivity.this,"LOCATION IS FAILED",Toast.LENGTH_LONG).show();
                        }
                     });
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }
    }
    //////////////////////////////////////////////////////////


    //CALLING EMERGENT NUMBER AUTOMATICALLY////////////////////
    public void callEmergentNumber() {
        if (ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("NUMBER IS CALLED");
            String number = "tel:05372799744";
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        }
        else {
            ActivityCompat.requestPermissions(HomePageActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
        }
    }
    /////////////////////////////////


    //SENDING SMS TO THE EMERGENT NUMBER INCLUDING CURRENT LOCATION//////////////
    public void sendSmsToEmergentNumber(String address) {
        if (ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                String phoneNum = "05372799744";
                String message = "HELP ME! MY LOCATION IS ....\n"+address;

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum, null, message, null, null);
                System.out.println("SMS IS SEND");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            ActivityCompat.requestPermissions(HomePageActivity.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        }
    }
    /////////////////////////////////////


    //CONVERT THE LOCATION OBJECT TO THE ADDRESS OBJECT///////////
    public Address getAddress(double lattitude, double longitude){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try{
            addresses = geocoder.getFromLocation(lattitude,longitude,1);
            return addresses.get(0);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////


    //MENU OPTIONS PROCESSES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepage_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intentToProfile = new Intent(HomePageActivity.this,ProfileActivity.class);
                startActivity(intentToProfile);

            case  R.id.logout:
                Intent intentToLogin = new Intent(HomePageActivity.this,LoginActivity.class);
                startActivity(intentToLogin);
        }

        return super.onOptionsItemSelected(item);
    }
    ////////////////////////////////////


    //CALLING WHEN USER REQUEST ANY PERMISSION FIRST TIME////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (grantResults.length > 0) {
                if (requestCode == REQUEST_CALL) {
                   callEmergentNumber();
                }
            else if(requestCode == REQUEST_SMS){
                try {
                    String phoneNum = "05372799744";
                    String message = "HELP ME! MY LOCATION IS ....\n"+address;

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, message, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == REQUEST_LOCATION){
               getCurrentLocation();
            }
        }
    }

    /////////////////////////////////////////////


}


