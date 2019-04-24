package com.ict.delivirko.fragment.restaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseBillResData;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.BillOrders;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.restaurant.BillRestAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;


public class BillRestFragment extends Fragment implements View.OnClickListener {

    private ImageButton btnOlderBillRest, btnNewerBillRest;
    private RecyclerView rvBillRest;
    private TextView tvWeekBillRest, tvRestNameBill, totalPrice;
    private BillRestAdapter billRestAdapter;
    private int page = 0;
    private ImageView ResImage;
    private List<BillOrders> billOrders;

    public BillRestFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_rest, container, false);

        intiViews(view);

        return view;
    }

    private void intiViews(View view) {
        billOrders = new Vector<>();
        rvBillRest = view.findViewById(R.id.rvBillRest);
        ResImage = view.findViewById(R.id.ResImage);
        billRestAdapter = new BillRestAdapter(getContext(), billOrders, false);
        rvBillRest.setAdapter(billRestAdapter);
        rvBillRest.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnOlderBillRest = view.findViewById(R.id.btnOlderBillRest);
        btnNewerBillRest = view.findViewById(R.id.btnNewerBillRest);
        tvWeekBillRest = view.findViewById(R.id.tvWeekBillRest);
        tvRestNameBill = view.findViewById(R.id.tvRestNameBill);
        totalPrice = view.findViewById(R.id.totalPrice);
        btnNewerBillRest.setOnClickListener(this);
        btnOlderBillRest.setOnClickListener(this);
        BillCompany(page + "", getActivity());
        tvRestNameBill.setText(ApplicationController.getInstance().getUser().getName());
        Picasso.with(getActivity()).load(Constants.Image_URL + ApplicationController.getInstance().getUser().getImage()).fit()
                .into(ResImage);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewerBillRest:
                BillCompany((page = page + 1) + "", getActivity());
                btnNewerBillRest.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;

            case R.id.btnOlderBillRest:
                BillCompany((page = page - 1) + "", getActivity());
                btnOlderBillRest.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;
        }
    }

    public void BillCompany(final String week, final Context context) {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().BillRest(week, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseBillResData result1 = (ResponseBillResData) result;
                if (result1.isStatus()) {
                    billOrders.clear();
                    billOrders .addAll(result1.getBillData().getOrders());
                    billRestAdapter.notifyDataSetChanged();

                    tvWeekBillRest.setText(result1.getBillData().getStart_date() + " - " + result1.getBillData().getEnd_date());
                    totalPrice.setText(result1.getBillData().getTotal_price() + "");
                } else {
                    Constants.showDialog(getActivity(), result1.getMessage());
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
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

}
