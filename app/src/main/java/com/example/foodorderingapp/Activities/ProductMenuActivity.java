package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodorderingapp.Adapters.CategoryAdapter;
import com.example.foodorderingapp.Adapters.ProductAdapter;
import com.example.foodorderingapp.Models.Category;
import com.example.foodorderingapp.Models.Products;
import com.example.foodorderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ProductMenuActivity extends AppCompatActivity {
    public static List<Products> productsListFromFireBase = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    TextView textViewAll;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        bindingView();
        bindingAction();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            System.out.println("NO INTEND");
//        List<Category> categoryList = new ArrayList<>();
//        categoryList.add(new Category(4, "Drink"));
//        categoryList.add(new Category(5, "Fries"));
//        categoryList.add(new Category(6, "BBQ"));
//
//        setProductRecycler(categoryList);
//        List<Products> productsList = new ArrayList<>();
//        productsList.add(new Products(4, "watter is very nice", 7 , R.drawable.sprite, "CHAI SPRITE 1.85L", 30, 100));
//        productsList.add(new Products(4, "watter is very nice", 8 , R.drawable.coke, "CHAI COCA 1.5L", 30, 100));
//        productsList.add(new Products(4, "watter is very nice", 9 , R.drawable.fanta, "CHAI FANTA 1.5L", 30, 100));
//        setProdItemRecycler(productsList);
//
//        for (Products p : productsList) {
//            DatabaseReference myRef = database.getReference("Product");
//            myRef.child(p.getProductId()+"").setValue(p);
//        }
//        database.getReference("Product").child("1").child("productImageUrl").setValue(R.drawable.pizza_lap_xuong2);
//        database.getReference("Product").child("2").child("productImageUrl").setValue(R.drawable.okonomiyaki);
//        database.getReference("Product").child("3").child("productImageUrl").setValue(R.drawable.ocean_mania);
//        database.getReference("Product").child("4").child("productImageUrl").setValue(R.drawable.pizzaminsea);
//        database.getReference("Product").child("5").child("productImageUrl").setValue(R.drawable.pasta_hai_san);
//        database.getReference("Product").child("6").child("productImageUrl").setValue(R.drawable.pasta_bo_bam);
//        for (Category c : categoryList) {
//            DatabaseReference myRef = database.getReference("Category");
//            myRef.child(c.getCategoryId()+"").setValue(c);
//        }
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
    }

    public ArrayList<Products> removeDuplicates(List<Products> list) {
        Set set = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (((Products) o1).getProductName().equalsIgnoreCase(((Products) o2).getProductName())) {
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);
        final ArrayList newList = new ArrayList(set);
        return newList;
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
    }
}