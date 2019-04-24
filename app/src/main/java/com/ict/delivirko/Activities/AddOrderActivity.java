package com.ict.delivirko.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseMakeOrder;
import com.ict.delivirko.API.ResponseOrderId;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.restaurant.MakeOrder;
import com.ict.delivirko.Module.restaurant.places;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.restaurant.ListPopupWindowAdapter;

import java.util.List;
import java.util.Vector;

public class AddOrderActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    RadioButton rbCash, rbEPay;
    TextView spinnerCity, tvLocationOnMAp;
    private List<places> placesList;
    private EditText price_range;
    private EditText txtStreet, txtSpecialMark, txtBuildingNo, txtFloorNo, txtFlatNo, txtClientName, txtMobileAddOrder, txtOrderValue;
    private int payment = 0;
    private LinearLayout txtMapAddress;
    private OrderNotification orderNotification;
    private Switch PaymentSwitch;
    private int checked = 0;
    private String LocationName_ = "", toLat = "", toLng = "";
    private Button save;

    private int placeID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        findViews();
        setActions();


    }

    private void setActions() {
        PaymentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checked = 1;
                } else {
                    checked = 0;

                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValidation();
            }
        });
    }

    private void findViews() {
        placesList = new Vector<>();
        rbCash = findViewById(R.id.rbCash);
        rbCash = findViewById(R.id.rbEPay);
        price_range = findViewById(R.id.price_range);
        spinnerCity = findViewById(R.id.spinnerCity);
        txtStreet = findViewById(R.id.txtStreet);
        txtSpecialMark = findViewById(R.id.txtSpecialMark);
        txtBuildingNo = findViewById(R.id.txtBuildingNo);
        txtFloorNo = findViewById(R.id.txtFloorNo);
        txtFlatNo = findViewById(R.id.txtFlatNo);
        txtClientName = findViewById(R.id.txtClientName);
        txtMobileAddOrder = findViewById(R.id.txtMobileAddOrder);
        txtOrderValue = findViewById(R.id.txtOrderValue);
        tvLocationOnMAp = findViewById(R.id.tvLocationOnMAp);
        txtMapAddress = findViewById(R.id.txtMapAddress);
        PaymentSwitch = findViewById(R.id.PaymentSwitch);
        save = findViewById(R.id.save);
        orderNotification = (OrderNotification) getIntent().getSerializableExtra("orderNotification");
        getOrderData(AddOrderActivity.this);
        spinnerCity.setOnClickListener(this);
        txtMapAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinnerCity:
                showSizeListPopupWindow(spinnerCity);
                break;
            case R.id.txtMapAddress:
                Intent intent = new Intent(AddOrderActivity.this, LocationMapsActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rbCash:
                if (isChecked) {
                    payment = 0;
                    rbEPay.setChecked(false);
                }
                break;
            case R.id.rbEPay:
                if (isChecked) {
                    payment = 1;
                    rbCash.setChecked(false);
                }
                break;
        }
    }


    public void getOrderData(final Context context) {

        new UserAPI().getOrderData(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseMakeOrder result1 = (ResponseMakeOrder) result;
                if (result1.isStatus()) {
                    placesList.clear();
                    placesList.addAll(result1.getMakeOrderData().getPlacesList());
                } else {
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

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    private void showSizeListPopupWindow(View anchorView) {
        final android.support.v7.widget.ListPopupWindow listPopupWindow = new android.support.v7.widget.ListPopupWindow(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            listPopupWindow.setDropDownGravity(Gravity.RIGHT);
        }
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setWidth(950);

        listPopupWindow.setAnchorView(anchorView);
        ListPopupWindowAdapter listPopupWindowAdapter = new ListPopupWindowAdapter(AddOrderActivity.this, placesList, new ListPopupWindowAdapter.OnClickListener() {
            @Override
            public void onClickButton(int position, places places) {
                listPopupWindow.dismiss();
                price_range.setHint(places.getPrice_from() + " - " + places.getPrice_to());
                placeID = places.getId();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);

        listPopupWindow.show();
    }

    private boolean isValidation() {
        if (!Constants.ValidationEmptyInput(txtStreet)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtSpecialMark)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtBuildingNo)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtFloorNo)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtFlatNo)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtClientName)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtMobileAddOrder)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (!Constants.ValidationEmptyInput(txtOrderValue)) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else if (LocationName_.equals("")) {
            Constants.showDialog(AddOrderActivity.this, getResources().getString(R.string.errorFeaild));
            return false;
        } else {
            MakeOrder makeOrder = null;
            try {
                makeOrder = new MakeOrder(orderNotification.getOrder_id() + "",
                        txtClientName.getText().toString(), txtMobileAddOrder.getText().toString(), tvLocationOnMAp.getText().toString(), txtStreet.getText().toString(),
                        txtBuildingNo.getText().toString(), txtFlatNo.getText().toString(), txtFloorNo.getText().toString(),
                        txtSpecialMark.getText().toString(), toLat, toLng, txtOrderValue.getText().toString(), price_range.getText().toString(), payment + "", checked + "", placeID + "");

            } catch (Exception e) {
                makeOrder = new MakeOrder(getIntent().getIntExtra("orderId", 0) + "",
                        txtClientName.getText().toString(), txtMobileAddOrder.getText().toString(), tvLocationOnMAp.getText().toString(), txtStreet.getText().toString(),
                        txtBuildingNo.getText().toString(), txtFlatNo.getText().toString(), txtFloorNo.getText().toString(),
                        txtSpecialMark.getText().toString(), toLat, toLng, txtOrderValue.getText().toString(), price_range.getText().toString(), payment + "", checked + "", placeID + "");

            }
            makeOrder(AddOrderActivity.this, makeOrder);
            return true;

        }


    }


    public void makeOrder(final Context context, MakeOrder makeOrder) {

        new UserAPI().makeOrder(makeOrder, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOrderId result1 = (ResponseOrderId) result;
                if (result1.isStatus()) {

                    Intent intent = new Intent(AddOrderActivity.this, TravelMapsActivity.class);
                    intent.putExtra("message", result1.getMessage());
                    intent.putExtra("orderNo", result1.getOrderId().getOrder_id());
                    intent.putExtra("Status_id", 3);
                    startActivity(intent);
                    finish();
                } else {
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

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String LocationName = data.getStringExtra("name");
                String lat = data.getStringExtra("lat");
                String lng = data.getStringExtra("lng");
                LocationName_ = LocationName;
                tvLocationOnMAp.setText(LocationName + "");
                toLat = lat;
                toLng = lng;
            }


            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}
