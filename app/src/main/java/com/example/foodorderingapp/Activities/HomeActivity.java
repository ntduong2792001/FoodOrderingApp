package com.example.foodorderingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    Button btnUser;
    TextView tvGreet; ImageView avatar;
    Button profileBtn, orderDetailBtn, foodOrderingBtn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindElements();
        bindElementToActivity();
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
         }
        else{
            tvGreet.setText("Hello, "+ auth.getCurrentUser().getDisplayName());
            Uri photo = auth.getCurrentUser().getPhotoUrl();
            Picasso.with(this).load(photo).into(avatar);
        }



        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,UserActivity.class );
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void bindElements(){
        tvGreet = findViewById(R.id.tvGreet);
        avatar = findViewById(R.id.avatar);
        profileBtn = findViewById(R.id.btn_Profile);
        orderDetailBtn = findViewById(R.id.btn_OrderDetail);
        foodOrderingBtn = findViewById(R.id.btn_foodOrdering);
    }

    public void bindElementToActivity(){
        foodOrderingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this,ProductMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileDetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
}