package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.Models.User;
import com.example.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileDetail extends AppCompatActivity {

    TextView tvName;
    ImageView backBtn,imgProfile,imgSave,btnChangeImage;
    EditText edtName,edtEmail,edtPhone;
    ProgressDialog progressDialog;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);


        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        imgSave = findViewById(R.id.imgSave);
        tvName = findViewById(R.id.textView10);
        btnChangeImage = findViewById(R.id.changeImg);
        imgProfile= findViewById(R.id.imageView9);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Selected picture"),200);
            }
        });

        EditText[] edts = {edtEmail,edtName,edtPhone};
        for (EditText item:edts   ) {
            item.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    imgSave.setImageResource(R.drawable.check_icon_blue);
                    return false;
                }
            });
        }

        progressDialog = new ProgressDialog(this);

        backBtn = findViewById(R.id.imgBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDetail.this,UserActivity.class);
                startActivity(intent);
                finish();
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog.show();
        FirebaseDatabase.getInstance().getReference("Users/"+user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User readUser = snapshot.getValue(User.class);
                tvName.setText(readUser.getFullName());
                edtName.setText(readUser.getFullName());
                edtEmail.setText(readUser.getEmail());
                edtPhone.setText(readUser.getPhone());
                Glide.with(ProfileDetail.this).load(readUser.getImageUrl()).error(R.drawable.avatar_default).into(imgProfile);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //update user profile
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User updateUser = new User(
                        edtName.getText().toString(),
                        edtEmail.getText().toString(),
                        edtPhone.getText().toString(),
                        imgUri.toString()
                );
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                progressDialog.show();
                String userUid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(userUid).setValue(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(ProfileDetail.this, "Update profile Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 200) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                imgUri = selectedImageUri;
                if (null != selectedImageUri) {

//
                    Glide.with(ProfileDetail.this).load(selectedImageUri).error(R.drawable.avatar_default).into(imgProfile);
                }
            }
        }
    }
}