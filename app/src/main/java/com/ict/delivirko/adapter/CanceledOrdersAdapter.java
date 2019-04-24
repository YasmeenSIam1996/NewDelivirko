package com.ict.delivirko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ict.delivirko.Module.pilot.CanceledOrder;
import com.ict.delivirko.R;

import java.util.ArrayList;


public class CanceledOrdersAdapter extends RecyclerView.Adapter<CanceledOrdersAdapter.MyViewHolder> {
    Context context;
    ArrayList<CanceledOrder> objects;

    public CanceledOrdersAdapter(Context context, ArrayList<CanceledOrder> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_canceled_orders, viewGroup, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        if (i % 2 == 0)
            myViewHolder.containerOrders.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerOrders;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            containerOrders = itemView.findViewById(R.id.containerOrders);
        }
    }
}
