package com.ict.delivirko.fragment.pilot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.ict.delivirko.R;
import com.ict.delivirko.adapter.BackedOrdersPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackedOrderFragment extends Fragment implements View.OnClickListener {

    ImageButton btnOlderBackedOrders, btnNewerBackedOrders;

    public BackedOrderFragment() {
        // Required empty public constructor
    }


    ViewPager pagerBackedOrders;
    BackedOrdersPagerAdapter backedOrdersPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_backed_order, container, false);

        pagerBackedOrders = v.findViewById(R.id.pagerBackedOrders);

        btnNewerBackedOrders = v.findViewById(R.id.btnNewerBackedOrders);
        btnOlderBackedOrders = v.findViewById(R.id.btnOlderBackedOrders);

        btnNewerBackedOrders.setOnClickListener(this);
        btnOlderBackedOrders.setOnClickListener(this);

        backedOrdersPagerAdapter = new BackedOrdersPagerAdapter(getFragmentManager());
        pagerBackedOrders.setAdapter(backedOrdersPagerAdapter);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        pagerBackedOrders.setAdapter(backedOrdersPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewerBackedOrders:
                pagerBackedOrders.setCurrentItem(pagerBackedOrders.getCurrentItem() + 1, true);
                btnNewerBackedOrders.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;

            case R.id.btnOlderBackedOrders:
                pagerBackedOrders.setCurrentItem(pagerBackedOrders.getCurrentItem() - 1, true);
                btnOlderBackedOrders.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrows_pager_click));
                break;
        }

    }
}
