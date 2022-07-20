package com.example.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.Adapters.CategoryAdapter;
import com.example.foodorderingapp.Adapters.ProductAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView  prodItemRecycler;
    EditText addressTxt;
    Button checkoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        prodItemRecycler = findViewById(R.id.product_recycler);
        Bundle b = new Bundle();
        if ((ArrayList<OrderDetail>) b.getSerializable("cart") != null) {
            setProdItemRecycler((ArrayList<OrderDetail>) b.getSerializable("cart"));
        } else {
            setProdItemRecycler(new ArrayList<OrderDetail>());
        }
        checkoutBtn = findViewById(R.id.checkoutBtn);
        addressTxt = findViewById(R.id.addressTxt);
        ArrayList<OrderDetail> cart = (ArrayList<OrderDetail>) b.getSerializable("cart");
        double totalPrice = 0;
        for (OrderDetail odd:cart) {
            totalPrice+= odd.getQuantity();
        }
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Order order = new Order();
                order.setAddress(addressTxt.getText().toString());
                //add order
                FirebaseDatabase.getInstance().getReference("Order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //add order detail
                        for (OrderDetail odd:cart) {
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


            }
        });
    }

    private void setProdItemRecycler(List<OrderDetail> productsList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
    }

}