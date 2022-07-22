package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    Button btnUser;
    TextView tvGreet; ImageView avatar;
    Button profileBtn, orderDetailBtn, foodOrderingBtn,showOrder;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_home);
        bindElements();
        bindElementToActivity();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
        String id = user== null ? googleSignInAccount.getId(): user.getUid();
        FirebaseDatabase.getInstance().getReference("Users/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User readUser = snapshot.getValue(User.class);
                tvGreet.setText("Hello, "+readUser.getFullName());
                //Glide.with(UserActivity.this).load(readUser.getImageUrl()).error(R.drawable.avatar_default).into(imgProfile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,UserActivity.class );
                intent.putExtra("test", "test");
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
        showOrder = findViewById(R.id.btn_foodOrder);
    }

    public void bindElementToActivity(){
        showOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ShowOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
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