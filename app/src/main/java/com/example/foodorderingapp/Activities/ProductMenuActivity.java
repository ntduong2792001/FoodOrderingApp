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
import android.widget.TextView;

import com.example.foodorderingapp.Adapters.CategoryAdapter;
import com.example.foodorderingapp.Adapters.ProductAdapter;
import com.example.foodorderingapp.Models.Category;
import com.example.foodorderingapp.Models.Order;
import com.example.foodorderingapp.Models.OrderDetail;
import com.example.foodorderingapp.Models.Products;
import com.example.foodorderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuActivity extends AppCompatActivity {
    public static List<Products> productsListFromFireBase = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    TextView textViewAll, textViewChicken, textViewBeef, textViewChay;
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "PROD" ;
    public static final String MyPREFERENCES2 = "ORDR" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        bindingView();
        bindingAction();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Product").child("1").child("productImageUrl").setValue(R.drawable.pd_pizza_lap_xuong2);
        database.getReference("Product").child("2").child("productImageUrl").setValue(R.drawable.pd_okonomiyaki);
        database.getReference("Product").child("3").child("productImageUrl").setValue(R.drawable.pd_ocean_mania);
        database.getReference("Product").child("4").child("productImageUrl").setValue(R.drawable.pd_pizzaminsea);
        database.getReference("Product").child("5").child("productImageUrl").setValue(R.drawable.pd_pasta_hai_san);
        database.getReference("Product").child("6").child("productImageUrl").setValue(R.drawable.pd_pasta_bo_bam);
        database.getReference("Product").child("7").child("productImageUrl").setValue(R.drawable.pd_sprite);
        database.getReference("Product").child("8").child("productImageUrl").setValue(R.drawable.pd_coke);
        database.getReference("Product").child("9").child("productImageUrl").setValue(R.drawable.pd_fanta);
        database.getReference("Product").child("10").child("productImageUrl").setValue(R.drawable.pd_canh_ga_bbq_kieu_my);
        database.getReference("Product").child("11").child("productImageUrl").setValue(R.drawable.pd_ga_khong_xuong_bbq);
        database.getReference("Product").child("12").child("productImageUrl").setValue(R.drawable.pd_xuc_xich_xong_khoi);
        database.getReference("Product").child("13").child("productImageUrl").setValue(R.drawable.pd_khoai_tay_pho_mai);
        database.getReference("Product").child("14").child("productImageUrl").setValue(R.drawable.pd_banh_mi_pho_mai);
        database.getReference("Product").child("15").child("productImageUrl").setValue(R.drawable.pd_banh_mi_bo_toi);
        database.getReference("Product").child("16").child("productImageUrl").setValue(R.drawable.pd_signature_salad);
        database.getReference("Product").child("17").child("productImageUrl").setValue(R.drawable.pd_caesars_salad_300);
        database.getReference("Product").child("18").child("productImageUrl").setValue(R.drawable.pd_mcroyal_with_cheese);
        database.getReference("Product").child("19").child("productImageUrl").setValue(R.drawable.pd_bigmac);
        DatabaseReference myRef2 = database.getReference("Category");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    List<Category> categoriesListFromFireBase = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        categoriesListFromFireBase.add(ds.getValue(Category.class));
                    }
                    setProductRecycler(categoriesListFromFireBase);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference myRef3 = database.getReference("Order");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    List<Order> ordersListFromFireBase = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ordersListFromFireBase.add(ds.getValue(Order.class));
                    }
                    sharedPreferences = getSharedPreferences(ProductMenuActivity.MyPREFERENCES2, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String gsonList = gson.toJson(ordersListFromFireBase);
                    editor.putString("order", gsonList);
                    editor.commit();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Intent i = getIntent();
        if ((ArrayList<Products>) i.getSerializableExtra("list") != null) {
            System.out.println("HAS INTEND");
            ArrayList<Products> list = (ArrayList<Products>) i.getSerializableExtra("list");
            setProdItemRecycler(list);

            productsListFromFireBase = new ArrayList<>();
            DatabaseReference myRef = database.getReference("Product");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        productsListFromFireBase.add(ds.getValue(Products.class));
                    }
                    sharedPreferences = getSharedPreferences(ProductMenuActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String gsonList = gson.toJson(productsListFromFireBase);
                    editor.putString("prod", gsonList);
                    editor.commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            System.out.println("NO INTEND");

            DatabaseReference myRef = database.getReference("Product");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        productsListFromFireBase.add(ds.getValue(Products.class));
                    }
                    setProdItemRecycler(productsListFromFireBase);
                    sharedPreferences = getSharedPreferences(ProductMenuActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String gsonList = gson.toJson(productsListFromFireBase);
                    editor.putString("prod", gsonList);
                    editor.commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }


    private void setProductRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        productCatRecycler.setAdapter(categoryAdapter);
    }

    private void setProdItemRecycler(List<Products> productsList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);
    }

    private void bindingView() {
        textViewAll = findViewById(R.id.textViewAllProduct);
        productCatRecycler = findViewById(R.id.cat_recycler);
        prodItemRecycler = findViewById(R.id.product_recycler);
        textViewChicken = findViewById(R.id.textViewChicken);
        textViewBeef = findViewById(R.id.textViewBeef);
        textViewChay = findViewById(R.id.textViewChay);
    }

    private void bindingAction() {
        textViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productsListFromFireBase = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Product");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            productsListFromFireBase.add(ds.getValue(Products.class));
                        }
                        setProdItemRecycler(productsListFromFireBase);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        textViewChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsListFromFireBase = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Product");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue(Products.class).getProductIngredient().equalsIgnoreCase("Chicken")) {
                                productsListFromFireBase.add(ds.getValue(Products.class));
                            }
                        }
                        setProdItemRecycler(productsListFromFireBase);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        textViewBeef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsListFromFireBase = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Product");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue(Products.class).getProductIngredient().equalsIgnoreCase("Beef")) {
                                productsListFromFireBase.add(ds.getValue(Products.class));
                            }
                        }
                        setProdItemRecycler(productsListFromFireBase);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        textViewChay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsListFromFireBase = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Product");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getValue(Products.class).getProductIngredient().equalsIgnoreCase("Chay")) {
                                productsListFromFireBase.add(ds.getValue(Products.class));
                            }
                        }
                        setProdItemRecycler(productsListFromFireBase);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}