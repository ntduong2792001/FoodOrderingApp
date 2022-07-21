package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide
import com.example.foodorderingapp.Models.User;
import com.example.foodorderingapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    TextView editBtn , name;
    ImageView imgProfile;
    Button btnLogout;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        editBtn = findViewById(R.id.txtEditProfile);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.parseColor("#f1f3f5"));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            v.setBackgroundColor(Color.WHITE);
                    }
                },50);
                Intent intent = new Intent(UserActivity.this,ProfileDetail.class);
                startActivity(intent);
                finish();
            }
        });

        name = findViewById(R.id.tvName);
        imgProfile = findViewById(R.id.imgProfile);
        btnLogout = findViewById(R.id.buttonLogout);
        progressDialog = new ProgressDialog(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseAuth.signOut();

                Intent intent = new Intent(UserActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(UserActivity.this);

        progressDialog.show();

        String id = user== null ? googleSignInAccount.getId(): user.getUid();
        FirebaseDatabase.getInstance().getReference("Users/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User readUser = snapshot.getValue(User.class);
                name.setText(readUser.getFullName());
                //Glide.with(UserActivity.this).load(readUser.getImageUrl()).error(R.drawable.avatar_default).into(imgProfile);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}