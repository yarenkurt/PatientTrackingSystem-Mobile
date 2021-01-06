package com.example.patienttrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignUpTabFragment extends Fragment {
    EditText Id, Name, Surname, Password, Repassword, Firstnumber, SecondNumber;
    Button Signup;
    float v = 0;

    public static SignUpTabFragment getInstance() {
        SignUpTabFragment signUpTabFragment = new SignUpTabFragment();
        return signUpTabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);


        Id = root.findViewById(R.id.editTextID);
        Name = root.findViewById(R.id.editTextName);
        Surname = root.findViewById(R.id.editTextSurname);
        Password = root.findViewById(R.id.editTextFirstNumber);
        Repassword = root.findViewById(R.id.editTextSurname);
        Firstnumber = root.findViewById(R.id.editTextFirstNumber);
        SecondNumber = root.findViewById(R.id.editTextSecondNumber);
        Signup = root.findViewById(R.id.btnSignUp);

        Id.setTranslationX(800);
        Name.setTranslationX(800);
        Password.setTranslationX(800);
        Repassword.setTranslationX(800);
        Firstnumber.setTranslationX(800);
        SecondNumber.setTranslationX(800);
        Signup.setTranslationX(800);

        Id.setAlpha(v);
        Name.setAlpha(v);
        Surname.setAlpha(v);
        Password.setAlpha(v);
        Repassword.setAlpha(v);
        Firstnumber.setAlpha(v);
        SecondNumber.setAlpha(v);


        Id.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Repassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Firstnumber.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        SecondNumber.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;
    }
}
