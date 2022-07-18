package com.example.foodorderingapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodorderingapp.Activities.HomeActivity;
import com.example.foodorderingapp.Activities.LoginActivity;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignupTabFragment extends Fragment {
    EditText email;
    EditText pass;
    EditText confirmPass;
    Button signup;
    float v = 0;
    Context context;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.editTextEmailS);
        pass = root.findViewById(R.id.editTextPasswordS);
        confirmPass = root.findViewById(R.id.editTextConfirmPassword);
        signup = root.findViewById(R.id.buttonSignup);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        email.setTranslationX(800);
        pass.setTranslationX(800);
        confirmPass.setTranslationX(800);
        signup.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        progressDialog = new ProgressDialog(context);
    }

    private void PerformAuth() {
        String emails = email.getText().toString();
        String passwords = pass.getText().toString();
        String confirmPasswords = confirmPass.getText().toString();

        if (!emails.matches(emailPatter)) {
            email.setError("Enter Correct Email");
        } else if (passwords.isEmpty() || passwords.length() < 8) {
            pass.setError("Enter Proper Password");
        } else if (!passwords.equals(confirmPasswords)) {
            confirmPass.setError("Password Does Not Match");
        } else {
            progressDialog.setMessage("Please Wait While Signup");
            progressDialog.setTitle("Signup");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User("", emails, "", "");
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    sendUserToNextActivity();
                                    Toast.makeText(context, "Signup Successful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }

}
