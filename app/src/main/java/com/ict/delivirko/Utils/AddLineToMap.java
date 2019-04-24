package com.ict.delivirko.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ict.delivirko.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddLineToMap {
    private GoogleMap mMap;
    private ArrayList markerPoints = new ArrayList();
    private Context context;
    //For Custom Line
    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    private Marker driverMarker;


    public AddLineToMap(GoogleMap mMap, Context context) {
        this.mMap = mMap;
        this.context = context;
    }

    public void addMarker(LatLng latLng) {
        if (markerPoints.size() > 1) {
            markerPoints.clear();
            mMap.clear();
        }

        // Adding new item to the ArrayList
        markerPoints.add(latLng);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(latLng);

        if (markerPoints.size() >= 2) {
            LatLng origin = (LatLng) markerPoints.get(0);
            LatLng dest = (LatLng) markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);
            Log.e("lnglng", url + "");

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {


        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            try {
                Log.e("lnglng", result.get(0) + "");
                Log.e("lnglng", result.size() + "");
            } catch (Exception e) {

            }


            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));


                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(17);
                lineOptions.pattern(PATTERN_POLYGON_ALPHA);
                lineOptions.color(R.color.colorPrimary);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            try {
                mMap.addPolyline(lineOptions);
                Log.e("statusOrder_","hhhhhhhhhhhhh11111111");

            } catch (Exception e) {
                Log.e("statusOrder_",e.getMessage());
                if (markerPoints.size() == 1) {
                    mMap.addMarker(new MarkerOptions().position((LatLng) markerPoints.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.rest_icon)));
                } else if (markerPoints.size() == 2) {
                    driverMarker = mMap.addMarker(new MarkerOptions().position((LatLng) markerPoints.get(1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)));
                }
            } finally {
                Log.e("statusOrder_","hhhhhhhhhhhh");

            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = Constants.SENSOR + "=false";
        String mode = Constants.MODE + "=driving";
        String Lang = Constants.LANGUAGE + "=en-EN";
        String Key = "key=" + Constants.GOOGLE_API_KEY;
        // getResources().getString(R.string.google_api_key);
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode + "&" + Lang + "&" + Key + "&" + sensor;


        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    public Bitmap getMarkerBitmapFromView(String eta, String value) {
        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_info_window, null);
        TextView markertext = customMarkerView.findViewById(R.id.txt_eta);
        ImageView iv = customMarkerView.findViewById(R.id.eta_iv);
        if (value.equals("1")) {
            iv.setImageResource(R.drawable.rest_icon);
        } else {
            iv.setImageResource(R.drawable.my_location);
        }
        if (eta.equals("")) {
            markertext.setVisibility(View.GONE);
        }
        markertext.setText(eta);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
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

    public void ZoomBetween2Marker(LatLng CompanyLatLang, LatLng DriverLatLang) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(CompanyLatLang);
        builder.include(DriverLatLang);
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 70);
        mMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
            public void onCancel() {
            }

            public void onFinish() {
                CameraUpdate zout = CameraUpdateFactory.zoomBy(-2.0f);
                mMap.animateCamera(zout);
            }
        });
    }

    public void deleteDriverMarker() {
        if (driverMarker != null)
            driverMarker.remove();
    }

}
