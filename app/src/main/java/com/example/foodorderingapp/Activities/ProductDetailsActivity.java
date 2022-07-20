package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.Models.Category;
import com.example.foodorderingapp.Models.OrderDetail;
import com.example.foodorderingapp.Models.Products;
import com.example.foodorderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "listOrderDetail" ;
    TextView productName, productPrice, productDescription, categoryName, productType, quantityTxt;
    ImageView productImage, backImage, minusBtn, plusBtn;
    Button addToCartBtn;
    Products p;
    Category c;
    Context context;
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Product").child(productId);
        quantityTxt = findViewById(R.id.quantityTxt);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        addToCartBtn = findViewById(R.id.addToCartBtn);
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

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(quantityTxt.getText().toString())==1) return;
                quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())-1));
            }
        });

        plusBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())+1));
            }
        }));

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle b = new Bundle();
                OrderDetail od = new OrderDetail();
                od.setProductId(p.getProductId());
                od.setQuantity(Integer.parseInt(quantityTxt.getText().toString()));
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String s = sharedPreferences.getString("cart","");
                ArrayList<OrderDetail> lo = gson.fromJson(s, ArrayList.class);
                lo.add(od);
                editor.remove("cart");
                editor.putString("cart", gson.toJson(lo));
                editor.commit();



                Intent i = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                startActivity(i);
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
        productType = findViewById(R.id.textView10);

        productDescription.setText(p.getProductDescription());
        productName.setText(p.getProductName());
        productPrice.setText(p.getProductPrice() + " $");
        productImage.setImageResource(p.getProductImageUrl());
        productType.setText(p.getProductIngredient());
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, ProductMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    //anhpd add

}
