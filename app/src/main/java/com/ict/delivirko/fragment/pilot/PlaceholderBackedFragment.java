package com.ict.delivirko.fragment.pilot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ict.delivirko.Activities.ExpandableCanceledOrdersData;
import com.ict.delivirko.Module.pilot.CanceledOrder;
import com.ict.delivirko.Module.pilot.CanceledOrdersDetails;
import com.ict.delivirko.R;
import com.ict.delivirko.adapter.ExpandableCanceledOrdersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceholderBackedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PlaceholderBackedFragment() {
        // Required empty public constructor
    }

    public static PlaceholderBackedFragment newInstance(String param1, String param2) {
        PlaceholderBackedFragment fragment = new PlaceholderBackedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ExpandableListView listBackedOrders;
    ExpandableCanceledOrdersAdapter adapter;
    ExpandableCanceledOrdersData data;
    ArrayList<CanceledOrder> objects;
    HashMap<CanceledOrder, List<CanceledOrdersDetails>> hashData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_placeholder_backed, container, false);
        listBackedOrders = v.findViewById(R.id.listBackedOrders);

        hashData = ExpandableCanceledOrdersData.getData();
        objects = new ArrayList<>(hashData.keySet());
        adapter = new ExpandableCanceledOrdersAdapter(getContext(), objects, hashData);
        listBackedOrders.setAdapter(adapter);

        listBackedOrders.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView indicatorCanceled = v.findViewById(R.id.indicatorCanceled);
                if (listBackedOrders.isGroupExpanded(groupPosition)) {

                    indicatorCanceled.setImageResource(R.drawable.ic_expandable_closed);
                }else {
                    indicatorCanceled.setImageResource(R.drawable.ic_expandable_open);
                }

                return false;
            }
        });
        return v;
    }


}
