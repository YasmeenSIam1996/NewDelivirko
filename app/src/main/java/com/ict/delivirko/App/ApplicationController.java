package com.ict.delivirko.App;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.ict.delivirko.API.VolleySingleton;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.UserDriver;
import com.ict.delivirko.Module.restaurant.OrderTemp;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;


public class ApplicationController extends Application {

    private static ApplicationController mInstance;
    private static Context context;
    private Locale myLocale;
    public static int langNum = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        context = getApplicationContext();
        mInstance = this;
        VolleySingleton.getInstance();
        FirebaseApp.initializeApp(this);
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String lang = prefs.getString("Language", "en");
        changeLang(lang);

    }


    public static synchronized ApplicationController getInstance() {
        if (mInstance == null)
            return mInstance = new ApplicationController();
        else
            return mInstance;
    }


    public static Context getAppContext() {
        return ApplicationController.context;
    }


    public OrderTemp getOrderTemp() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("orderTemp", Context.MODE_PRIVATE);
        OrderTemp orderTemp = new OrderTemp();
        orderTemp.setId(sharedPreferences.getInt("OrderId", 0));
        orderTemp.setStatus(sharedPreferences.getInt("OrderStatus", 0));


        return orderTemp;
    }

    public UserDriver getUser() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
        UserDriver user = new UserDriver();
        user.setEmail(sharedPreferences.getString("UserEmail", ""));
        user.setId(sharedPreferences.getInt("UserId", 0));
        user.setCar_number(sharedPreferences.getString("car_number", ""));
        user.setPhone(sharedPreferences.getString("UserPhone", ""));
        user.setName(sharedPreferences.getString("UserName", ""));
        user.setToken(sharedPreferences.getString("UserToken", ""));
        user.setDriver(sharedPreferences.getBoolean("isDriver", false));
        user.setImage(sharedPreferences.getString("Image",""));


        return user;
    }

    public void loginUser(final UserDriver user) {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", user.getToken());
        editor.putString("UserEmail", user.getEmail());
        editor.putString("UserName", user.getName());
        editor.putString("UserPhone", user.getPhone());
        editor.putInt("UserId", user.getId());
        editor.putString("car_number", user.getCar_number());
        editor.putBoolean("isDriver", user.isDriver());
        editor.putString("Image", user.getImage());

        editor.commit();
    }

    public void SetTempOrder(final OrderTemp orderTemp) {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("orderTemp", Context.MODE_PRIVATE).edit();
        editor.putInt("OrderId", orderTemp.getId());
        editor.putInt("OrderStatus", orderTemp.getId());

        editor.commit();
    }


    public void SetOrderNotification(final OrderNotification orderNotification) {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("OrderNotification", Context.MODE_PRIVATE).edit();
        editor.putString("status_id", orderNotification.getStatus_id());
        editor.putString("duration", orderNotification.getDuration());
        editor.putString("driver_id", orderNotification.getDriver_id());
        editor.putString("driver_car_number", orderNotification.getDriver_car_number());
        editor.putString("driver_phone", orderNotification.getDriver_phone());
        editor.putString("distance", orderNotification.getDistance());
        editor.putString("driver_lat", orderNotification.getDriver_lat());
        editor.putString("driver_lng", orderNotification.getDriver_lng());
        editor.putString("order_id", orderNotification.getOrder_id());
        editor.putString("driver_name", orderNotification.getDriver_name());
        editor.putString("driver_rating", orderNotification.getDriver_rating());
        editor.putString("order_status_text", orderNotification.getOrder_status_text());
        editor.putString("driver_image", orderNotification.getDriver_image());
        editor.putInt("driver_rating_count", orderNotification.getDriver_rating_count());
        editor.putString("waiting_time", orderNotification.getWaiting_time());
        editor.putString("company_name", orderNotification.getCompany_name());
        editor.putString("company_address", orderNotification.getCompany_address());
        editor.putString("company_lng", orderNotification.getCompany_lng());
        editor.putString("company_lat", orderNotification.getCompany_lat());

        editor.commit();
    }


    public OrderNotification getOrderNotification() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("OrderNotification", Context.MODE_PRIVATE);
        OrderNotification notification = new OrderNotification();

        notification.setStatus_id(sharedPreferences.getString("status_id", ""));
        notification.setDuration(sharedPreferences.getString("duration", ""));
        notification.setDriver_id(sharedPreferences.getString("driver_id", ""));
        notification.setDriver_car_number(sharedPreferences.getString("driver_car_number", ""));
        notification.setDriver_phone(sharedPreferences.getString("driver_phone", ""));
        notification.setDistance(sharedPreferences.getString("distance", ""));
        notification.setDriver_lat(sharedPreferences.getString("driver_lat", ""));
        notification.setDriver_lng(sharedPreferences.getString("driver_lng", ""));
        notification.setOrder_id(sharedPreferences.getString("order_id", ""));
        notification.setDriver_name(sharedPreferences.getString("driver_name", ""));
        notification.setDriver_rating(sharedPreferences.getString("driver_rating", ""));
        notification.setOrder_status_text(sharedPreferences.getString("order_status_text", ""));
        notification.setDriver_image(sharedPreferences.getString("driver_image", ""));
        notification.setDriver_rating_count(sharedPreferences.getInt("driver_rating_count", 0));
        notification.setWaiting_time(sharedPreferences.getString("waiting_time", ""));
        notification.setCompany_address(sharedPreferences.getString("company_address", ""));
        notification.setCompany_name(sharedPreferences.getString("company_name", ""));
        notification.setCompany_lat(sharedPreferences.getString("company_lng", ""));
        notification.setCompany_lng(sharedPreferences.getString("company_lat", ""));

        return notification;
    }


    public void deleteOrderNotification() {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("OrderNotification", Context.MODE_PRIVATE).edit();
        editor.putString("status_id", "-1");
        editor.putString("duration", "");
        editor.putString("driver_id", "");
        editor.putString("driver_car_number", "");
        editor.putString("driver_phone", "");
        editor.putString("distance", "");
        editor.putString("driver_lat", "");
        editor.putString("driver_lng", "");
        editor.putString("order_id", "");
        editor.putString("driver_name", "");
        editor.putString("driver_rating", "");
        editor.putString("order_status_text", "");
        editor.putString("driver_image", "");
        editor.putInt("driver_rating_count", 0);
        editor.putString("waiting_time", "");
        editor.putString("company_name", "");
        editor.putString("company_address", "");
        editor.putString("company_lng", "");
        editor.putString("company_lat", "");

        editor.commit();
    }

    public void deleteTempOrder() {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("orderTemp", Context.MODE_PRIVATE).edit();
        editor.putString("OrderId", "-1");
        editor.putString("OrderStatus", "");
        editor.commit();
    }

    public void deleteUser() {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", "");
        editor.putString("UserEmail", "");
        editor.putString("UserName", "");
        editor.putString("UserPhone", "");
        editor.putInt("UserId", 0);
        editor.putString("car_number", "");
        editor.putBoolean("isDriver", false);
        editor.putString("Image", "");


        editor.commit();
    }


    public boolean IsDriverUserLoggedIn() {
        if (ApplicationController.getInstance().getUser().getName().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }


    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "ar");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (lang.equals("ar")) {
            langNum = 1;
        } else {
            langNum = 0;
        }
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }


}
