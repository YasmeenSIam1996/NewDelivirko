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
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseOrders;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.Orders;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.restaurant.MyOrdersRestAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;


public class SubOrdersRestFragment extends Fragment implements View.OnClickListener {

    ImageButton btnNewerSubOrders, btnOlderSubOrders;
    private MyOrdersRestAdapter adapter;
    private RecyclerView rvMyOrdersRest;
    private List<Orders> list;
    private ImageView tvRestImageBill;
    private int Page = 0;
    private int status;
    public static final String ARG_PARAM1 = "Status";
    private TextView tvMenuPilotName,tvRestNameBill;

    public SubOrdersRestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_orders_rest, container, false);

        setUpAdapter(view);
        setActions(view);
        status = getArguments().getInt(ARG_PARAM1);

        Log.e("status_", status + "");
        myOrders(status + "", Page + "", getActivity());

        tvRestImageBill = view.findViewById(R.id.tvRestImageBill);
        tvMenuPilotName = view.findViewById(R.id.tvMenuRestName);
        tvRestNameBill = view.findViewById(R.id.tvRestNameBill);
//        tvMenuPilotName.setText(ApplicationController.getInstance().getUser().getName());
        Picasso.with(getActivity()).load(Constants.Image_URL + ApplicationController.getInstance().getUser().getImage()).fit()
                .into(tvRestImageBill);
        tvRestNameBill.setText(ApplicationController.getInstance().getUser().getName());
        return view;
    }

    private void setActions(View view) {
        btnOlderSubOrders = view.findViewById(R.id.btnOlderSubOrders);
        btnNewerSubOrders = view.findViewById(R.id.btnNewerSubOrders);
        btnOlderSubOrders.setOnClickListener(this);
        btnNewerSubOrders.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewerSubOrders:
                btnNewerSubOrders.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                myOrders(status + "", (Page = Page + 1) + "", getActivity());


                break;

            case R.id.btnOlderSubOrders:
//                pagerOrdersRest.setCurrentItem(pagerOrdersRest.getCurrentItem() - 1, true);
                btnOlderSubOrders.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                myOrders(status + "", (Page = Page - 1) + "", getActivity());

                break;
        }
    }


    public void myOrders(String status, String week, final Context context) {

        new UserAPI().myOrders(status, week, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                list.clear();
                ResponseOrders result1 = (ResponseOrders) result;
                if (result1.isStatus()) {
                    list.addAll(result1.getOrderData().getOrders());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText((Activity) context, result1.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((Activity) context, responseError.getMessage());
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


    private void setUpAdapter(View view) {
        list = new Vector<>();
        rvMyOrdersRest = view.findViewById(R.id.rvMyOrdersRest);
        adapter = new MyOrdersRestAdapter(getContext(), list);
        rvMyOrdersRest.setAdapter(adapter);
        rvMyOrdersRest.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
