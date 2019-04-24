package com.ict.delivirko.adapter.restaurant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.delivirko.Activities.HomeRestaurantActivity;
import com.ict.delivirko.Activities.TravelMapsActivity;
import com.ict.delivirko.Module.restaurant.DriverTracking;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowOrdersAdapter extends RecyclerView.Adapter<FollowOrdersAdapter.MyViewHolder> {

    Context context;
    List<DriverTracking> objects;

    public FollowOrdersAdapter(Context context, List<DriverTracking> objects) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final DriverTracking driverTracking = objects.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (driverTracking.getStatus_id() == 3 || driverTracking.getStatus_id() == 2) {
                    intent = new Intent(context, TravelMapsActivity.class);
                } else {
                    intent = new Intent(context, HomeRestaurantActivity.class);
                }
                intent.putExtra("orderNo", driverTracking.getId());
                intent.putExtra("Status_id", driverTracking.getStatus_id());

                context.startActivity(intent);
            }
        });

        if (i == 0)
            holder.imgDotsAbove.setVisibility(View.INVISIBLE);
        if (i == objects.size() - 1)
            holder.imgBelow.setVisibility(View.INVISIBLE);

        holder.textDriverName.setText(driverTracking.getDriver().getName());
        holder.txtOrderId.setText(driverTracking.getId() + "");
        Picasso.with(context).load(Constants.Image_URL + driverTracking.getDriver().getImage()).fit()
                .into(holder.pilotImage);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDotsAbove, imgBelow, pilotImage;
        private TextView txtOrderId, textDriverName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDotsAbove = itemView.findViewById(R.id.imgAbove);
            imgBelow = itemView.findViewById(R.id.imgBelow);
            pilotImage = itemView.findViewById(R.id.pilotImage);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            textDriverName = itemView.findViewById(R.id.textDriverName);

        }
    }
}
