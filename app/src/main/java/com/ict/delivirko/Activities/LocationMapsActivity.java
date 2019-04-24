package com.ict.delivirko.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

import java.util.List;
import java.util.Locale;

public class LocationMapsActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude;
    private double longitude;
    private Button btnSave;
    private Marker MyMarker;
    private TextView address;
    private String nameLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSave = findViewById(R.id.save);
        address = findViewById(R.id.address);
        //---------------------------------

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameLocation != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("name", nameLocation);
                    returnIntent.putExtra("lat", latitude + "");
                    returnIntent.putExtra("lng", longitude + "");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {

                    Constants.showDialog(LocationMapsActivity.this, getResources().getString(R.string.choose_location));
                }
            }
        });


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                moveMap(latitude, longitude);
            }

            @Override
            public void onError(Status status) {

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(LocationMapsActivity.this, R.raw.style_json));
        mMap.setOnMapClickListener(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        googleMap.clear();
        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("state_", "1");
            try {
                locationMode = Settings.Secure.getInt(LocationMapsActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != 0 && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (b) {
                Log.e("state_", "3");

                getCurrentLocation();

            } else {
                Log.e("state_", "4");

                showSettingAlert();
            }
        } else {
            Log.e("state_", "2");

            showSettingAlert();

        }
    }


    private void moveMap(final double latitude, final double longitude) {
        setAddress(latitude, longitude);
        if (MyMarker != null) {
            MyMarker.remove();
        }
        LatLng latLng = new LatLng(latitude, longitude);
        MyMarker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)).title("My Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 15));


        mMap.getUiSettings().setZoomControlsEnabled(false);

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(LocationMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(LocationMapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LocationMapsActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                LocationMapsActivity.this.startActivity(intent);
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
                ActivityCompat.finishAffinity((Activity) LocationMapsActivity.this
                );
            }
        });


        alertDialog.show();
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        Log.e("latitude", "");
        Log.e("latitude1", latitude + "");

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(LocationMapsActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            moveMap(latitude, longitude);
                        }
                    }

                });
    }

    private void setAddress(double latitude, double longitude) {
        try {
            Geocoder geo = new Geocoder(LocationMapsActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                address.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    nameLocation = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                    address.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        latitude=latLng.latitude;
        longitude=latLng.longitude;
        moveMap(latitude,longitude);

    }
}
