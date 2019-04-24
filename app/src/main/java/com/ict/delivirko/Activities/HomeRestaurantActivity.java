package com.ict.delivirko.Activities;

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
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.fragment.ConditionsFragment;
import com.ict.delivirko.fragment.restaurant.BillRestFragment;
import com.ict.delivirko.fragment.restaurant.FollowOrdersFragment;
import com.ict.delivirko.fragment.restaurant.FreeOrdersFragment;
import com.ict.delivirko.fragment.restaurant.HelpFragment;
import com.ict.delivirko.fragment.restaurant.MapRestaurantFragment;
import com.ict.delivirko.fragment.restaurant.MyAccountRestFragment;
import com.ict.delivirko.fragment.restaurant.MyOrdersRestFragment;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class HomeRestaurantActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isLeftToRight;
    private ResideMenu resideMenu;
    private LinearLayout restNavMain, restNavMyOrders, restNavFollowOrders, restNavAccount,
            restNavFreeOrders, restNavSettings, restNavSupply, restNavConditions;
    private TextView txtLogout;
    private TextView tvMenuPilotName;
    private ImageView imgRestaurantLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbarRest);
        setSupportActionBar(toolbar);

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


        getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new MapRestaurantFragment()).commit();


        setUpMenu();

        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.side_menu_bg));
                }
            }

            @Override
            public void closeMenu() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(HomeRestaurantActivity.this, R.color.colorPrimaryDark));
                }
            }
        });

    }

    private void setUpMenu() {

        View view;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR;
        }

        if (isLeftToRight) {
            resideMenu = new ResideMenu(this, R.layout.rest_side_menu, -1);
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
            view = resideMenu.getLeftMenuView();
        } else {
            resideMenu = new ResideMenu(this, -1, R.layout.rest_side_menu);
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
            view = resideMenu.getRightMenuView();
        }

        resideMenu.attachToActivity(this);
        resideMenu.setShadowVisible(false);

        resideMenu.setScaleValue(0.6f);
        resideMenu.setBackgroundColor(ContextCompat.getColor(HomeRestaurantActivity.this, R.color.side_menu_bg));

        restNavMain = view.findViewById(R.id.restNavMain);
        restNavMyOrders = view.findViewById(R.id.restNavMyOrders);
        restNavFollowOrders = view.findViewById(R.id.restNavFollowOrders);
        restNavAccount = view.findViewById(R.id.restNavAccount);
        restNavFreeOrders = view.findViewById(R.id.restNavFreeOrders);
        restNavSettings = view.findViewById(R.id.restNavSettings);
        restNavSupply = view.findViewById(R.id.restNavSupply);
        restNavConditions = view.findViewById(R.id.restNavConditions);
        txtLogout = view.findViewById(R.id.txtLogout);
        tvMenuPilotName = view.findViewById(R.id.tvMenuRestName);
        imgRestaurantLogo = view.findViewById(R.id.imgRestaurantLogo);
        restNavMain.setOnClickListener(this);
        restNavMyOrders.setOnClickListener(this);
        restNavFollowOrders.setOnClickListener(this);
        restNavAccount.setOnClickListener(this);
        restNavFreeOrders.setOnClickListener(this);
        restNavSettings.setOnClickListener(this);
        restNavSupply.setOnClickListener(this);
        restNavConditions.setOnClickListener(this);
        txtLogout.setOnClickListener(this);

        tvMenuPilotName.setText(ApplicationController.getInstance().getUser().getName());
        Picasso.with(this).load(Constants.Image_URL + ApplicationController.getInstance().getUser().getImage()).fit()
                .into(imgRestaurantLogo);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restNavMain:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new MapRestaurantFragment()).commit();
                break;
            case R.id.restNavMyOrders:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new MyOrdersRestFragment()).commit();
                break;
            case R.id.restNavFollowOrders:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new FollowOrdersFragment()).commit();
                break;
            case R.id.restNavAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new BillRestFragment()).commit();
                break;
            case R.id.restNavFreeOrders:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new FreeOrdersFragment()).commit();
                break;
            case R.id.restNavSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new MyAccountRestFragment()).commit();
                break;
            case R.id.restNavSupply:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new HelpFragment()).commit();
                break;
            case R.id.restNavConditions:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerRestaurant, new ConditionsFragment()).commit();
                break;
            case R.id.txtLogout:
                Constants.showCustomDialog(HomeRestaurantActivity.this);
        }

        resideMenu.closeMenu();
    }

    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened())
            resideMenu.closeMenu();
        else
            super.onBackPressed();
    }

}






