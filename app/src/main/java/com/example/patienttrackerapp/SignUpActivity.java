package com.example.patienttrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar1=(Toolbar) findViewById(R.id.toolbarSignUp);
        setSupportActionBar(toolbar1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

               case R.id.exit:
                Intent intentToLogin = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intentToLogin);

            case R.id.share:
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("Text/Plain");
                String shareBody="Your Body Here";
                String shareSubject="Your Subject Here";

                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);

                startActivity(Intent.createChooser(shareIntent,"Share Using"));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickSignUp(View view) {

        Intent intent=new Intent(this,HomePageActivity.class);
        startActivity(intent);
    }
}