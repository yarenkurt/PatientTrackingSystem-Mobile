package com.example.patienttrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    EditText userName,password;
    TextView forgetPassword;
    Button btnLogin;
    float v =0;
    public static LoginTabFragment getInstance() {
        LoginTabFragment loginTabFragment = new LoginTabFragment();
        return loginTabFragment;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);


        userName=root.findViewById(R.id.etUsername);
        password=root.findViewById(R.id.etPassword);
        forgetPassword=root.findViewById(R.id.tvForgetPassword);
        btnLogin=root.findViewById(R.id.buttonLogin);
        userName.setAlpha(v);

        userName.setTranslationX(800);
        password.setTranslationX(800);
        forgetPassword.setTranslationX(800);
        btnLogin.setTranslationX(800);

        userName.setAlpha(v);
        password.setAlpha(v);
        forgetPassword.setAlpha(v);
        btnLogin.setAlpha(v);

        userName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();



        return root;
    }
}
