package com.example.patienttrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class LoginPageActivity extends AppCompatActivity {
TabLayout tabLayout;
ViewPager viewPager;
float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_Pager);
        TextView textViewW=findViewById(R.id.textViewWelcome);


       LoginAdapter loginAdapter=new LoginAdapter(getSupportFragmentManager());
       loginAdapter.addFragment(LoginTabFragment.getInstance(),"Login");
        loginAdapter.addFragment(SignUpTabFragment.getInstance(),"SignUp");

        viewPager.setAdapter(loginAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /*imageView=findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.bg);*/

       /* tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/

       // tabLayout.setAlpha(v);

       /* final  LoginAdapter adapter=new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
    }
}