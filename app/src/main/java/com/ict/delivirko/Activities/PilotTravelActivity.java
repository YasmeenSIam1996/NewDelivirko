package com.ict.delivirko.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObject;
import com.ict.delivirko.API.ResponseOnTheWay;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Interfaces.UniversalStringCallBack;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.pilot.Status;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.AddLineToMap;
import com.ict.delivirko.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PilotTravelActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private ImageButton currentLocationImageButton;


    //    private LocationCallback mLocationCallback;
    private GoogleMap mMap;
    private TextView text_timer, LocationFrom, LocationTo;
    private Button reject, agree;
    private OrderNotification orderNotification;
    private boolean isStart = false, isEnd = false;
    private RelativeLayout company_bottom_sheet_layout, LocationLay;
    //Bottom Lay Views
    private Button ReturnOrder, EndTravel;
    private ImageView call, company_image;
    private TextView ClintName, Company_name, telephone, fromLocation, toLocation, Price;
    private String CompanyTel = "", OrderTel = "";
    private AddLineToMap addLineToMap;
    private Status statusOrder;
    private int second = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilot_travel);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        intiViews();
        intiActions();

        currentLocationImageButton = findViewById(R.id.currentLocationImageButton);
        currentLocationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateCamera(currentLocation);
            }
        });


    }


    private void intiActions() {
        String time;

        if (orderNotification != null) {
            time = orderNotification.getWaiting_time();
        } else {
            time = statusOrder.getOrder().getWaiting_time();
        }

//        new CountDownTimer(Integer.valueOf(time) * 1000, 1) {
//
//            public void onTick(long millisUntilFinished) {
//                text_timer.setText("" + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                text_timer.setText("finish!");
////                PilotTravelActivity.this.startActivity(new Intent(PilotTravelActivity.this, HomePilotActivity.class));
//                this.cancel();
////                finish();
//            }
//
//        }.start();


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    if (!orderNotification.getOrder_id().trim().equals("")) {
                        CancelTravel(orderNotification.getOrder_id(), PilotTravelActivity.this);
                    } else {
                        CancelTravel(statusOrder.getOrder().getId() + "", PilotTravelActivity.this);
                    }

                } else {
                    if (!orderNotification.getOrder_id().trim().equals("")) {
                        RejectOrder(orderNotification.getOrder_id(), PilotTravelActivity.this);
                    } else {
                        RejectOrder(statusOrder.getOrder().getId() + "", PilotTravelActivity.this);
                    }
                }
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {

                    if (!orderNotification.getOrder_id().trim().equals("")) {
                        StartTravel(orderNotification.getOrder_id(), PilotTravelActivity.this);
                    } else {
                        StartTravel(statusOrder.getOrder().getId() + "", PilotTravelActivity.this);

                    }

                } else {
                    if (!orderNotification.getOrder_id().trim().equals("")) {
                        AcceptOrder(orderNotification.getOrder_id(), PilotTravelActivity.this);
                    } else {
                        AcceptOrder(statusOrder.getOrder().getId() + "", PilotTravelActivity.this);

                    }

                }
            }
        });
        ReturnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReturnDialog(PilotTravelActivity.this);
            }
        });

        EndTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDialog(PilotTravelActivity.this);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(PilotTravelActivity.this, "" + CompanyTel);
                } else {
                    if (ActivityCompat.checkSelfPermission(PilotTravelActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(PilotTravelActivity.this, "" + CompanyTel);
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(PilotTravelActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }
            }
        });

        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(PilotTravelActivity.this, "" + OrderTel);
                } else {
                    if (ActivityCompat.checkSelfPermission(PilotTravelActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(PilotTravelActivity.this, "" + OrderTel);
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(PilotTravelActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }
            }
        });
    }

    private void intiViews() {
        statusOrder = (Status) getIntent().getSerializableExtra("OrderStatus");
        orderNotification = ApplicationController.getInstance().getOrderNotification();

        text_timer = findViewById(R.id.text_timer);
        LocationFrom = findViewById(R.id.LocationFrom);
        LocationTo = findViewById(R.id.LocationTo);
        reject = findViewById(R.id.reject);
        agree = findViewById(R.id.agree);
        company_bottom_sheet_layout = findViewById(R.id.company_bottom_sheet_layout);
        LocationLay = findViewById(R.id.place_back_map_layout);
        ReturnOrder = findViewById(R.id.ReturnOrder);
        EndTravel = findViewById(R.id.EndTravel);
        call = findViewById(R.id.call);
        company_image = findViewById(R.id.company_image);
        ClintName = findViewById(R.id.ClintName);
        Company_name = findViewById(R.id.Company_name);
        telephone = findViewById(R.id.telephone);
        fromLocation = findViewById(R.id.fromLocation);
        toLocation = findViewById(R.id.toLocation);
        Price = findViewById(R.id.TxtPrice);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        isStart = getIntent().getBooleanExtra("isStart", false);
        isEnd = getIntent().getBooleanExtra("isEnd", false);

        if (isStart) {
            text_timer.setVisibility(View.GONE);
            reject.setText(getResources().getString(R.string.CancelTravel));
            agree.setText(getResources().getString(R.string.StartTravel));
        }
        if (isEnd) {
            company_bottom_sheet_layout.setVisibility(View.VISIBLE);
            text_timer.setVisibility(View.GONE);
            LocationLay.setVisibility(View.GONE);
            text_timer.setVisibility(View.GONE);


        }


    }


    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.e("_second_", second + "");
            if (second == 0 || second == 7) {
                if (locationResult.getLastLocation() == null)
                    return;
                currentLocation = locationResult.getLastLocation();
                changeLocationFirebase(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()));

                if (firstTimeFlag && mMap != null) {
                    animateCamera(currentLocation);
                    firstTimeFlag = false;
                }
                showMarker(currentLocation);

            }
            second++;

            if (second == 7) {
                second = 0;
            }

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        addLineToMap = new AddLineToMap(mMap, PilotTravelActivity.this);

    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PilotTravelActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        try {
            if (currentLocationMarker == null) {
                if (isEnd) {
                if (!orderNotification.getOrder_id().trim().equals("")) {
                    OrderOnTheWay(orderNotification.getOrder_id(), PilotTravelActivity.this, currentLocation);
                } else {
                    OrderOnTheWay(statusOrder.getOrder().getId() + "", PilotTravelActivity.this, currentLocation);
                }
                } else {
                    drawStatus2(currentLocation, true);

                }
            }
//            else {
//                if (isEnd) {
//                if (!orderNotification.getOrder_id().trim().equals("")) {
//                    OrderOnTheWay(orderNotification.getOrder_id(), PilotTravelActivity.this, currentLocation);
//                } else {
//                    OrderOnTheWay(statusOrder.getOrder().getId() + "", PilotTravelActivity.this, currentLocation);
//                }
//                } else {
//                    drawStatus2(currentLocation, false);
//
//                }
//            }
        }catch (Exception e){

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        mMap = null;
    }

    public void AcceptOrder(final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().AcceptOrder(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {

                    Intent intent = new Intent(PilotTravelActivity.this, PilotTravelActivity.class);
                    intent.putExtra("Message", result1.getMessage());
                    intent.putExtra("isStart", true);

                    startActivity(intent);
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
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    public void RejectOrder(final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().RejectOrder(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    Intent intent = new Intent(PilotTravelActivity.this, HomePilotActivity.class);
                    intent.putExtra("Message", result1.getMessage());
                    startActivity(intent);

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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    public void StartTravel(final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().StartTravel(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    Intent intent = new Intent(PilotTravelActivity.this, PilotTravelActivity.class);
                    intent.putExtra("Message", result1.getMessage());
                    intent.putExtra("isEnd", true);

                    Constants.showDialog((Activity) context, result1.getMessage());
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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    public void CancelTravel(final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().CancelTravel(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    Intent intent = new Intent(PilotTravelActivity.this, HomePilotActivity.class);
                    intent.putExtra("Message", result1.getMessage());
                    Constants.showDialog((Activity) context, result1.getMessage());
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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    public void FinishOrder(final Dialog dialog, final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().FinishOrder(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    dialog.dismiss();
                    Intent intent = new Intent(PilotTravelActivity.this, HomePilotActivity.class);
                    intent.putExtra("Message", result1.getMessage());
                    startActivity(intent);
                    finish();
//                    showOrderReciveDialog(PilotTravelActivity.this);

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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    public void ReturnOrder(final Dialog dialog, final String order_id, final Context context) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().ReturnOrder(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    dialog.dismiss();
                    Intent intent = new Intent(PilotTravelActivity.this, HomePilotActivity.class);
                    intent.putExtra("Message", result1.getMessage());
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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

    private void phoneCall(Context context, String telNum) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telNum));
            context.startActivity(callIntent);
        } else {
            Constants.showDialog((Activity) context, getResources().getString(R.string.permission));
        }
    }


    public void showReturnDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_question);
        Button btnDialogReject = dialog.findViewById(R.id.btnDialogReject);
        Button btnDialogAgree = dialog.findViewById(R.id.btnDialogAgree);
        TextView tvCloseQuestionDialog = dialog.findViewById(R.id.tvCloseQuestionDialog);
        btnDialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!orderNotification.getOrder_id().trim().equals("")) {
                    ReturnOrder(dialog, orderNotification.getOrder_id(), PilotTravelActivity.this);
                } else {
                    ReturnOrder(dialog, statusOrder.getOrder().getId() + "", PilotTravelActivity.this);

                }

            }
        });
        dialog.show();

        tvCloseQuestionDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }

    public void showEndDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_question_end);
        Button btnDialogReject = dialog.findViewById(R.id.btnDialogReject);
        Button btnDialogAgree = dialog.findViewById(R.id.btnDialogAgree);
        TextView tvCloseQuestionDialog = dialog.findViewById(R.id.tvCloseQuestionDialog);
        btnDialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!orderNotification.getOrder_id().trim().equals("")) {
                    FinishOrder(dialog, orderNotification.getOrder_id(), PilotTravelActivity.this);
                } else {
                    FinishOrder(dialog, statusOrder.getOrder().getId() + "", PilotTravelActivity.this);

                }
            }
        });
        dialog.show();

        tvCloseQuestionDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }

    private void drawStatus2(Location location, boolean isFirst) {
        Double longitude, latitude;
        LatLng CompanyLatLang, DriverLatLang;
        String address;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        DriverLatLang = new LatLng(latitude,longitude);
        if (statusOrder == null) {
            Log.e("statusOrder_", orderNotification.getCompany_name());
            CompanyLatLang = new LatLng(Double.valueOf(orderNotification.getCompany_lng()), Double.valueOf(orderNotification.getCompany_lat()));
            address = orderNotification.getCompany_address();
            LocationFrom.setText(orderNotification.getCompany_name());
            LocationTo.setText(orderNotification.getCompany_address());
            text_timer.setText(orderNotification.getWaiting_time());
        } else {
            Log.e("statusOrder_2", statusOrder.getCompany().toString());
            CompanyLatLang = new LatLng(statusOrder.getCompany().getLat(), statusOrder.getCompany().getLng());
            address = statusOrder.getCompany().getAddress();
            LocationFrom.setText(statusOrder.getCompany().getName());
            LocationTo.setText(statusOrder.getCompany().getAddress());
            text_timer.setText(statusOrder.getOrder().getWaiting_time());
        }


        TimeTrackingOrder(address, Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                + String.valueOf(CompanyLatLang.latitude) + "," + String.valueOf(CompanyLatLang.longitude) + "&" + Constants.DESTINATION + "="
                + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&" + Constants.MODE + "="
                + "driving" + "&" + Constants.LANGUAGE + "="
                + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                + String.valueOf(false), CompanyLatLang, DriverLatLang, PilotTravelActivity.this);

        addLineToMap.addMarker(CompanyLatLang);
        addLineToMap.addMarker(DriverLatLang);
        if (isFirst) {
            //Zoom Between Two marker
            addLineToMap.ZoomBetween2Marker(CompanyLatLang, DriverLatLang);
        }
//        else {
//            MarkerAnimation.animateMarkerToGB(currentLocationMarker, DriverLatLang, new LatLngInterpolator.Spherical());

//        }
    }

    public void TimeTrackingOrder(final String address, String link, final LatLng LatLangRes, final LatLng LatLangDriver, final Context context) {
        Log.e("statusOrder_", link);
        new UserAPI().TimeOrderTracking(link, new UniversalStringCallBack() {
            @Override
            public void onResponse(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("OK")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        JSONObject elementsObject = jsonArray.getJSONObject(0);
                        JSONArray elementsArray = elementsObject.getJSONArray("elements");
                        JSONObject distanceObject = elementsArray.getJSONObject(0);
                        JSONObject durationObject = distanceObject.getJSONObject("duration");
                        final String duration = durationObject.getString("text");

                        PilotTravelActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));
                                currentLocationMarker = mMap.addMarker(new MarkerOptions().position(LatLangDriver).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(duration, "2")))));

                            }
                        });

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


    public void OrderOnTheWay(final String order_id, final Context context, final Location location) {
        Constants.showSimpleProgressDialog(PilotTravelActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().OrderOnTheWay(order_id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOnTheWay result1 = (ResponseOnTheWay) result;
                if (result1.isStatus()) {

                    ClintName.setText(result1.getOnTheWay().getOrder().getName());
                    telephone.setText(result1.getOnTheWay().getOrder().getPhone());
                    fromLocation.setText(result1.getOnTheWay().getCompany().getName());
                    toLocation.setText(result1.getOnTheWay().getOrder().getPlace());
                    Price.setText(result1.getOnTheWay().getOrder().getPrice() + "");
                    Picasso.with(getApplicationContext()).load(Constants.Image_URL + result1.getOnTheWay().getCompany().getImage()).fit()
                            .into(company_image);
                    CompanyTel = result1.getOnTheWay().getCompany().getPhone();
                    OrderTel = result1.getOnTheWay().getOrder().getPhone();
                    Company_name.setText(result1.getOnTheWay().getCompany().getName());
//                    LatLng CompanyLatLang = new LatLng(result1.getOnTheWay().getCompany().getLat(), result1.getOnTheWay().getCompany().getLng());

                    LatLng DriverLatLang = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng OrderLatLang = new LatLng(result1.getOnTheWay().getOrder().getLat(), result1.getOnTheWay().getOrder().getLng());

                    addLineToMap.addMarker(OrderLatLang);
                    addLineToMap.addMarker(DriverLatLang);
                    TimeTrackingOrder(result1.getOnTheWay().getOrder().getPlace(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                            + String.valueOf(OrderLatLang.latitude) + "," + String.valueOf(OrderLatLang.longitude) + "&" + Constants.DESTINATION + "="
                            + String.valueOf(DriverLatLang.latitude) + "," + String.valueOf(DriverLatLang.longitude) + "&" + Constants.MODE + "="
                            + "driving" + "&" + Constants.LANGUAGE + "="
                            + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                            + String.valueOf(false), OrderLatLang, DriverLatLang, PilotTravelActivity.this);


                    //Zoom Between Two marker
//                    addLineToMap.ZoomBetween2Marker(OrderLatLang, DriverLatLang);


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
                Constants.removeProgressDialog();

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }


    private void changeLocationFirebase(LatLng latLng){
        int Id = ApplicationController.getInstance().getUser().getId();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("drivers/" + Id + "/lat");
        DatabaseReference myRef2 = database.getReference("drivers/" + Id + "/lng");
        if (latLng.latitude != 0 || latLng.longitude != 0) {
            myRef2.setValue(latLng.longitude);
            myRef.setValue(latLng.latitude);
        }
    }

}
