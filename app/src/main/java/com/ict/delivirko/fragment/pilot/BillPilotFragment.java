package com.ict.delivirko.fragment.pilot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponsePilotBill;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;


public class BillPilotFragment extends Fragment implements View.OnClickListener {


    private ImageButton btnNewerBillPilot, btnOlderBillPilot;
    private TextView tvRateBill, tvWorksHoursBill, tvPointNo,
            tvNoOfOrders, tvComplete, tvAcceptance, tvOrdersIncome,
            tvReward, tvEnsure, tvCanceledTrips, tvOfficeFee, tvPilotNameOrders,
            tvOrderTimeDialog, tvEPay, tvTotalMoney, tvWeekBalance, tvDateOrders;
    private int numPage = 0;

    public BillPilotFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_pilot, container, false);

        btnNewerBillPilot = view.findViewById(R.id.btnNewerBillPilot);
        btnOlderBillPilot = view.findViewById(R.id.btnOlderBillPilot);
        tvPilotNameOrders = view.findViewById(R.id.tvPilotNameOrders);
        tvPilotNameOrders.setText(ApplicationController.getInstance().getUser().getName());

        tvRateBill = view.findViewById(R.id.tvRateBill);
        tvWorksHoursBill = view.findViewById(R.id.tvWorksHoursBill);
        tvPointNo = view.findViewById(R.id.tvPointNo);
        tvNoOfOrders = view.findViewById(R.id.tvNoOfOrders);
        tvComplete = view.findViewById(R.id.tvComplete);
        tvAcceptance = view.findViewById(R.id.tvAcceptance);
        tvOrdersIncome = view.findViewById(R.id.tvOrdersIncome);
        tvReward = view.findViewById(R.id.tvReward);
        tvEnsure = view.findViewById(R.id.tvEnsure);
        tvCanceledTrips = view.findViewById(R.id.tvCanceledTrips);
        tvOfficeFee = view.findViewById(R.id.tvOfficeFee);
        tvOrderTimeDialog = view.findViewById(R.id.tvOrderTimeDialog);
        tvEPay = view.findViewById(R.id.tvEPay);
        tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
        tvWeekBalance = view.findViewById(R.id.tvWeekBalance);
        tvDateOrders = view.findViewById(R.id.tvDateOrders);

        btnNewerBillPilot.setOnClickListener(this);
        btnOlderBillPilot.setOnClickListener(this);
        BillDriver(String.valueOf(numPage), getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewerBillPilot:
                BillDriver(String.valueOf(numPage++), getActivity());
                break;
            case R.id.btnOlderBillPilot:
                BillDriver(String.valueOf(numPage--), getActivity());
                break;
        }
    }


    public void BillDriver(final String week, final Context context) {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().BillDriver(week, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponsePilotBill result1 = (ResponsePilotBill) result;
                if (result1.isStatus()) {
                    tvDateOrders.setText(result1.getPilot_bill().getFrom() + " - " + result1.getPilot_bill().getTo());
                    tvRateBill.setText("5/" + result1.getPilot_bill().getRating());
                    tvWorksHoursBill.setText(result1.getPilot_bill().getWorkHours() + "");
                    tvPointNo.setText(result1.getPilot_bill().getCountPoints() + "");
                    tvNoOfOrders.setText(result1.getPilot_bill().getCountOrders() + "");
                    tvComplete.setText("%" + result1.getPilot_bill().getCompletedRate());
                    tvAcceptance.setText("%" + result1.getPilot_bill().getAcceptanceRate());
                    tvOrdersIncome.setText(result1.getPilot_bill().getRevenue() + "");
                    tvEnsure.setText(result1.getPilot_bill().getGuarantee() + "");
                    tvCanceledTrips.setText(result1.getPilot_bill().getRejectOrder() + "");
                    tvOfficeFee.setText(result1.getPilot_bill().getOfficePayment() + "");
                    tvOrderTimeDialog.setText(result1.getPilot_bill().getWallet() + "");
                    tvEPay.setText(result1.getPilot_bill().getElectronicPaymentService() + "");
                    tvTotalMoney.setText(result1.getPilot_bill().getCountTargetsMoney() + "");
                    tvWeekBalance.setText(result1.getPilot_bill().getAmount_cash() + "");

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
