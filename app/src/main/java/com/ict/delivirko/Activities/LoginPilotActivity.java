package com.ict.delivirko.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.ResponseObjectStatus;
import com.ict.delivirko.API.ResponseSign;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.UserDriver;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

public class LoginPilotActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvForgetPilot;
    private Button btnLoginPilot;
    private EditText edt_password, edt_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pilot);

        tvForgetPilot = findViewById(R.id.tvForgetPilot);
        btnLoginPilot = findViewById(R.id.btnLoginPilot);

        edt_password = findViewById(R.id.edt_password);
        edt_mobile = findViewById(R.id.edt_mobile);

        tvForgetPilot.setOnClickListener(this);
        btnLoginPilot.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvForgetPilot:
                Constants.showForgetPasswordDialog(LoginPilotActivity.this);
                break;
            case R.id.btnLoginPilot:
                if (!Constants.ValidationEmptyInput(edt_mobile)) {
                    Constants.showDialog(LoginPilotActivity.this, getResources().getString(R.string.EpityMobile));
                } else if (!Constants.ValidationEmptyInput(edt_password)) {
                    Constants.showDialog(LoginPilotActivity.this, getResources().getString(R.string.EpityPassword));
                } else {
                    Log.e("LAT", getIntent().getDoubleExtra("LAT", -1) + "");
                    Log.e("LNG",getIntent().getDoubleExtra("LNG", -1) + "" );

                    PilotLogin(edt_mobile.getText().toString(), getIntent().getDoubleExtra("LAT", -1) + "", getIntent().getDoubleExtra("LNG", -1) + "", edt_password.getText().toString());
                }
                break;
        }
    }


    public void PilotLogin(final String mobile, final String lat, final String lng, final String Password) {
        Constants.showSimpleProgressDialog(LoginPilotActivity.this, getResources().getString(R.string.Loading));
        btnLoginPilot.setEnabled(false);

        new UserAPI().Login(Constants.LOGIN_DRIVER, mobile, lat, lng, Password, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign != null) {
                    if (responseSign.isStatus()) {
                        UserDriver userDriver = responseSign.getUser();
                        userDriver.setDriver(true);
                        ApplicationController.getInstance().loginUser(userDriver);
                        setFcm(FirebaseInstanceId.getInstance().getToken(), LoginPilotActivity.this);
                    } else {
                        Constants.showDialog(LoginPilotActivity.this, responseSign.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(LoginPilotActivity.this, responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
                btnLoginPilot.setEnabled(true);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(LoginPilotActivity.this, message);
            }
        });
    }

    public void setFcm(final String token, final Context context) {
        Constants.showSimpleProgressDialog(context, context.getResources().getString(R.string.Loading));

        new UserAPI().setFcm(token, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject != null) {
                    if (responseObject.isStatus()) {
                        CheckStatus(LoginPilotActivity.this);
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


    public void CheckStatus(final Context context) {
        Constants.showSimpleProgressDialog(LoginPilotActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().CheckStatus(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObjectStatus result1 = (ResponseObjectStatus) result;
                Intent intent;
                Log.e("CHECK_STATUS", result1.isStatus() + "");
                if (result1.isStatus()) {
                    if (result1.getStatus_().getStatus_id() == 2) {
                        intent = new Intent(getApplicationContext(), PilotTravelActivity.class);
                        intent.putExtra("isEnd", true);
                    } else if (result1.getStatus_().getStatus_id() == 1) {
                        intent = new Intent(getApplicationContext(), PilotTravelActivity.class);
                        intent.putExtra("isStart", true);
                    }
                    else {
                        intent = new Intent(getApplicationContext(), HomePilotActivity.class);
                        ApplicationController.getInstance().deleteOrderNotification();
                        ApplicationController.getInstance().deleteTempOrder();
                    }
                    intent.putExtra("OrderStatus", result1.getStatus_());
                    startActivity(intent);
                    finish();

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
                Log.e("CHECK_STATUS", message);
                Intent intent = new Intent(getApplicationContext(), HomePilotActivity.class);
                ApplicationController.getInstance().deleteOrderNotification();
                ApplicationController.getInstance().deleteTempOrder();
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginPilotActivity.this,SelectServiceActivity.class));

    }
}
