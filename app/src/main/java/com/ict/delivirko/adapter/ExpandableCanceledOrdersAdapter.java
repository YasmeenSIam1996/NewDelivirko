package com.ict.delivirko.adapter;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.ict.delivirko.Module.pilot.CanceledOrder;
import com.ict.delivirko.Module.pilot.CanceledOrdersDetails;
import com.ict.delivirko.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableCanceledOrdersAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<CanceledOrder> expandableListTitle;
    private HashMap<CanceledOrder, List<CanceledOrdersDetails>> expandableListDetail;


    public ExpandableCanceledOrdersAdapter(Context context, List<CanceledOrder> expandableListTitle,
                                           HashMap<CanceledOrder, List<CanceledOrdersDetails>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_canceled_orders_details, null);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return convertView;
    }

    //sheet dialog method
    private void showDialog() {
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
    }

    //------------------------------------
    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_canceled_orders, null);


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
}
