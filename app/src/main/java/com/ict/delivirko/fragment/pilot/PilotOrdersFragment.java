package com.ict.delivirko.fragment.pilot;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.R;

public class PilotOrdersFragment extends Fragment {

    TabLayout ordersTab;

    public PilotOrdersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pilot_orders, container, false);

        ordersTab = v.findViewById(R.id.tabsOrders);

        getFragmentManager().beginTransaction().replace(R.id.ordersContainer, SubOrdersPilotFragment.newInstance(4)).commit();

        //set Margin between Tap
        for (int i = 0; i < ordersTab.getTabCount(); i++) {
            View tab = ((ViewGroup) ordersTab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            int margin = 10;
            if (ordersTab.getTabAt(i).getPosition() == 0) {
                p.setMarginEnd(margin);
            } else if (ordersTab.getTabAt(i).getPosition() + 1 == ordersTab.getTabCount()) {
                p.setMarginStart(margin);
            } else
                p.setMargins(margin, 0, margin, 0);
            tab.requestLayout();
        }
        //////////////////////////

        ordersTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.ordersContainer, SubOrdersPilotFragment.newInstance(4)).commit();
                        break;
                    case 1:
                        getFragmentManager().beginTransaction().replace(R.id.ordersContainer, SubOrdersPilotFragment.newInstance(5)).commit();
                        break;
                    case 2:
                        getFragmentManager().beginTransaction().replace(R.id.ordersContainer, SubOrdersPilotFragment.newInstance(2)).commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

}
