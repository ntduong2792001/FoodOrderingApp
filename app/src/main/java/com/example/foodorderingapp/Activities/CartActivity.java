package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.Adapters.CategoryAdapter;
import com.example.foodorderingapp.Adapters.ProductAdapter;
import com.example.foodorderingapp.Models.Category;
import com.example.foodorderingapp.Models.Order;
import com.example.foodorderingapp.Models.OrderDetail;
import com.example.foodorderingapp.Models.Products;
import com.example.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView  prodItemRecycler;
    EditText addressTxt;
    TextView tvTotalPrice;
    Button checkoutBtn;
    double totalPrice = 0;
    private FirebaseAuth auth;
    ArrayList<OrderDetail> lo = new ArrayList<>();
    ArrayList<Products> lp  = new ArrayList<>();
    ArrayList<Order> lor = new ArrayList<>();
    ArrayList<Products> listOrderView = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        prodItemRecycler = findViewById(R.id.product_recycler);
        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("cart","");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<OrderDetail>>(){}.getType();
        lo = gson.fromJson(s,type);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        addressTxt = findViewById(R.id.addressTxt);

        //
        sharedPreferences = getSharedPreferences(ProductMenuActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String s2 = sharedPreferences.getString("prod","");
        Gson gson2 = new Gson();
        Type type2 = new TypeToken<ArrayList<Products>>(){}.getType();
        lp = gson2.fromJson(s2,type2);
        //
        sharedPreferences = getSharedPreferences(ProductMenuActivity.MyPREFERENCES2, Context.MODE_PRIVATE);
        String s3 = sharedPreferences.getString("order","");
        Gson gson3 = new Gson();
        Type type3 = new TypeToken<ArrayList<Order>>(){}.getType();
        lor = gson3.fromJson(s3,type3);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        addressTxt = findViewById(R.id.addressTxt);

        for (OrderDetail odd:lo) {
            totalPrice+= odd.getQuantity()*getById(odd.getProductId()).getProductPrice();
        }

        for(OrderDetail odd:lo){
            Products products = new Products();
            products.setProductId(odd.getProductId());
            products.setProductName(getById(odd.getProductId()).getProductName());
            products.setProductPrice(getById(odd.getProductId()).getProductPrice()*odd.getQuantity());
            products.setProductImageUrl(getById(odd.getProductId()).getProductImageUrl());
            products.setProductQuantity(odd.getQuantity());
            listOrderView.add(products);
        }
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setAddress(addressTxt.getText().toString());
                order.setPrice(totalPrice);
                order.setOrderId(lor.size()+1);
                auth = FirebaseAuth.getInstance();
                order.setEmail(auth.getCurrentUser().getEmail());
                //add order
                FirebaseDatabase.getInstance().getReference("Order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        for (OrderDetail odd:lo) {
                            odd.setOrderId(lor.size()+1);
                            FirebaseDatabase.getInstance().getReference("OrderDetail").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(odd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    System.out.println("Add successfully");
                                }
                            });
                        }
                        Intent intent = new Intent(CartActivity.this,HomeActivity.class );
                        startActivity(intent);
                    }
                });
                sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit().clear();
                Gson gson = new Gson();
                String gsonList = gson.toJson(new ArrayList<OrderDetail>());
                sharedPreferences.edit().putString("cart", gsonList);
                sharedPreferences.edit().commit();
            }
        });
        setProdItemRecycler(listOrderView);
        tvTotalPrice.setText(totalPrice+" $");
    }



    private Products getById(int id){
        for (Products x: lp) {
            if(x.getProductId()==id) return x;
        }
        return null;
    }

    private void setProdItemRecycler(List<Products> lp) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, lp);
        prodItemRecycler.setAdapter(productAdapter);
    }

}