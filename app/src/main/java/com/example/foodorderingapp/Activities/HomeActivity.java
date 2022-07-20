package com.example.foodorderingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    Button btnUser;
    TextView tvGreet; ImageView avatar;
    Button profileBtn, orderDetailBtn, foodOrderingBtn;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_home);
        bindElements();
        bindElementToActivity();
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null  ){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
         }
        else{
            tvGreet.setText("Hello, "+ auth.getCurrentUser().getDisplayName());
            Uri photo = auth.getCurrentUser().getPhotoUrl();
            if(photo!= null)
            Picasso.with(this).load(photo).into(avatar);
            else{
                String uri = "@drawable/profile";
                int imageResourse = getResources().getIdentifier(uri,null,getPackageName());
                Drawable drawable = getResources().getDrawable(imageResourse);
                avatar.setImageDrawable(drawable);
            }
        }



        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,UserActivity.class );
                intent.putExtra("test", "test");
                startActivity(intent);
            }
        });
        //anhpd35
        orderDetailBtn = findViewById(R.id.btn_OrderDetail);
        orderDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,CartActivity.class );
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