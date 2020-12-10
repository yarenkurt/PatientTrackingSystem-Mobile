package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);

        //Comments added ////
    }

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
}


