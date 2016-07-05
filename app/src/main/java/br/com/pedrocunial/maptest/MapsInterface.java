package br.com.pedrocunial.maptest;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by summerjob on 05/07/16.
 */
public interface MapsInterface {
    GoogleMap mMap = null;

    void drawPath(String result, int color);
    Context getContext();

}
