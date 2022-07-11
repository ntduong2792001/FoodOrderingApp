package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.Models.Category;
import com.example.foodorderingapp.Models.Products;
import com.example.foodorderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView productName, productPrice, productDescription, categoryName;
    ImageView productImage, backImage;
    Products p;
    Category c;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Product").child(productId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p = dataSnapshot.getValue(Products.class);
                addProductValueToPage(p);
                setCategoryForProduct(p.getCategoryId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setCategoryForProduct(int categoryId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Category").child(categoryId + "");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                c = dataSnapshot.getValue(Category.class);
                addCategoryValueToPage(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addCategoryValueToPage(Category c) {
        categoryName = findViewById(R.id.textView7);
        categoryName.setText(c.getCategoryName());
    }

    private void addProductValueToPage(Products p) {
        productDescription = findViewById(R.id.textView13);
        productName = findViewById(R.id.textView11);
        productPrice = findViewById(R.id.textView12);
        productImage = findViewById(R.id.imageView7);
        backImage = findViewById(R.id.imageView);

        productDescription.setText(p.getProductDescription());
        productName.setText(p.getProductName());
        productPrice.setText(p.getProductPrice() + " $");
        productImage.setImageResource(p.getProductImageUrl());
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, ProductMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
