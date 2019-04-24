package com.ict.delivirko.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseTrackingOrderDetails;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.FirebaseUtils.MyFirebaseMessagingService;
import com.ict.delivirko.Interfaces.ObjectClickListener;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Interfaces.UniversalStringCallBack;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.restaurant.Driver;
import com.ict.delivirko.Module.restaurant.Order;
import com.ict.delivirko.Module.restaurant.TrackingOrderDetails;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.AddLineToMap;
import com.ict.delivirko.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TravelMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //Dialog Params
    private ImageView pilot_image, call;
    private RatingBar rateBarRest;
    private TextView motorcycle, telephone, PilotName, num, statusText;
    private RelativeLayout bottomLayId;
    private Button Done;
    private LinearLayout lay;
    private TrackingOrderDetails trackingOrderDetails;
    private AddLineToMap addLineToMap;
    private Order OrderTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_map_activity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        motorcycle = findViewById(R.id.motorcycle);
        telephone = findViewById(R.id.telephone);
        PilotName = findViewById(R.id.PilotName);
        pilot_image = findViewById(R.id.pilot_image);
        num = findViewById(R.id.num);
        call = findViewById(R.id.call);
        rateBarRest = findViewById(R.id.rateBarRest);
        bottomLayId = findViewById(R.id.bottom_pilot);
        statusText = findViewById(R.id.statusText);

        lay = findViewById(R.id.lay);
        Done = findViewById(R.id.Done);

        MyFirebaseMessagingService.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(OrderNotification orderNotification) {
                if (!orderNotification.getStatus_id().equals("0")) {
                    Intent intent = new Intent(TravelMapsActivity.this, TravelMapsActivity.class);
                    intent.putExtra("orderNo", Integer.valueOf(orderNotification.getOrder_id()));
                    intent.putExtra("Status_id", Integer.valueOf(orderNotification.getStatus_id()));

                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addLineToMap = new AddLineToMap(mMap, TravelMapsActivity.this);
        trackingOrderDetails(getIntent().getIntExtra("orderNo", 0), TravelMapsActivity.this);

    }


    public void TimeTrackingOrder(final String address, String link, final LatLng LatLangRes, final LatLng LatLangDriver, final Context context) {

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
                        final String duration = durationObject.getString("text");


                        TravelMapsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                    mMap.addMarker(new MarkerOptions().position(LatLangRes).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(address, "1")))));
                                    mMap.addMarker(new MarkerOptions().position(LatLangDriver).icon((BitmapDescriptorFactory.fromBitmap(addLineToMap.getMarkerBitmapFromView(duration, "2")))));

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


    private void showDialog(final Driver driver, final String status_text) {

        bottomLayId.setVisibility(View.VISIBLE);
        lay.setVisibility(View.GONE);
        int statusId = getIntent().getIntExtra("Status_id", 0);
        Log.e("statusIdstatusId", statusId + "");
        if (statusId == 4 || statusId == 6 || statusId == 7) {
            Done.setVisibility(View.VISIBLE);
        }
        statusText.setVisibility(View.VISIBLE);
        motorcycle.setText(driver.getCar_number() + "");
        PilotName.setText(driver.getName());
        try {
            rateBarRest.setRating(Float.parseFloat(Float.valueOf(driver.getDriver_rating()) + ""));

        } catch (Exception e) {
            rateBarRest.setRating(0);

        }
        telephone.setText(driver.getPhone() + "");
        num.setText(driver.getDriver_rating_count() + "");
        statusText.setText(status_text);
        Picasso.with(this)
                .load(Constants.Image_URL + driver.getImage()).fit()
                .into(pilot_image);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(TravelMapsActivity.this, "" + driver.getPhone());
                } else {
                    if (ActivityCompat.checkSelfPermission(TravelMapsActivity.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(TravelMapsActivity.this, "" + driver.getPhone());
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(TravelMapsActivity.this, PERMISSIONS_STORAGE, 9);
                    }
                }
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationController.getInstance().deleteTempOrder();
                ApplicationController.getInstance().deleteOrderNotification();
                startActivity(new Intent(TravelMapsActivity.this, HomeRestaurantActivity.class));
                finish();
            }
        });
    }


    private void phoneCall(Context context, String telNum) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telNum));
            context.startActivity(callIntent);
        } else {
            Constants.showDialog((Activity) context, TravelMapsActivity.this.getResources().getString(R.string.permission));
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
                        showDialog(trackingOrderDetails.getDriver(), trackingOrderDetails.getOrder().getStatus_text());


                        LatLng CompanyLatLang = new LatLng(result1.getTrackingOrderDetails().getCompany().getLat(), result1.getTrackingOrderDetails().getCompany().getLng());
                        LatLng DriverLatLang = new LatLng(result1.getTrackingOrderDetails().getDriver().getLat(), result1.getTrackingOrderDetails().getDriver().getLng());
                        LatLng OrderLatLang = new LatLng(result1.getTrackingOrderDetails().getOrder().getD_lat(), result1.getTrackingOrderDetails().getOrder().getD_lng());


                        Log.e("Status_id", getIntent().getIntExtra("Status_id", 0) + "");

                        if (getIntent().getIntExtra("Status_id", 0) == 3) {


                            TimeTrackingOrder(result1.getTrackingOrderDetails().getCompany().getAddress(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                    + String.valueOf(result1.getTrackingOrderDetails().getCompany().getLat()) + "," + String.valueOf(result1.getTrackingOrderDetails().getCompany().getLng()) + "&" + Constants.DESTINATION + "="
                                    + String.valueOf(result1.getTrackingOrderDetails().getDriver().getLat()) + "," + String.valueOf(result1.getTrackingOrderDetails().getDriver().getLng()) + "&" + Constants.MODE + "="
                                    + "driving" + "&" + Constants.LANGUAGE + "="
                                    + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                    + String.valueOf(false), CompanyLatLang, DriverLatLang, TravelMapsActivity.this);
                            //Draw Line Between Two Marker
                            addLineToMap.addMarker(CompanyLatLang);
                            addLineToMap.addMarker(DriverLatLang);
                            //Zoom Between Two marker
                            addLineToMap.ZoomBetween2Marker(CompanyLatLang, DriverLatLang);


                        } else {
                            OrderTemp = result1.getTrackingOrderDetails().getOrder();
                            //the order on the way and driver connect order between order place and Restaurant
                            TimeTrackingOrder(result1.getTrackingOrderDetails().getOrder().getAddress(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                                    + String.valueOf(result1.getTrackingOrderDetails().getOrder().getD_lat()) + "," + String.valueOf(result1.getTrackingOrderDetails().getOrder().getD_lng()) + "&" + Constants.DESTINATION + "="
                                    + String.valueOf(result1.getTrackingOrderDetails().getDriver().getLat()) + "," + String.valueOf(result1.getTrackingOrderDetails().getDriver().getLng()) + "&" + Constants.MODE + "="
                                    + "driving" + "&" + Constants.LANGUAGE + "="
                                    + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                                    + String.valueOf(false), OrderLatLang, DriverLatLang, TravelMapsActivity.this);
                            //Draw Line Between Two Marker
                            addLineToMap.addMarker(OrderLatLang);
                            addLineToMap.addMarker(DriverLatLang);
                            //Zoom Between Two marker
                            addLineToMap.ZoomBetween2Marker(OrderLatLang, DriverLatLang);
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

                TimeTrackingOrder(OrderTemp.getAddress(), Constants.GOOGLE_MATRIX_URL + Constants.ORIGINS + "="
                        + String.valueOf(OrderTemp.getD_lat()) + "," + String.valueOf(OrderTemp.getD_lng()) + "&" + Constants.DESTINATION + "="
                        + String.valueOf(latDriver) + "," + String.valueOf(lngDriver) + "&" + Constants.MODE + "="
                        + "driving" + "&" + Constants.LANGUAGE + "="
                        + "en-EN" + "&" + "key=" + Constants.GOOGLE_API_KEY + "&" + Constants.SENSOR + "="
                        + String.valueOf(false), new LatLng(OrderTemp.getD_lat(), OrderTemp.getD_lng()), new LatLng(latDriver, lngDriver), TravelMapsActivity.this);


                addLineToMap.addMarker(new LatLng(OrderTemp.getD_lat(), OrderTemp.getD_lng()));
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(TravelMapsActivity.this,HomeRestaurantActivity.class);
        startActivity(intent);
    }
}
