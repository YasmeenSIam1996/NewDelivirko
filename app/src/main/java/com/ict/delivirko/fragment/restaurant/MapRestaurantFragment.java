package com.ict.delivirko.fragment.restaurant;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseNearestCompany;
import com.ict.delivirko.API.ResponseNearestDriver;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.ResponseOrderTemp;
import com.ict.delivirko.API.ResponseTrackingOrderDetails;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Activities.AddOrderActivity;
import com.ict.delivirko.Activities.HomeRestaurantActivity;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.FirebaseUtils.MyFirebaseMessagingService;
import com.ict.delivirko.Interfaces.ObjectClickListener;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Interfaces.UniversalStringCallBack;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.restaurant.Company;
import com.ict.delivirko.Module.restaurant.Driver;
import com.ict.delivirko.Module.restaurant.OrderTemp;
import com.ict.delivirko.Module.restaurant.TrackingOrderDetails;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.AddLineToMap;
import com.ict.delivirko.Utils.AlarmReceive;
import com.ict.delivirko.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapRestaurantFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener {

    private boolean IS_ONCE = true;
    private AddLineToMap addLineToMap;
    private GoogleMap mMap;
    private Button PilotOrder;
    private boolean isNetDialogShowing = false;
    private AlertDialog internetDialog;
    private Dialog LoadingPilotsDialog, RejectPilotsDialog;
    //Dialog item
    private Dialog dialog;
    private TrackingOrderDetails trackingOrderDetails;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Marker driverMarker;
    private String duration = "";
    private Company companyTemp;

    public MapRestaurantFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_restaurant, container, false);
        LoadingPilotsDialog = new Dialog(getActivity());
        RejectPilotsDialog = new Dialog(getActivity());
        //get From Follow Adapter

        try {
            if (getActivity().getIntent().getIntExtra("orderNo", -1) == -1) {
                companyData();

            } else if (getActivity().getIntent().getIntExtra("Status_id", -1) == 0) {
                companyData();
                showRejectPilotsDialog(getActivity().getIntent().getStringExtra("Order_status_text"));

            } else if (getActivity().getIntent().getIntExtra("Status_id", -1) == 1) {
                Log.e("orderNo_", getActivity().getIntent().getIntExtra("orderNo", -1) + "");
                Log.e("orderNo_2", getActivity().getIntent().getIntExtra("orderNo", -1) + "");


                if (getActivity().getIntent().getIntExtra("orderNo", -1) == -1) {
                    trackingOrderDetails(Integer.valueOf(ApplicationController.getInstance().getOrderNotification().getOrder_id()), getActivity());

                } else {

                    trackingOrderDetails(getActivity().getIntent().getIntExtra("orderNo", -1), getActivity());

                }

            }

        } catch (Exception e) {

        }


        MyFirebaseMessagingService.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(OrderNotification orderNotification) {
                if (orderNotification.getStatus_id().equals("1") || orderNotification.getStatus_id().equals("0")) {
                    Log.e("orderNo_111", getActivity().getIntent().getIntExtra("orderNo", 0) + "");

                    Intent intent = new Intent(getActivity(), HomeRestaurantActivity.class);
                    intent.putExtra("Status_id", Integer.valueOf(orderNotification.getStatus_id()));
                    intent.putExtra("Order_status_text", orderNotification.getOrder_status_text());

                    startActivity(intent);
                    getActivity().finish();
                    LoadingPilotsDialog.dismiss();
                }
            }
        });
        SupportMapFragment myMAPF = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapRestPilot);
        myMAPF.getMapAsync(this);
        PilotOrder = view.findViewById(R.id.PilotOrder);
        PilotOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCompanyOrder(getActivity());
            }
        });


        return view;
    }

    public void companyData() {

        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));
        new UserAPI().companyData(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseNearestCompany responseNearestDriver = (ResponseNearestCompany) result;
                if (responseNearestDriver != null) {
                    if (responseNearestDriver.isStatus()) {


                        moveMap(responseNearestDriver.getDataNearestDriver().getNearestDriver().getLat()
                                , responseNearestDriver.getDataNearestDriver().getNearestDriver().getLng()
                                , 1, responseNearestDriver.getDataNearestDriver().getNearestDriver().getName(), false);


                        moveMap(responseNearestDriver.getDataNearestDriver().getLat()
                                , responseNearestDriver.getDataNearestDriver().getLng()
                                , 2, responseNearestDriver.getDataNearestDriver().getAddress(), true);


//                        addLineToMap.ZoomBetween2Marker(new LatLng(responseNearestDriver.getDataNearestDriver().getLat()
//                                , responseNearestDriver.getDataNearestDriver().getLng()), new LatLng(responseNearestDriver.getDataNearestDriver().getNearestDriver().getLat()
//                                , responseNearestDriver.getDataNearestDriver().getNearestDriver().getLng()));

                    } else {
                        Constants.showDialog(getActivity(), responseNearestDriver.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseNearestDriver responseError = (ResponseNearestDriver) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
                getActivity().getIntent().removeExtra("orderNo");
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        addLineToMap = new AddLineToMap(mMap, getActivity());
        getLocationPermission();

        //Location Driver
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlarmReceive.class);
            pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000,
                    pendingIntent);
        }


//        BookingTrackingService.setOnItemClickListener(new LocationClickListener() {
//            @Override
//            public void onItemClickListener(double lat, double lng) {
//                try {
//                    moveMap(lat, lng, 1, "Driver Location", false);
//                } catch (Exception e) {
//                    //nothing
//                }
//            }
//        });


    }


    private void moveMap(final double latitude, final double longitude, int num, String Name, boolean zoom) {

        LatLng latLng = new LatLng(latitude, longitude);
        if (num == 1) {
            mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(Name).icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)));
        } else if (num == 3) {
            mMap.addMarker(new MarkerOptions().position(latLng).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(duration, "2")))));
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(Name).icon(BitmapDescriptorFactory.fromResource(R.drawable.rest_icon)));
        }
        if (zoom) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mMap.getUiSettings().setZoomControlsEnabled(false);
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }


    public void createCompanyOrder(final Context context) {

        new UserAPI().createCompanyOrder(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOrderTemp result1 = (ResponseOrderTemp) result;
                if (result1.isStatus()) {
                    OrderTemp orderTemp = result1.getOrderTemp();
                    ApplicationController.getInstance().SetTempOrder(orderTemp);

                    showLoadingPilotsDialog();
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


    public void showLoadingPilotsDialog() {
        LoadingPilotsDialog.setContentView(R.layout.loading_pilots_dialog);
        LoadingPilotsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LoadingPilotsDialog.setCancelable(false);
        LoadingPilotsDialog.show();
        Button CancelOrder = LoadingPilotsDialog.findViewById(R.id.CancelOrder);
        CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(ApplicationController.getInstance().getOrderTemp().getId() + "", getActivity(), false);
            }
        });
    }


    public void cancelOrder(String OrderId, final Context context, final boolean isTrue) {

        new UserAPI().cancelOrder(OrderId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    if (LoadingPilotsDialog != null) {
                        LoadingPilotsDialog.dismiss();
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Constants.showDialog((Activity) context, result1.getMessage());
                    if (isTrue) {
                        startActivity(new Intent(getActivity(), HomeRestaurantActivity.class));
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(context, result1.getMessage(), Toast.LENGTH_LONG).show();
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


    public void showRejectPilotsDialog(String msg) {
        RejectPilotsDialog.setContentView(R.layout.reject_pilots_dialog);
        RejectPilotsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RejectPilotsDialog.setCancelable(false);
        TextView textView = RejectPilotsDialog.findViewById(R.id.textDialog);
        textView.setText(msg);
        Button Done = RejectPilotsDialog.findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectPilotsDialog.dismiss();
                ApplicationController.getInstance().deleteTempOrder();
                ApplicationController.getInstance().deleteOrderNotification();

            }
        });
        RejectPilotsDialog.show();

    }

    public BroadcastReceiver internetConnectionReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                // connected to the internet
                switch (activeNetwork.getType()) {
                    case 1:
                        removeInternetDialog();
                        break;
                    case 0:
                        removeInternetDialog();
                        break;
                    default:
                        break;
                }
            } else {
                if (isNetDialogShowing) {
                    return;
                }
                showInternetDialog();
            }
//            ConnectivityManager connectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetInfo = connectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            NetworkInfo activeWIFIInfo = connectivityManager
//                    .getNetworkInfo(connectivityManager.TYPE_WIFI);
//
//            if (activeWIFIInfo.isConnected() || activeNetInfo.isConnected()) {
//                removeInternetDialog();
//            } else {
//                if (isNetDialogShowing) {
//                    return;
//                }
//                showInternetDialog();
//            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        ContextWrapper contextWrapper = new ContextWrapper(getActivity());
        contextWrapper.registerReceiver(internetConnectionReciever, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

    }


    private void removeInternetDialog() {
        if (internetDialog != null && internetDialog.isShowing()) {
            internetDialog.dismiss();
            isNetDialogShowing = false;
            internetDialog = null;

        }
    }

    private void showInternetDialog() {

        isNetDialogShowing = true;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(
                getActivity());
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle(getString(R.string.dialog_no_internet))
                .setMessage(getString(R.string.dialog_no_inter_message))
                .setPositiveButton(getString(R.string.dialog_enable_3g),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        Settings.ACTION_SETTINGS);
                                startActivity(intent);
                                removeInternetDialog();
                            }
                        })
                .setNeutralButton(getString(R.string.dialog_enable_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                startActivity(new Intent(
                                        Settings.ACTION_WIFI_SETTINGS));
                                removeInternetDialog();
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeInternetDialog();
                                getActivity().finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }


    private void showDialog(final Driver driver, final int orderId) {

        ImageView pilot_image;
        RatingBar rateBarRest;
        TextView motorcycle, telephone, PilotName, num;
        Button CancelOrder, AddOrder;
        //---------------------------------

        final BottomSheetDialog sheetDailyReport = new BottomSheetDialog(getActivity(), R.style.SheetDialog);
        sheetDailyReport.setContentView(R.layout.bottom_pilot);
        sheetDailyReport.setCancelable(true);
        sheetDailyReport.show();
        AddOrder = sheetDailyReport.findViewById(R.id.AddOrder);
        CancelOrder = sheetDailyReport.findViewById(R.id.CancelOrder);
        motorcycle = sheetDailyReport.findViewById(R.id.motorcycle);
        telephone = sheetDailyReport.findViewById(R.id.telephone);
        PilotName = sheetDailyReport.findViewById(R.id.PilotName);
        pilot_image = sheetDailyReport.findViewById(R.id.pilot_image);
        num = sheetDailyReport.findViewById(R.id.num);
        ImageView call = sheetDailyReport.findViewById(R.id.call);

        rateBarRest = sheetDailyReport.findViewById(R.id.rateBarRest);
        motorcycle.setText(driver.getCar_number() + "");
        PilotName.setText(driver.getName());
        rateBarRest.setRating(Float.parseFloat(Float.valueOf(driver.getDriver_rating()) + ""));
        telephone.setText(driver.getPhone() + "");
        num.setText(driver.getDriver_rating_count() + "");

        Picasso.with(getActivity())
                .load(Constants.Image_URL + driver.getImage()).fit()
                .into(pilot_image);

        CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog(getActivity(), orderId);
            }
        });

        AddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddOrderActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(getActivity(), "" + driver.getPhone());
                } else {
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(getActivity(), "" + driver.getPhone());
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 9);
                    }
                }
            }
        });
    }


    public void showCancelDialog(final Context context, final int orderId) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_question);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tvCloseQuestionDialog = dialog.findViewById(R.id.tvCloseQuestionDialog);
        Button btnDialogReject = dialog.findViewById(R.id.btnDialogReject);
        Button btnDialogAgree = dialog.findViewById(R.id.btnDialogAgree);
        dialog.setCancelable(false);
        dialog.show();
        tvCloseQuestionDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelOrder(orderId + "", getActivity(), true);
            }
        });

    }

    private void phoneCall(Context context, String telNum) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telNum));
            context.startActivity(callIntent);
        } else {
            Constants.showDialog((Activity) context, getActivity().getResources().getString(R.string.permission));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall(getActivity(), "" + ApplicationController.getInstance().getOrderNotification().getDriver_phone());
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.permission), Toast.LENGTH_SHORT).show();
        }
    }


    public void trackingOrderDetails(int orderId, final Context context) {

        new UserAPI().trackingOrderDetails(orderId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseTrackingOrderDetails result1 = (ResponseTrackingOrderDetails) result;
                if (result1.isStatus()) {
                    try {
                        trackingOrderDetails = result1.getTrackingOrderDetails();

                        int Status_id = getActivity().getIntent().getIntExtra("Status_id", 1);
                        if (Status_id == 0) {
                            showRejectPilotsDialog(result1.getTrackingOrderDetails().getOrder().getStatus_text());
                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();

                        } else if (Status_id == 1) {
                            showDialog(result1.getTrackingOrderDetails().getDriver(), result1.getTrackingOrderDetails().getOrder().getId());
                            LatLng driver_lat_lng = new LatLng(result1.getTrackingOrderDetails().getDriver().getLat(), result1.getTrackingOrderDetails().getDriver().getLng());
                            LatLng company_lat_lng = new LatLng(result1.getTrackingOrderDetails().getCompany().getLat(), result1.getTrackingOrderDetails().getCompany().getLng());

                            addLineToMap.addMarker(company_lat_lng);
                            addLineToMap.addMarker(driver_lat_lng);

                            TimeTrackingOrder(result1.getTrackingOrderDetails().getCompany().getAddress(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                    + String.valueOf(company_lat_lng.latitude) + "," + String.valueOf(company_lat_lng.longitude) + "&" + Constants.DESTINATION + "="
                                    + String.valueOf(driver_lat_lng.latitude) + "," + String.valueOf(driver_lat_lng.longitude) + "&" + Constants.MODE + "="
                                    + "driving" + "&" + Constants.LANGUAGE + "="
                                    + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                    + String.valueOf(false), company_lat_lng, driver_lat_lng, getActivity(), true);

                            //Zoom Between Two marker
                            addLineToMap.ZoomBetween2Marker(company_lat_lng, driver_lat_lng);
                            companyTemp = result1.getTrackingOrderDetails().getCompany();
                            MovementDriverMarker(result1.getTrackingOrderDetails().getDriver().getId());

                        }

                    } catch (Exception e) {

                    }
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
                getActivity().getIntent().removeExtra("orderNo");


            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    private void MovementDriverMarker(final int id) {
        // draw driver movement  in map
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("drivers/" + id + "/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Double latDriver = dataSnapshot.child("lat").getValue(double.class);
                Double lngDriver = dataSnapshot.child("lng").getValue(double.class);

                TimeTrackingOrder(companyTemp.getAddress(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                        + String.valueOf(companyTemp.getLat()) + "," + String.valueOf(companyTemp.getLng()) + "&" + Constants.DESTINATION + "="
                        + String.valueOf(latDriver) + "," + String.valueOf(lngDriver) + "&" + Constants.MODE + "="
                        + "driving" + "&" + Constants.LANGUAGE + "="
                        + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                        + String.valueOf(false), new LatLng(companyTemp.getLat(), companyTemp.getLng()), new LatLng(latDriver, lngDriver), getActivity(), false);


                addLineToMap.addMarker(new LatLng(companyTemp.getLat(), companyTemp.getLng()));
                addLineToMap.addMarker(new LatLng(latDriver, lngDriver));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed_", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            showDialog(trackingOrderDetails.getDriver(), trackingOrderDetails.getOrder().getId());

        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().getIntent().removeExtra("orderNo");

    }

    public void TimeTrackingOrder(final String address, String link, final LatLng LatLangRes, final LatLng LatLangDriver, final Context context, final boolean drawMarker) {

        new UserAPI().TimeOrderTracking(link, new UniversalStringCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("result1_result1", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("OK")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        JSONObject elementsObject = jsonArray.getJSONObject(0);
                        JSONArray elementsArray = elementsObject.getJSONArray("elements");
                        JSONObject distanceObject = elementsArray.getJSONObject(0);
                        JSONObject durationObject = distanceObject.getJSONObject("duration");
                        duration = durationObject.getString("text");

                        if (drawMarker) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                public void run() {

                            mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));

                            driverMarker = mMap.addMarker(new MarkerOptions().position(LatLangDriver).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(duration, "2")))));
//
//
//                                }
//                            });
                        } else {

                            if (IS_ONCE) {
                                addLineToMap.deleteDriverMarker();
                                if (driverMarker != null) {
                                    driverMarker.remove();
                                }
                                IS_ONCE = false;
                            }
                            mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));

                            moveMap(LatLangDriver.latitude, LatLangDriver.longitude, 3, "Driver", false);

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String result) {
                if (result != null) {
                    Constants.showDialog((Activity) context, result);
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

}
