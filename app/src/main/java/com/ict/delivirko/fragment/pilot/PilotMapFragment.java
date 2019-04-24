package com.ict.delivirko.fragment.pilot;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.ict.delivirko.API.ResponseNearestCompany;
import com.ict.delivirko.API.ResponseNearestDriver;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Activities.PilotTravelActivity;
import com.ict.delivirko.FirebaseUtils.MyFirebaseMessagingService;
import com.ict.delivirko.Interfaces.ObjectClickListener;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.OrderNotification;
import com.ict.delivirko.Module.pilot.NearestCompany;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.Utils.LatLngInterpolator;
import com.ict.delivirko.Utils.MarkerAnimation;


public class PilotMapFragment extends Fragment implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private ImageButton currentLocationImageButton;


    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && mMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            showMarker(currentLocation);
        }
    };

    public PilotMapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map_pilot, container, false);

        currentLocationImageButton = v.findViewById(R.id.currentLocationImageButton);
        currentLocationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.currentLocationImageButton && mMap != null && currentLocation != null)
                    animateCamera(currentLocation);
            }
        });
        driverData();

        SupportMapFragment mapPilot = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapHomePilot);
        mapPilot.getMapAsync(this);

        MyFirebaseMessagingService.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(OrderNotification orderNotification) {
                Log.e("Status_id", orderNotification.getStatus_id() + "");
                if (orderNotification.getStatus_id().equals("7")) {
                    Intent intent = new Intent(getActivity(), PilotTravelActivity.class);
                    intent.putExtra("orderNo", Integer.valueOf(orderNotification.getOrder_id()));
                    intent.putExtra("Status_id", Integer.valueOf(orderNotification.getStatus_id()));
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.clear();

        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("state_", "1");
            try {
                locationMode = Settings.Secure.getInt(getActivity().getContentResolver(), "location_mode");

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != 0 && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (b) {
                Log.e("state_", "3");


            } else {
                Log.e("state_", "4");

                Toast.makeText(getActivity(), "turn on"/* getResources().getString(R.string.turn_on)*/, Toast.LENGTH_SHORT).show();
                showSettingAlert();
            }
        } else {
            Log.e("state_", "2");

            showSettingAlert();

        }




    }


    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getActivity().finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // if from activity
                ActivityCompat.finishAffinity(getActivity());
            }
        });

        alertDialog.show();
    }


    public void driverData() {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));
        new UserAPI().driverData(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseNearestDriver responseNearestCompany = (ResponseNearestDriver) result;
                if (responseNearestCompany != null) {
                    if (responseNearestCompany.isStatus()) {
                        for (NearestCompany nearestCompany : responseNearestCompany.getDataNearestCompany().getNearestCompanies()) {
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(nearestCompany.getName(), false))).position(new LatLng(nearestCompany.getLat(), nearestCompany.getLng())));
                        }

                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseNearestCompany responseError = (ResponseNearestCompany) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


    private Bitmap getMarkerBitmapFromView(String eta, boolean isDriver) {
        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_info_window, null);
        TextView markertext = customMarkerView.findViewById(R.id.txt_eta);
        ImageView iv = customMarkerView.findViewById(R.id.eta_iv);
        if (isDriver) {
            iv.setImageResource(R.drawable.my_location);
        } else {
            iv.setImageResource(R.drawable.rest_icon);
        }
        if (eta.equals("")) {
            markertext.setVisibility(View.GONE);
        }
        markertext.setText(eta);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildLayer();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
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
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (currentLocationMarker == null)
            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(getResources().getString(R.string.myLocation), true))).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }


    @Override
    public void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            startCurrentLocationUpdates();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        mMap = null;
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(getActivity(), "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(getActivity(), "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

}
