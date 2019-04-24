package com.ict.delivirko.fragment.restaurant;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import static com.ict.delivirko.fragment.pilot.MyAccountFragment.isValidEmail;


public class MyAccountRestFragment extends Fragment {
    private Button btnUpdateRest;
    private EditText txtMobileUpdateRest, txtEmailUpdateRest, txtOldPassUpdate, txtNewPass, txtConfirm;

    public MyAccountRestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account_rest, container, false);

        intiView(view);
        return view;
    }

    private void intiView(View view) {
        txtMobileUpdateRest=view.findViewById(R.id.txtMobileUpdateRest);
        txtEmailUpdateRest=view.findViewById(R.id.txtEmailUpdateRest);
        txtOldPassUpdate=view.findViewById(R.id.txtOldPassUpdate);
        txtNewPass=view.findViewById(R.id.txtNewPass);
        txtConfirm=view.findViewById(R.id.txtConfirm);
        btnUpdateRest=view.findViewById(R.id.btnUpdateRest);
        setData();

        btnUpdateRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {

                    UpdateProfile(txtMobileUpdateRest.getText().toString().trim()
                            , txtEmailUpdateRest.getText().toString().trim()
                            , txtNewPass.getText().toString().trim()
                            , txtOldPassUpdate.getText().toString().trim()
                            , getActivity());
                }
            }
        });
    }

    private void setData() {
        txtMobileUpdateRest.setText(ApplicationController.getInstance().getUser().getPhone());
        txtEmailUpdateRest.setText(ApplicationController.getInstance().getUser().getEmail());
    }

    private boolean validateData() {
        if (!Constants.ValidationEmptyInput(txtMobileUpdateRest)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EpityMobile));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtEmailUpdateRest)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EpityEmail));
            return false;
        } else if (!isValidEmail(txtEmailUpdateRest.getText().toString().trim())) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmailNotValidate));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtNewPass)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityNewPass));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtOldPassUpdate)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityOldPass));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtConfirm)) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.EmpityComfirmPass));
            return false;
        } else if (!txtConfirm.getText().toString().equals(txtNewPass.getText().toString())) {
            Constants.showDialog(getActivity(), getResources().getString(R.string.NotMatchPass));
            return false;
        } else
            return true;

    }




    public void UpdateProfile(final String phone, final String email, String newPass, String oldPass, final Context context) {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().UpdateProfile(Constants.UPDATE_PROFILE_RES,phone, email, newPass, oldPass, new UniversalCallBack() {
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
