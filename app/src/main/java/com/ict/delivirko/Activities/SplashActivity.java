package com.ict.delivirko.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseObjectStatus;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

public class SplashActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    public double LAT, LNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ApplicationController.getInstance().IsDriverUserLoggedIn()) {
                    if (ApplicationController.getInstance().getUser().isDriver()) {
                        CheckStatus(SplashActivity.this);
                    } else {
                        startActivity(new Intent(getApplicationContext(), HomeRestaurantActivity.class));
                        finish();
                    }
                } else {
                    checkAndSetCurrentLocation(new Intent(getApplicationContext(), SelectServiceActivity.class));
                }
            }
        }, 2000);
    }


    public void CheckStatus(final Context context) {
        Constants.showSimpleProgressDialog(SplashActivity.this, getResources().getString(R.string.Loading));

        new UserAPI().CheckStatus(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObjectStatus result1 = (ResponseObjectStatus) result;
                Intent intent;
                if (result1.isStatus()) {
                    if (result1.getStatus_().getStatus_id() == 2) {
                        intent = new Intent(getApplicationContext(), PilotTravelActivity.class);
                        intent.putExtra("isEnd", true);
                    } else if (result1.getStatus_().getStatus_id() == 1) {
                        Log.e("CHECK_STATUS3", result1.getStatus_().getStatus_id() + "");
                        intent = new Intent(getApplicationContext(), PilotTravelActivity.class);
                        intent.putExtra("isStart", true);
                    } else {
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
                Intent intent = new Intent(getApplicationContext(), HomePilotActivity.class);
                ApplicationController.getInstance().deleteOrderNotification();
                ApplicationController.getInstance().deleteTempOrder();
                checkAndSetCurrentLocation(intent);
            }
        });
    }


    private void checkAndSetCurrentLocation(Intent intent) {
        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("state_", "1");
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (b) {
                Log.e("state_", "3");
                //Location between Company and Driver (Current Location)
                getCurrentLocation(intent);

            } else {
                Log.e("state_", "4");

                Toast.makeText(SplashActivity.this, "turn on"/* getResources().getString(R.string.turn_on)*/, Toast.LENGTH_SHORT).show();
                showSettingAlert();
            }
        } else {
            Log.e("state_", "2");

            showSettingAlert();

        }


    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // if from activity
                ActivityCompat.finishAffinity(SplashActivity.this);
            }
        });

        alertDialog.show();
    }


    private void getCurrentLocation(final Intent intent) {
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    LAT = location.getLatitude();
                    LNG = location.getLongitude();

//                    Intent intent = new Intent(getApplicationContext(), HomePilotActivity.class);
//                    ApplicationController.getInstance().deleteOrderNotification();
//                    ApplicationController.getInstance().deleteTempOrder();
                    intent.putExtra("LAT", LAT);
                    intent.putExtra("LNG", LNG);

                    startActivity(intent);
                    finish();
                }
            }

        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

}
