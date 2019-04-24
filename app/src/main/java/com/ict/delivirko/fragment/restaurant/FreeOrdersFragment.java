package com.ict.delivirko.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseFreeTrips;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeOrdersFragment extends Fragment {
    private TextView TxtNum, TxtDate, tvDiscountAmount, tvDiscountInEgp;

    public FreeOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free_orders, container, false);
        TxtNum = view.findViewById(R.id.TxtNum);
        TxtDate = view.findViewById(R.id.TxtDate);
        tvDiscountAmount = view.findViewById(R.id.tvDiscountAmount);
        tvDiscountInEgp = view.findViewById(R.id.tvDiscountInEgp);
        FreeTrips();
        return view;
    }

    public void FreeTrips() {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().FreeTrips(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseFreeTrips responseFreeTrips = (ResponseFreeTrips) result;
                if (responseFreeTrips != null) {
                    if (responseFreeTrips.isStatus()) {
                        TxtNum.setText("" + (responseFreeTrips.getFreeTrips().getOrder_num() - responseFreeTrips.getFreeTrips().getOrder_num_used()));
                        TxtDate.setText(responseFreeTrips.getFreeTrips().getEnd_at());
                        tvDiscountAmount.setText(responseFreeTrips.getFreeTrips().getDiscount_rate() + "");
                        tvDiscountInEgp.setText(responseFreeTrips.getFreeTrips().getDiscount_max_value() + "");
                    } else {
                        Constants.showDialog(getActivity(), responseFreeTrips.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


}
