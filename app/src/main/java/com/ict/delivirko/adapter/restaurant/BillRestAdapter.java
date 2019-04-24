package com.ict.delivirko.adapter.restaurant;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseReportOrder;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.BillOrders;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

import java.util.List;
import java.util.Vector;

public class BillRestAdapter extends RecyclerView.Adapter<BillRestAdapter.MyViewHolder> {
    Context context;
    boolean isSheet;
    List<BillOrders> billOrders;


    public BillRestAdapter(Context context, List<BillOrders> billOrders, boolean isSheet) {
        this.context = context;
        this.billOrders = billOrders;
        this.isSheet = isSheet;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_bill_rest, viewGroup, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final BillOrders billOrders1 = billOrders.get(i);

        myViewHolder.tvDateBillRest.setText(billOrders1.getDate());
        myViewHolder.tvTotalOrderBill.setText(billOrders1.getPrice() + "");

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSheet)
                    showDialog(billOrders1.getDate());
//                if (isFinal)
//                    orderDetails();
            }
        });
    }

    @Override
    public int getItemCount() {
        return billOrders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateBillRest, tvTotalOrderBill;
        ImageView imgIsPayedBillRest;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateBillRest = itemView.findViewById(R.id.tvDateBillRest);
            tvTotalOrderBill = itemView.findViewById(R.id.tvTotalOrderBill);
            imgIsPayedBillRest = itemView.findViewById(R.id.imgIsPayedBillRest);

        }
    }

    private void showDialog(String date) {

        ///bottom sheet 2
        ImageView imgCancel;
        RecyclerView rvDailyReport;
        BillRestSheetAdapter adapter;
        List<BillOrders> billOrders2;

        final BottomSheetDialog sheetDailyReport = new BottomSheetDialog(context, R.style.SheetDialog);
        sheetDailyReport.setContentView(R.layout.sheet_daily_report_rest);
        sheetDailyReport.setCancelable(false);
        sheetDailyReport.show();
        imgCancel = sheetDailyReport.findViewById(R.id.imgCancelDailyReport);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDailyReport.dismiss();
            }
        });
        billOrders2 = new Vector<>();
        rvDailyReport = sheetDailyReport.findViewById(R.id.rvDailyReport);
        adapter = new BillRestSheetAdapter(context, billOrders2);
        rvDailyReport.setAdapter(adapter);
        rvDailyReport.setLayoutManager(new LinearLayoutManager(context));

        ReportBillCompany(billOrders2, adapter, date, context);

    }

    public void ReportBillCompany(final List<BillOrders> billOrders2, final BillRestSheetAdapter adapter, final String date, final Context context) {
        Constants.showSimpleProgressDialog(context, context.getResources().getString(R.string.Loading));

        new UserAPI().ReportBillRest(date, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseReportOrder result1 = (ResponseReportOrder) result;
                billOrders2.clear();
                if (result1.isStatus()) {
                    billOrders2.addAll(result1.getReportData().getOrders());
                    adapter.notifyDataSetChanged();
                } else {
                    Constants.showDialog((Activity) context, result1.getMessage());
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
