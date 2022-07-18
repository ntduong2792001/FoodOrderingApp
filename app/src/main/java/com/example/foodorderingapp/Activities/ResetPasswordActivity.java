package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity {
    TextView email;
    Button next;
    float v = 0;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editTextEmailR);
        next = findViewById(R.id.buttonNext);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        email.setTranslationX(800);
        next.setTranslationX(800);

        email.setAlpha(v);
        next.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        next.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformResetPass();
            }
        });
    }

    public void PerformResetPass(){
        String emails = email.getText().toString();

        if(!emails.matches(emailPatter)){
            email.setError("Enter Correct Email");
        }else{
            progressDialog.setMessage("Please Wait While Reset Password");
            progressDialog.setTitle("Reset Password");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.sendPasswordResetEmail(emails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(ResetPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(ResetPasswordActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity(){
        Intent intent = new Intent(ResetPasswordActivity.this, ResetNotification.class);
        startActivity(intent);
    }
}