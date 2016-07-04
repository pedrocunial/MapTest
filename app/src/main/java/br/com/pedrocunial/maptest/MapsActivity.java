package br.com.pedrocunial.maptest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static br.com.pedrocunial.maptest.model.JSONParser.getJSONFromUrl;
import static br.com.pedrocunial.maptest.model.PathGoogleMap.makeURL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap       mMap;
    private Random          random;
    private LatLng          home;
    private LatLng          insper;
    private LatLng          cesar;
    private LatLng          position;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    private final int             SHORT_INTERVAL = 1000;
    private final int             LONG_INTERVAL  = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LONG_INTERVAL);
        mLocationRequest.setFastestInterval(SHORT_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap   = googleMap;
        random = new Random();

        double[] homeLatLng = this.getLatLongFromPlace("Rua Gomes de Carvalho, 638");
        home = new LatLng(homeLatLng[0], homeLatLng[1]);
        mMap.addMarker(new MarkerOptions().position(home).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        double[] insperLatLng = this.getLatLongFromPlace("Rua Quata, 300");
        insper = new LatLng(insperLatLng[0], insperLatLng[1]);
        mMap.addMarker(new MarkerOptions().position(insper).title("Insper"));

        double[] cesarLatLng = this.getLatLongFromPlace("CESAR - Recife");
        cesar = new LatLng(cesarLatLng[0], cesarLatLng[1]);
        mMap.addMarker(new MarkerOptions().position(cesar).title("C.E.S.A.R"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cesar));


        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng position = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        mMap.addMarker(new MarkerOptions().position(position).title("You"));

        String url = makeURL(position, cesar);
//        new connectAsyncTask(url).execute();

//        String url      = makeURL(home, insper);
//        String urlVolta = makeURL(insper, home);
//        new connectAsyncTask(url).execute();
//        new connectAsyncTask(urlVolta).execute();
    }

    public double[] getLatLongFromPlace(String place) {
        double[] latLng = new double[2];

        try {
            Geocoder selectedPlaceGeocoder = new Geocoder(this);
            List<android.location.Address> address;

            address = selectedPlaceGeocoder.getFromLocationName(place, 5);

            if (address == null) {
                System.out.println("ops!");
            } else {
                android.location.Address location = address.get(0);
                latLng[0] = location.getLatitude();
                latLng[1] = location.getLongitude();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return latLng;
    }

    public void drawPath(String result, int color) {

        try {
            //Tranform the string into a json object
            final JSONObject json        = new JSONObject(result);
            JSONArray routeArray         = json.getJSONArray("routes");
            JSONObject routes            = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");

            String encodedString         = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            System.out.println("Hello");
            for(int z = 0; z < list.size()-1; z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                        .width(4)
                        .color(color).geodesic(true));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift  += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        LatLng position = new LatLng(lat, lng);

        String url = makeURL(position, cesar);
        new connectAsyncTask(url);
    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(MapsActivity.this);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            return getJSONFromUrl(url);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();

            if(result != null) {
                int color = random.nextInt(0xFFFFFF) + 0xFF000000;
                drawPath(result, color);
            }
        }
    }
}
