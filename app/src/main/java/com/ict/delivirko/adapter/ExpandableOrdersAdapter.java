package com.ict.delivirko.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseMyOrderDetails;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.pilot.MyOrder;
import com.ict.delivirko.Module.pilot.MyOrderDetails;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.fragment.pilot.MyOrderData;


public class ExpandableOrdersAdapter extends BaseExpandableListAdapter {

    private Context context;
    private MyOrderData myOrderData;

    public ExpandableOrdersAdapter(Context context, MyOrderData myOrderData) {
        this.context = context;
        this.myOrderData = myOrderData;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return myOrderData.getMyOrder().get(listPosition).getMyOrderDetails().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final MyOrderDetails myOrderDetails = myOrderData.getMyOrder().get(listPosition).getMyOrderDetails().get(expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_order_details, null);

            TextView tvOrderTime = convertView.findViewById(R.id.tvOrderTime);
            TextView tvOrderDate = convertView.findViewById(R.id.tvOrderDate);
            ImageView imgIsPayed = convertView.findViewById(R.id.imgIsPayed);

            tvOrderTime.setText(myOrderDetails.getTime());
            tvOrderDate.setText(myOrderDetails.getDate());

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilotOrderDetails(myOrderDetails.getId() + "", context);
            }
        });

        return convertView;
    }

    private void showDialog(com.ict.delivirko.Module.MyOrderDetails myOrderDetails) {
        ImageView imgCancelDetailsPilot;

        final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.SheetDialog);
        dialog.setContentView(R.layout.sheet_orders_details_pilot);
        dialog.setCancelable(true);
        dialog.show();

        imgCancelDetailsPilot = dialog.findViewById(R.id.imgCancelDetailsPilot);
        imgCancelDetailsPilot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvOrderIdValue = dialog.findViewById(R.id.tvOrderIdValue);
        TextView tvTimeZonePilotSheet = dialog.findViewById(R.id.tvTimeZonePilotSheet);
        TextView tvOrderDateValue = dialog.findViewById(R.id.tvOrderDateValue);
        TextView tvOrderClientValue = dialog.findViewById(R.id.tvOrderClientValue);
        TextView tvOrderAddressValue = dialog.findViewById(R.id.tvOrderAddressValue);
        TextView tvOrderPriceValue = dialog.findViewById(R.id.tvOrderPriceValue);
        TextView tvDeliveryPriceValue = dialog.findViewById(R.id.tvDeliveryPriceValue);
        TextView tvOrderPointsValue = dialog.findViewById(R.id.tvOrderPointsValue);
        RatingBar rateBarPilot = dialog.findViewById(R.id.rateBarPilot);
        tvOrderIdValue.setText(myOrderDetails.getId() + "");
        tvTimeZonePilotSheet.setText(myOrderDetails.getTime());
        tvOrderDateValue.setText(myOrderDetails.getDate());
        tvOrderClientValue.setText(myOrderDetails.getName());
        tvOrderAddressValue.setText(myOrderDetails.getPlace() + " " + myOrderDetails.getStreet() + " " + myOrderDetails.getBuilding_no() + " " + myOrderDetails.getFloor_no() + " " + myOrderDetails.getApartment_no());
        tvOrderPriceValue.setText(myOrderDetails.getPrice() + "");
        tvDeliveryPriceValue.setText(myOrderDetails.getShipping() + "");
        tvOrderPointsValue.setText(myOrderDetails.getPoints() + "");
        rateBarPilot.setRating(myOrderDetails.getCompany_rating());

    }


    @Override
    public int getChildrenCount(int listPosition) {
        return myOrderData.getMyOrder().get(listPosition).getMyOrderDetails().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return myOrderData.getMyOrder().get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return myOrderData.getMyOrder().size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MyOrder myOrder = myOrderData.getMyOrder().get(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_order_pilot, null);
            TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
            TextView tvOrderTime = convertView.findViewById(R.id.tvOrderTime);
            TextView tvOrderDate = convertView.findViewById(R.id.tvOrderDate);
            ImageView imgIsPayed = convertView.findViewById(R.id.imgIsPayed);
            tvOrderId.setText(myOrder.getId() + "");
            tvOrderTime.setText(myOrder.getTime() + "");
            tvOrderDate.setText(myOrder.getDate() + "");

        }

        if (listPosition % 2 == 0) {
            LinearLayout containerOrders = convertView.findViewById(R.id.containerOrders);
            containerOrders.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    public void PilotOrderDetails(final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(context, context.getResources().getString(R.string.Loading));

        new UserAPI().PilotOrderDetails(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseMyOrderDetails result1 = (ResponseMyOrderDetails) result;
                if (result1.isStatus()) {

                    showDialog(result1.getMyOrderDetails());

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
