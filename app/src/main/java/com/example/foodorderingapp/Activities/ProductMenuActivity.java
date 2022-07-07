package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class ProductMenuActivity extends AppCompatActivity {

    CategoryAdapter categoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(4, "Drink"));
        categoryList.add(new Category(5, "Fries"));
        categoryList.add(new Category(6, "BBQ"));

        setProductRecycler(categoryList);

        List<Products> productsList = new ArrayList<>();
//        productsList.add(new Products(3, "Japanese Cherry Blossom", 100, 17, R.drawable.prod2, 3, "nice nice nice"));
//        productsList.add(new Products(4, "African Mango Shower Gel", 100, 25, R.drawable.prod1, 4, "nice nice nice"));
//        productsList.add(new Products(5, "Japanese Cherry Blossom", 100, 17, R.drawable.prod2, 5, "nice nice nice"));
//        productsList.add(new Products(6, "African Mango Shower Gel", 100, 25, R.drawable.prod1, 6, "nice nice nice"));

        //setProdItemRecycler(productsList);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        for (Products p : productsList) {
//            DatabaseReference myRef = database.getReference("Product");
//            myRef.child(p.getProductId()+"").setValue(p);
//        }
//        for (Category c : categoryList) {
//            DatabaseReference myRef = database.getReference("Category");
//            myRef.child(c.getCategoryId()+"").setValue(c);
//        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Product");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* This method is called once with the initial value and again whenever data at this location is updated.*/
                long value = dataSnapshot.getChildrenCount();
                GenericTypeIndicator<List<Products>> t = new GenericTypeIndicator<List<Products>>() {
                };
                List<Products> productsListFromFireBase = dataSnapshot.getValue(t);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX "+productsListFromFireBase.size());
                setProdItemRecycler(productsListFromFireBase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setProductRecycler(List<Category> categoryList) {
        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        productCatRecycler.setAdapter(categoryAdapter);
    }

    private void setProdItemRecycler(List<Products> productsList) {
        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);
    }
}



