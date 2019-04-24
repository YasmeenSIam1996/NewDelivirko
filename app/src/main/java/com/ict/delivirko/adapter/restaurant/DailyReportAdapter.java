package com.ict.delivirko.adapter.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.Module.BillRest;
import com.ict.delivirko.R;

import java.util.List;


public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.MyViewHolder> {
    Context context;
    List<BillRest> objects;

    public DailyReportAdapter(Context context, List<BillRest> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_follow_orders, viewGroup, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
