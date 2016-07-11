package br.com.pedrocunial.maptest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.pedrocunial.maptest.connect.ConnectAsyncTaskWithoutAlert;
import br.com.pedrocunial.maptest.utils.DrawerItemClickListener;
import br.com.pedrocunial.maptest.utils.FooterOnClickListener;
import br.com.pedrocunial.maptest.utils.ImageOptions;

import static br.com.pedrocunial.maptest.model.PathGoogleMap.makeURL;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        MapsInterface {

    private boolean isHamburgerMenuOn = false;
    private boolean isFooterLarge;

    private int                timePreview;
    private LatLng             cesar;
    private String             dest;
    private LatLng             myPosition;
    private String             problemCode;
    private String             genericProblemOverview;
    private String             clientName;
    private TextView           largeDestinationView;
    private TextView           largeProblemCodeView;
    private TextView           clientNameView;
    private TextView           genericProblemOverviewView;
    private TextView           destinationView;
    private TextView           problemCodeView;
    private ImageView          problemIdentifierView;
    private ImageView          largeProblemIdentifierView;
    private GoogleMap          mMap;
    private LinearLayout       footerLayout;  // Map footer
    private LinearLayout       largeFooterLayout;
    private LocationRequest    mLocationRequest;
    private GoogleApiClient    mGoogleApiClient;

    // Drawer Navigation
    private String                mActivityTitle;
    private ListView              mDrawerList;
    private DrawerLayout          mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private final int    ZOOM           = 19;
    private final int    LONG_INTERVAL  = 5000;
    private final double LINE_THICKNESS = 1;
    private final String TAG            = "MapApp";
    private final String NAME           = "Jose Carlos Silva";
    private final int    SDK            = android.os.Build.VERSION.SDK_INT;

    private FooterOnClickListener footerOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mActivityTitle = getTitle().toString();

        setupDrawer();

        buildGoogleApiClient();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawer() {
        // Sets up the drawer menu (hamburger menu)
        String[] options = getResources().getStringArray(R.array.sandwich);
        mDrawerList      = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout    = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set the adapter for the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, options);

        View      headerView  = getLayoutInflater().inflate(R.layout.header, null);
        Drawable  picture     = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bob);
        ImageView pictureView = (ImageView) headerView.findViewById(R.id.profile_image);
        TextView  nameView    = (TextView)  headerView.findViewById(R.id.textView1);

        pictureView.setBackground(picture);
        nameView.setText(NAME);

        assert mDrawerList != null;
        mDrawerList.setAdapter(adapter);
        mDrawerList.addHeaderView(headerView);

        // Makes the menu toggleable
        mDrawerToggle = new MyActionBarDrawerToggle();

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect to the client
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting from the client
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void buildGoogleApiClient() {
        // Starts the google api client (which is an asynchronous task)
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
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
        mMap                   = googleMap;
        dest                   = "C.E.S.A.R - Recife";
        clientName             = "Alberto de Jesus";
        problemCode            = "#12345";
        genericProblemOverview = "Problema no controle";

        defineLayoutsAndViews();
        startViews();
        startLayouts();
        markDestination(dest, "Destino");
    }

    private void defineLayoutsAndViews() {
        footerLayout          = (LinearLayout) findViewById(R.id.footer);
        largeFooterLayout     = (LinearLayout) findViewById(R.id.extended_footer);
        destinationView       = (TextView)     findViewById(R.id.dest);
        problemCodeView       = (TextView)     findViewById(R.id.problem_code);
        largeDestinationView  = (TextView)     findViewById(R.id.extended_dest);
        largeProblemCodeView  = (TextView)     findViewById(R.id.extended_problem_code);
        clientNameView        = (TextView)     findViewById(R.id.client_name);
        problemIdentifierView = (ImageView)    findViewById(R.id.image_identifier);

        genericProblemOverviewView = (TextView) findViewById(R.id.generic_problem_overview);
        largeProblemIdentifierView = (ImageView) findViewById(R.id.large_image_identifier);
    }

    private void startViews() {
        assert destinationView            != null;
        assert largeDestinationView       != null;
        assert problemCodeView            != null;
        assert largeProblemCodeView       != null;
        assert clientNameView             != null;
        assert problemIdentifierView      != null;
        assert genericProblemOverviewView != null;
        assert largeProblemIdentifierView != null;
        destinationView.setText(dest);
        largeDestinationView.setText(dest);
        problemCodeView.setText(problemCode);
        largeProblemCodeView.setText(problemCode);
        clientNameView.setText(clientName);
        genericProblemOverviewView.setText(genericProblemOverview);

        int randomImage = ImageOptions.getRandomImage();
        problemIdentifierView.setImageResource(randomImage);
        largeProblemIdentifierView.setImageResource(randomImage);
    }

    private void startLayouts() {
        assert footerLayout      != null;
        assert largeFooterLayout != null;
        isFooterLarge             = false;
        footerOnClickListener     = new FooterOnClickListener(isFooterLarge,
                footerLayout, largeFooterLayout, problemIdentifierView,
                largeProblemIdentifierView);
        footerLayout.setOnClickListener(footerOnClickListener);
        footerLayout.setVisibility(View.VISIBLE);
        largeFooterLayout.setOnClickListener(footerOnClickListener);
        largeFooterLayout.setVisibility(View.INVISIBLE);
        largeProblemIdentifierView.setVisibility(View.INVISIBLE);
    }

    private void markDestination(String dest, String title) {
        double[] cesarLatLng = this.getLatLongFromPlace(dest);
        cesar = new LatLng(cesarLatLng[0], cesarLatLng[1]);
        mMap.addMarker(new MarkerOptions().position(cesar).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cesar));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM));
    }

    public double[] getLatLongFromPlace(String place) {
        double[] latLng = new double[2];

        try {
            Geocoder selectedPlaceGeocoder = new Geocoder(this);
            List<android.location.Address> address;

            address = selectedPlaceGeocoder.getFromLocationName(place, 5);

            if (address != null) {
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM));

        try {
            //Tranform the string into a json object
            final JSONObject json              = new JSONObject(result);
            JSONArray        routeArray        = json.getJSONArray("routes");
            JSONObject       routes            = routeArray.getJSONObject(0);
            JSONObject       overviewPolylines = routes.getJSONObject("overview_polyline");

            String       encodedString = overviewPolylines.getString("points");
            List<LatLng> list          = decodePoly(encodedString);

            for (int z = 0; z < list.size() - 1; z++) {
                LatLng src  = list.get(z);
                LatLng dest = list.get(z + 1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width((int) (ZOOM * LINE_THICKNESS))
                        .color(color).geodesic(true));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return MapsActivity.this;
    }

    @Override
    public void setTimePreview(int timePreview) {
        this.timePreview = timePreview;
    }

    @Override
    public int getTimePreview() {
        return this.timePreview;
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly  = new ArrayList<LatLng>();
        int          index = 0, len = encoded.length();
        int          lat   = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift  = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Log.i(TAG, "Starting...");

        updateMyPosition(lat, lng);

        String url  = makeURL(myPosition, cesar);
        new ConnectAsyncTaskWithoutAlert(url, this).execute();
        Log.i(TAG, "Complete!");
    }

    private void updateMyPosition(double lat, double lng) {
        myPosition = new LatLng(lat, lng);

        if(hasArrived(lat, lng)) {
            Log.i(TAG, "Chegou!");
        } else {
            Log.i(TAG, "Nao chegou");
        }

        mMap.clear();
        Marker locationMarker = mMap.addMarker(new MarkerOptions().position(cesar)
                .title("C.E.S.A.R"));
        mMap.addMarker(new MarkerOptions().position(myPosition).title("You!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(myPosition));

        locationMarker.showInfoWindow();
    }

    private boolean hasArrived(double lat, double lng) {
        if(((cesar.latitude + 0.012 > lat) && (cesar.latitude - 0.012 < lat)) &&
                ((cesar.longitude + 0.012 > lng) && (cesar.longitude - 0.012 < lng))) {
            return true;
        }
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(LONG_INTERVAL); // Update location every 'x' milliseconds

        // Permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onBackPressed() {
        // We need to override android's default back button soo that it closes the drawer if
        // it's open instead of quitting the app
        if(isHamburgerMenuOn) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private class MyActionBarDrawerToggle extends ActionBarDrawerToggle {
        public MyActionBarDrawerToggle() {
            super(MapsActivity.this, MapsActivity.this.mDrawerLayout, R.string.drawer_open,
                    R.string.drawer_close);
        }

        /** Called when a drawer has settled in a completely open state. */
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle("Opções");
            isHamburgerMenuOn = true;
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        /** Called when a drawer has settled in a completely closed state. */
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            getSupportActionBar().setTitle(mActivityTitle);
            isHamburgerMenuOn = false;
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }
}