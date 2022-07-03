package com.example.foodorderingapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodorderingapp.R;

public class SignupTabFragment extends Fragment {
    TextView email;
    TextView phoneNumber;
    TextView pass;
    TextView confirmPass;
    Button signup;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.editTextEmail);
        pass = root.findViewById(R.id.editTextPassword);
        phoneNumber = root.findViewById(R.id.editTextPhone);
        confirmPass = root.findViewById(R.id.editTextConfirmPassword);
        signup = root.findViewById(R.id.buttonSignup);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        phoneNumber.setTranslationX(800);
        confirmPass.setTranslationX(800);
        signup.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        phoneNumber.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        phoneNumber.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();

        return root;
    }
}
