package com.ict.delivirko.fragment.restaurant;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.R;

import static com.ict.delivirko.fragment.restaurant.SubOrdersRestFragment.ARG_PARAM1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersRestFragment extends Fragment {

    TabLayout tabsMyOrdersRest;


    public MyOrdersRestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_orders_rest, container, false);
        SubOrdersRestFragment subOrdersRestFragment = new SubOrdersRestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PARAM1, 4);
        subOrdersRestFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.containerSubOrdersRest, subOrdersRestFragment).commit();

        tabsMyOrdersRest = view.findViewById(R.id.tabsMyOrdersRest);
        for (int i = 0; i < tabsMyOrdersRest.getTabCount(); i++) {
            View tab = ((ViewGroup) tabsMyOrdersRest.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            int margin = 10;
            if (tabsMyOrdersRest.getTabAt(i).getPosition() == 0) {
                p.setMarginEnd(margin);
            } else if (tabsMyOrdersRest.getTabAt(i).getPosition() + 1 == tabsMyOrdersRest.getTabCount()) {
                p.setMarginStart(margin);
            } else
                p.setMargins(margin, 0, margin, 0);
            tab.requestLayout();
        }


        tabsMyOrdersRest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SubOrdersRestFragment subOrdersRestFragment = new SubOrdersRestFragment();
                Bundle bundle = new Bundle();
                if (tab.getPosition() == 0) {
                    bundle.putInt(ARG_PARAM1, 4);
                } else if (tab.getPosition() == 1) {
                    bundle.putInt(ARG_PARAM1, 5);
                } else if (tab.getPosition() == 2) {
                    bundle.putInt(ARG_PARAM1, 2);
                }
                subOrdersRestFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.containerSubOrdersRest, subOrdersRestFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


}
