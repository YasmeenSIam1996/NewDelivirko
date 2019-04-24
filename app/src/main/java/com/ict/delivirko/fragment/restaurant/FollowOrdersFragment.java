package com.ict.delivirko.fragment.restaurant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseTrackingOrders;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.DriverTracking;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.restaurant.FollowOrdersAdapter;

import java.util.ArrayList;
import java.util.List;


public class FollowOrdersFragment extends Fragment {

    private RecyclerView rvFollowOrders;
    private FollowOrdersAdapter followOrdersAdapter;
    private List<DriverTracking> objects;


    public FollowOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow_orders, container, false);

        setUpAdapter(view);
        trackingOrders(getActivity());
        return view;
    }

    private void setUpAdapter(View view) {
        rvFollowOrders = view.findViewById(R.id.rvFollowOrders);
        objects = new ArrayList<>();
        followOrdersAdapter = new FollowOrdersAdapter(getContext(), objects);
        rvFollowOrders.setAdapter(followOrdersAdapter);
        rvFollowOrders.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    public void trackingOrders(final Context context) {

        new UserAPI().trackingOrders(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseTrackingOrders result1 = (ResponseTrackingOrders) result;
                if (result1.isStatus()) {
                    objects.clear();
                    objects.addAll(result1.getDriverTracking());
                    followOrdersAdapter.notifyDataSetChanged();

                } else {
                    Constants.showDialog((Activity) context, result1.getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((Activity) context, responseError.getMessage());
                    Log.e("TRACKING_ORDER", responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }


}
