package br.com.pedrocunial.maptest.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Pedro Cunial on 7/3/16.
 */
public class PathGoogleMap {

    private LatLng[] places;
    private int      numberOfPlaces;

    public PathGoogleMap(LatLng[] places) {
        this.places         = places;
        this.numberOfPlaces = places.length;
    }

    public static String makeURL(LatLng source, LatLng destination){
        double sourcelat = source.latitude;
        double sourcelog = source.longitude;
        double destlat   = destination.latitude;
        double destlog   = destination.longitude;

        StringBuilder urlString = new StringBuilder();


        urlString.append("http://maps.googleapis.com/maps/api/directions/json");

        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString( sourcelog));

        urlString.append("&destination=");// to
        urlString.append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true&language=en");

        return urlString.toString();
    }
}
