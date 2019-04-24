package com.ict.delivirko.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.ResponseSign;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.UserDriver;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

public class LoginRestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLoginRest;
    private TextView tvForgetRest;
    private EditText edt_password, edt_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_rest);
        btnLoginRest = findViewById(R.id.btnLoginRest);
        tvForgetRest = findViewById(R.id.tvForgetRest);
        edt_password = findViewById(R.id.edt_password);
        edt_mobile = findViewById(R.id.edt_mobile);

        btnLoginRest.setOnClickListener(this);
        tvForgetRest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginRest:
                if (!Constants.ValidationEmptyInput(edt_mobile)) {
                    Constants.showDialog(LoginRestActivity.this, getResources().getString(R.string.EpityMobile));
                } else if (!Constants.ValidationEmptyInput(edt_password)) {
                    Constants.showDialog(LoginRestActivity.this, getResources().getString(R.string.EpityPassword));
                } else {
                    RestLogin(edt_mobile.getText().toString(), edt_password.getText().toString());
                }
                break;
            case R.id.tvForgetRest:
                Constants.showForgetPasswordDialog(LoginRestActivity.this);
                break;
        }
    }

    public void RestLogin(final String mobile, final String Password) {
        Constants.showSimpleProgressDialog(LoginRestActivity.this, getResources().getString(R.string.Loading));
        btnLoginRest.setEnabled(false);

        new UserAPI().Login(Constants.LOGIN_REST, mobile,"","", Password, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign != null) {
                    if (responseSign.isStatus()) {
                        UserDriver userDriver = responseSign.getUser();
                        userDriver.setDriver(false);
                        ApplicationController.getInstance().loginUser(userDriver);
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                String deviceToken = instanceIdResult.getToken();

                                setFcm(deviceToken, LoginRestActivity.this);

                            }
                        });

                    } else {
                        Constants.showDialog(LoginRestActivity.this, responseSign.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(LoginRestActivity.this, responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
                btnLoginRest.setEnabled(true);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(LoginRestActivity.this, message);
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
                        startActivity(new Intent(LoginRestActivity.this, HomeRestaurantActivity.class));
                        finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginRestActivity.this, SelectServiceActivity.class));

    }

}
