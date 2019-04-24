package com.ict.delivirko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ict.delivirko.Module.pilot.Order;
import com.ict.delivirko.R;

import java.util.ArrayList;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    Context context;
    ArrayList<Order> objects;

    public OrdersAdapter(Context context, ArrayList<Order> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_order_pilot, viewGroup, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        if (i % 2 == 0)
            myViewHolder.containerOrders.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        else
            myViewHolder.imgIsPayed.setImageResource(R.drawable.ic_payed);

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout containerOrders;
        ImageView imgIsPayed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            containerOrders = itemView.findViewById(R.id.containerOrders);
            imgIsPayed = itemView.findViewById(R.id.imgIsPayed);
        }
    }
}
