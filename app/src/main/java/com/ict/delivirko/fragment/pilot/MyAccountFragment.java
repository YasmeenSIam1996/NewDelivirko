package com.ict.delivirko.fragment.pilot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;


public class MyAccountFragment extends Fragment {

    private EditText txtMobileUpdatePilot, txtEmailUpdatePilot, txtNewPassPilot, txtOldPassUpdate, txtConfirmPilot;
    private Button btnUpdatePilot;

    public MyAccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_account_pilot, container, false);
        findViews(v);

        return v;
    }

    private void findViews(View view) {

        txtMobileUpdatePilot = view.findViewById(R.id.txtMobileUpdatePilot);
        txtEmailUpdatePilot = view.findViewById(R.id.txtEmailUpdatePilot);
        txtNewPassPilot = view.findViewById(R.id.txtNewPass);
        txtOldPassUpdate = view.findViewById(R.id.txtOldPass);
        txtConfirmPilot = view.findViewById(R.id.txtConfirm);
        btnUpdatePilot = view.findViewById(R.id.btnUpdatePilot);
        setData();


        btnUpdatePilot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {

                    UpdateProfile(txtMobileUpdatePilot.getText().toString().trim()
                            , txtEmailUpdatePilot.getText().toString().trim()
                            , txtNewPassPilot.getText().toString().trim()
                            , txtOldPassUpdate.getText().toString().trim()
                            , getActivity());
                }
            }
        });

    }

    private void setData() {
        txtMobileUpdatePilot.setText(ApplicationController.getInstance().getUser().getPhone());
        txtEmailUpdatePilot.setText(ApplicationController.getInstance().getUser().getEmail());
    }

    private boolean validateData() {
        if (!Constants.ValidationEmptyInput(txtMobileUpdatePilot)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EpityMobile));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtEmailUpdatePilot)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EpityEmail));
            return false;
        } else if (!isValidEmail(txtEmailUpdatePilot.getText().toString().trim())) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmailNotValidate));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtNewPassPilot)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityNewPass));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtOldPassUpdate)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityOldPass));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtConfirmPilot)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityComfirmPass));
            return false;
        } else if (!txtConfirmPilot.getText().toString().equals(txtNewPassPilot.getText().toString())) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.NotMatchPass));
            return false;
        } else
            return true;

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void UpdateProfile(final String phone, final String email, String newPass, String oldPass, final Context context) {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().UpdateProfile(Constants.UPDATE_PROFILE,phone, email, newPass, oldPass, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    Constants.showDialog(getActivity(), result1.getMessage());
                    ApplicationController.getInstance().getUser().setPhone(phone);
                    ApplicationController.getInstance().getUser().setEmail(email);
                    setData();
                } else {
                    Constants.showDialog(getActivity(), result1.getMessage());
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
