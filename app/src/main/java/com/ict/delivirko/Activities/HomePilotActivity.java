package com.ict.delivirko.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.AlarmReceive;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.fragment.ConditionsFragment;
import com.ict.delivirko.fragment.pilot.BillPilotFragment;
import com.ict.delivirko.fragment.pilot.MyAccountFragment;
import com.ict.delivirko.fragment.pilot.PilotMapFragment;
import com.ict.delivirko.fragment.pilot.PilotOrdersFragment;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class HomePilotActivity extends AppCompatActivity implements View.OnClickListener {

    boolean isLeftToRight;
    private ResideMenu resideMenu;
    private LinearLayout pilotNavMain, pilotNavOrders, pilotNavAccount, pilotNavSettings, pilotNavConditions;
    private ImageView txtLogout;
    private TextView tvMenuPilotName;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private ImageView tvMenuPilotImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pilot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Location Driver
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceive.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000,
                    pendingIntent);
        }
        ///////////////////////////


        try {
            if (!getIntent().getStringExtra("Message").trim().equals("")) {
                Constants.showDialog(HomePilotActivity.this, getIntent().getStringExtra("Message"));
            }
        } catch (Exception e) {

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new PilotMapFragment()).commit();

        //side menu code
        setUpMenu();

        toolbar.setNavigationIcon(R.drawable.ic_nav_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeftToRight)
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                else
                    resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });

        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(HomePilotActivity.this, R.color.side_menu_bg));
                }
            }

            @Override
            public void closeMenu() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(HomePilotActivity.this, R.color.colorPrimaryDark));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pilotNavMain:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new PilotMapFragment()).commit();
                break;
            case R.id.pilotNavOrders:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new PilotOrdersFragment()).commit();
                break;
            case R.id.pilotNavAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new BillPilotFragment()).commit();
                break;
            case R.id.pilotNavSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new MyAccountFragment()).commit();
                break;
            case R.id.pilotNavConditions:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerPilot, new ConditionsFragment()).commit();
                break;
            case R.id.txtLogout:
                Constants.showCustomDialog(HomePilotActivity.this);
                break;
        }

        resideMenu.closeMenu();
    }

    private void setUpMenu() {

        View view;

        isLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR;

        if (isLeftToRight) {
            resideMenu = new ResideMenu(this, R.layout.pilot_side_menu, -1);
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
            view = resideMenu.getLeftMenuView();
        } else {
            resideMenu = new ResideMenu(this, -1, R.layout.pilot_side_menu);
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
            view = resideMenu.getRightMenuView();
        }

        resideMenu.attachToActivity(this);
        resideMenu.setShadowVisible(false);

        resideMenu.setScaleValue(0.6f);
        resideMenu.setBackgroundColor(ContextCompat.getColor(HomePilotActivity.this, R.color.side_menu_bg));

        pilotNavMain = view.findViewById(R.id.pilotNavMain);
        pilotNavOrders = view.findViewById(R.id.pilotNavOrders);
        pilotNavAccount = view.findViewById(R.id.pilotNavAccount);
        pilotNavSettings = view.findViewById(R.id.pilotNavSettings);
        pilotNavConditions = view.findViewById(R.id.pilotNavConditions);
        txtLogout = view.findViewById(R.id.txtLogout);

        tvMenuPilotName=view.findViewById(R.id.tvMenuPilotName);
        tvMenuPilotImage=view.findViewById(R.id.tvMenuPilotImage);

        pilotNavMain.setOnClickListener(this);
        pilotNavOrders.setOnClickListener(this);
        pilotNavAccount.setOnClickListener(this);
        pilotNavSettings.setOnClickListener(this);
        pilotNavConditions.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        tvMenuPilotName.setText(ApplicationController.getInstance().getUser().getName());
        Picasso.with(this).load(Constants.Image_URL + ApplicationController.getInstance().getUser().getImage()).fit()
                .into(tvMenuPilotImage);
    }


    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened())
            resideMenu.closeMenu();
        else
            super.onBackPressed();
    }
}
