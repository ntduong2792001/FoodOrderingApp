package com.example.foodorderingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.Models.Order;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {
    private Context mContext;
    private List<Order> mRestaurantFoodList = new ArrayList<>();

    public OrderAdapter(Context mContext, List<Order> mRestaurantFoodList) {
        this.mContext = mContext;
        this.mRestaurantFoodList = mRestaurantFoodList;
    }

    @NonNull
    @Override
    public OrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        return new OrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.viewHolder holder, int position) {
        Order food = mRestaurantFoodList.get(position);

        holder.tv_foodName.setText(food.getOrderId());
        holder.tv_foodPrice.setText((int) food.getPrice());


    }

    @Override
    public int getItemCount() {
        return mRestaurantFoodList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_foodImage;
        private TextView tv_foodName;
        private TextView tv_foodPrice;
        private TextView tv_foodDesc;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foodImage = itemView.findViewById(R.id.cv_foodImage);
            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_foodPrice = itemView.findViewById(R.id.tv_foodPrice);



        }
    }
}
