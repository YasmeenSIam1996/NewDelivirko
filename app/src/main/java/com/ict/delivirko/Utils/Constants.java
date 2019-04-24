package com.ict.delivirko.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Activities.SelectServiceActivity;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;


/**
 * Created by Ahmed hani on 11/5/2017.
 */

public class Constants {

    public static final String FONTS_APP = "sf_pro_text_semibold.otf";

    public static final String BASE_DRIVER_URL = "http://142.93.203.170/api/driver/";
    public static final String BASE_COMPANY_URL = "http://142.93.203.170/api/company/";


    public static final String BASE_URL = "http://142.93.203.170/api/";

    public static final String Image_URL = "http://142.93.203.170/public/uploads/";
    public static final String LOGIN_DRIVER = BASE_DRIVER_URL + "login";
    public static final String LOGOUT = BASE_URL + "logout";
    public static final String FORGET_PASS = BASE_URL + "forgot/password";
    public static final String LOGIN_REST = BASE_COMPANY_URL + "login";
    public static final String COMPANY_DATA = BASE_COMPANY_URL + "data";
    public static final String SET_FCM = BASE_URL + "setFcmToken";
    public static final String CREATE_ORDER = BASE_COMPANY_URL + "createOrder";
    public static final String CANCEL_ORDER = BASE_COMPANY_URL + "cancelOrder";
    public static final String MY_ORDER = BASE_COMPANY_URL + "myOrder";
    public static final String ORDER_DETAILS = BASE_COMPANY_URL + "myOrderDetails";
    public static final String GET_COMPANY_DATA = BASE_COMPANY_URL + "getMakeOrderData";
    public static final String UPDATE_PROFILE_RES = BASE_COMPANY_URL + "updateProfile";
    public static final String MAKE_ORDER = BASE_COMPANY_URL + "makeOrder";
    public static final String TRACKING_ORDER = BASE_COMPANY_URL + "trackingOrders";
    public static final String TRACKING_ORDER_DATA = BASE_COMPANY_URL + "trackingOrderData";
    public static final String TRACKING_ORDER_DETAILS = BASE_COMPANY_URL + "trackingOrderDetails";
    public static final String DRIVER_DATA = BASE_DRIVER_URL + "data";
    public static final String REJECT_ORDER = BASE_DRIVER_URL + "rejectOrder";
    public static final String ACCEPT_ORDER = BASE_DRIVER_URL + "approveOrder";
    public static final String START_TRAVEL = BASE_DRIVER_URL + "startOrder";
    public static final String CANCEL_TRAVEL = BASE_DRIVER_URL + "cancelOrder";
    public static final String RETURN_ORDER = BASE_DRIVER_URL + "returnOrder";
    public static final String FINISH_ORDER = BASE_DRIVER_URL + "finishOrder";
    public static final String ORDER_ON_THE_WAY = BASE_DRIVER_URL + "orderOnTheWay";
    public static final String CHECK_STATUS = BASE_DRIVER_URL + "checkStatus";
    public static final String DRIVER_BILL = BASE_DRIVER_URL + "bill";
    public static final String DRIVER_ORDER = BASE_DRIVER_URL + "myOrder";
    public static final String UPDATE_PROFILE = BASE_DRIVER_URL + "updateProfile";
    public static final String CONDITION = BASE_URL + "conditions";
    public static final String PILOT_ORDER_DETAILS = BASE_DRIVER_URL + "myOrderDetails";
    public static final String COMPANY_BILL = BASE_COMPANY_URL + "bill";
    public static final String REPORT_BILL = BASE_COMPANY_URL + "report";
    public static final String CONTACT = BASE_URL + "contacts";
    public static final String QUESTIONS = BASE_URL + "questions";
    public static final String FREE_TRIPS = BASE_COMPANY_URL + "freeTrips";


    public static final String ORIGINS = "origins";
    public static final String DESTINATION = "destinations";
    public static final String SENSOR = "sensor";
    public static final String MODE = "mode";
    public static final String DISTANCE = "distance";
    public static final String TIME = "time";
    public static final Object GOOGLE_API_KEY = "AIzaSyCq89vpwa_xekSic9fTX15W2T_08UN0obE";
    public static final String LANGUAGE = "language";
    public static final String GOOGLE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?";

    public static Dialog mProgressDialog;

    public static String getLanguage() {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            return "en";
        } else {
            return "ar";
        }
    }

    public static void showDialog(Activity context, String message) {
        Alerter.create(context)
                .setText(message)
                .hideIcon()
//                .setIcon(ContextCompat.getDrawable(context, R.drawable.pilot_logo))
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }


    public static void showForgetPasswordDialog(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_forget_password);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tvCloseFDialog = dialog.findViewById(R.id.tvCloseFDialog);
        final EditText txtForget1 = dialog.findViewById(R.id.txtForget1);
        Button rest_btn = dialog.findViewById(R.id.rest_btn);
        dialog.setCancelable(false);
        dialog.show();
        tvCloseFDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidationEmptyInput(txtForget1)) {
                    Toast.makeText(context, context.getResources().getString(R.string.EpityEmail), Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(txtForget1)) {
                    Toast.makeText(context, context.getResources().getString(R.string.EmailValid), Toast.LENGTH_LONG).show();
                } else {
                    ForgetPass(txtForget1.getText().toString(), context);
                }
            }
        });
    }


    public static boolean ValidationEmptyInput(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return false;
        }
        return true;

    }

    public static boolean isValidEmail(EditText text) {
        CharSequence target = text.getText().toString();
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static void showSimpleProgressDialog(Context context, String msg) {
        if (context != null) {
            mProgressDialog = new Dialog(context, R.style.DialogSlideAnim_leftright);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.animation_loading);
            TextView tv_title = mProgressDialog.findViewById(R.id.tv_title);
            tv_title.setText(msg);

            mProgressDialog.show();
        }
    }


//    public static ProgressDialog getSimpleProgressDialog(Context context, String msg, boolean isCancelable) {
//        ProgressDialog mProgressDialog = new ProgressDialog(context);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setMessage(msg);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
//        return mProgressDialog;
//    }

    public static void removeProgressDialog() {
        if (null != mProgressDialog)
            mProgressDialog.dismiss();
    }


    public static void showCustomDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        TextView textView = dialog.findViewById(R.id.text);
        textView.setText(context.getResources().getString(R.string.logo_out));
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getInstance().IsDriverUserLoggedIn()) {
                    logout(context);

                } else {
                    context.startActivity(new Intent(context, SelectServiceActivity.class));
                    ((Activity) context).finish();
                }

            }
        });

        dialog.show();

    }


    public static void logout(final Context context) {
        Constants.showSimpleProgressDialog(context, context.getResources().getString(R.string.Loading));

        new UserAPI().logout(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    ApplicationController.getInstance().deleteUser();
                    context.startActivity(new Intent(context, SelectServiceActivity.class));
                    ((Activity) context).finish();
                } else {
                    removeProgressDialog();
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
                removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }


    public static void ForgetPass(final String email, final Context context) {
        Constants.showSimpleProgressDialog(context, context.getResources().getString(R.string.Loading));

        new UserAPI().ForgetPass(email, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject != null) {
                    if (responseObject.isStatus()) {
                        Constants.showDialog((Activity) context, responseObject.getMessage());
                    } else {
                        Constants.showDialog((Activity) context, responseObject.getMessage());
                    }
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
