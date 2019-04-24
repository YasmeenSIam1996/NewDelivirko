package com.ict.delivirko.fragment.pilot;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseOrderPilot;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.ExpandableOrdersAdapter;


public class SubOrdersPilotFragment extends Fragment implements View.OnClickListener {

    private ImageButton btnNewerSubOrdersPilot, btnOlderSubOrdersPilot;
    private ExpandableListView orders;
    private ExpandableOrdersAdapter adapter;
    private TextView tvPilotNameOrders, tvWeekPilotOrders, tvNoOrders;
    private int PagerNum = 0;
    private static int Status;

    public static SubOrdersPilotFragment newInstance(int param1) {
        SubOrdersPilotFragment fragment = new SubOrdersPilotFragment();
        Status = param1;
        return fragment;
    }


    public SubOrdersPilotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_orders_pilot, container, false);
        tvPilotNameOrders = v.findViewById(R.id.tvPilotNameOrders);
        tvWeekPilotOrders = v.findViewById(R.id.tvWeekPilotOrders);
        tvNoOrders = v.findViewById(R.id.tvNoOrders);

        btnNewerSubOrdersPilot = v.findViewById(R.id.btnNewerSubOrdersPilot);
        btnOlderSubOrdersPilot = v.findViewById(R.id.btnOlderSubOrdersPilot);
        orders = v.findViewById(R.id.orders);
        btnNewerSubOrdersPilot.setOnClickListener(this);
        btnOlderSubOrdersPilot.setOnClickListener(this);
        setUpAdapter();
        Log.e("btnOlderSubOrdersPilot", (PagerNum ) + "   " + Status);

        PilotOrder(PagerNum + "", Status + "", getActivity());

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewerSubOrdersPilot:
                PilotOrder((PagerNum = PagerNum + 1) + "", Status + "", getActivity());

                btnNewerSubOrdersPilot.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;

            case R.id.btnOlderSubOrdersPilot:
                PilotOrder((PagerNum = PagerNum - 1) + "", Status + "", getActivity());
                btnOlderSubOrdersPilot.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;
        }

    }

    private void setUpAdapter() {

        orders.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView indicatorOrders = v.findViewById(R.id.indicatorOrders);
                if (orders.isGroupExpanded(groupPosition)) {

                    indicatorOrders.setImageResource(R.drawable.ic_expandable_closed);
                } else {
                    indicatorOrders.setImageResource(R.drawable.ic_expandable_open);
                }

                return false;
            }
        });

    }


    public void PilotOrder(final String week, final String status, final Context context) {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().PilotOrder(week, status, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOrderPilot result1 = (ResponseOrderPilot) result;
                if (result1.isStatus()) {
                    adapter = new ExpandableOrdersAdapter(getActivity(), result1.getMyOrder());
                    orders.setAdapter(adapter);
                    tvPilotNameOrders.setText(ApplicationController.getInstance().getUser().getName());
                    tvWeekPilotOrders.setText(result1.getMyOrder().getStart_date() + "-" + result1.getMyOrder().getEnd_date());
                    tvNoOrders.setText(result1.getMyOrder().getRate() + "");

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
