package com.example.foodorderingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodorderingapp.R;

public class ResetNotification extends AppCompatActivity {

    ImageView logo, splashImg;
    LottieAnimationView lottieAnimationView;
    TextView textDetail;
    Button backToLogin;

    private static int TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_notification);

        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);
        textDetail = findViewById(R.id.text_detail);
        backToLogin = findViewById(R.id.back_to_login);

        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        textDetail.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        backToLogin.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetNotification.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ResetNotification.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}