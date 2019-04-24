package com.ict.delivirko.adapter.restaurant;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseOrderDetails;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.BillOrders;
import com.ict.delivirko.Module.restaurant.OrderDetails;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

import java.util.List;

public class BillRestSheetAdapter extends RecyclerView.Adapter<BillRestSheetAdapter.MyViewHolder> {
    Context context;
    List<BillOrders> billOrders;

    public BillRestSheetAdapter(Context context, List<BillOrders> billOrders) {
        this.context = context;
        this.billOrders = billOrders;

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
                orderDetails(billOrders1.getId() + "", context);
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

    public void orderDetails(String order_id, final Context context) {

        new UserAPI().orderDetails(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOrderDetails result1 = (ResponseOrderDetails) result;
                if (result1.isStatus()) {
                    showFinalDialog(result1.getOrderDetails());

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

    private void showFinalDialog(OrderDetails orderDetails) {
        ImageView imgCancel;

        final BottomSheetDialog sheetDetailsRest = new BottomSheetDialog(context, R.style.SheetDialog);
        sheetDetailsRest.setContentView(R.layout.sheet_orders_details_rest);
        sheetDetailsRest.setCancelable(false);
        sheetDetailsRest.show();
        imgCancel = sheetDetailsRest.findViewById(R.id.imgCancelDetailsRest);
        TextView tvOrderIdRestValue = sheetDetailsRest.findViewById(R.id.tvOrderIdRestValue);
        TextView tvRestTimeValue = sheetDetailsRest.findViewById(R.id.tvRestTimeValue);
        TextView tvOrderDateRestValue = sheetDetailsRest.findViewById(R.id.tvOrderDateRestValue);
        TextView tvPilotNameValue = sheetDetailsRest.findViewById(R.id.tvPilotNameValue);
        TextView tvPilotCodeValue = sheetDetailsRest.findViewById(R.id.tvPilotCodeValue);
        TextView tvAddressRestValue = sheetDetailsRest.findViewById(R.id.tvAddressRestValue);
        TextView tvRestPriceValue = sheetDetailsRest.findViewById(R.id.tvRestPriceValue);
        TextView tvDeliveryPriceValue = sheetDetailsRest.findViewById(R.id.tvDeliveryPriceValue);
        TextView tvRestPointsValue = sheetDetailsRest.findViewById(R.id.tvRestPointsValue);
        TextView tvOrderTypeValue = sheetDetailsRest.findViewById(R.id.tvOrderTypeValue);
        RatingBar rateBarRest = sheetDetailsRest.findViewById(R.id.rateBarRest);
        tvOrderIdRestValue.setText(orderDetails.getId() + "");
        tvRestTimeValue.setText(orderDetails.getTime());
        tvOrderDateRestValue.setText(orderDetails.getDate());
        tvPilotNameValue.setText(orderDetails.getDriver().getName());
        tvPilotCodeValue.setText(orderDetails.getDriver().getId() + "");
        tvAddressRestValue.setText(orderDetails.getStreet() + " " + orderDetails.getBuilding_no() + " " + orderDetails.getFloor_no());
        tvRestPriceValue.setText(orderDetails.getPrice() + "");
        tvDeliveryPriceValue.setText(orderDetails.getShipping() + "");
        tvRestPointsValue.setText(orderDetails.getPoints() + "");
        tvOrderTypeValue.setText(orderDetails.getOrder_type());
        rateBarRest.setRating((float) orderDetails.getCompany_rating());

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDetailsRest.dismiss();
            }
        });
    }

}
