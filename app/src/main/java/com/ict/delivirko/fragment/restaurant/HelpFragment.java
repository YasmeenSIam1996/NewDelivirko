package com.ict.delivirko.fragment.restaurant;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.R;

public class HelpFragment extends Fragment {

    TabLayout tabsHelp;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        getFragmentManager().beginTransaction().replace(R.id.containerHelp, new ContactsFragment()).commit();

        tabsHelp = view.findViewById(R.id.tabsHelp);

        for (int i = 0; i < tabsHelp.getTabCount(); i++) {
            View tab = ((ViewGroup) tabsHelp.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            int margin = 10;
            if (tabsHelp.getTabAt(i).getPosition() == 0) {
                p.setMarginEnd(margin);
            } else if (tabsHelp.getTabAt(i).getPosition() + 1 == tabsHelp.getTabCount()) {
                p.setMarginStart(margin);
            } else
                p.setMargins(margin, 0, margin, 0);
            tab.requestLayout();
        }

        tabsHelp.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    getFragmentManager().beginTransaction().replace(R.id.containerHelp, new ContactsFragment()).commit();
                else
                    getFragmentManager().beginTransaction().replace(R.id.containerHelp, new FAQFragment()).commit();
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
