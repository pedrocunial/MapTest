package br.com.pedrocunial.maptest;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by summerjob on 05/07/16.
 */
public interface MapsInterface {
    GoogleMap mMap = null;

    void drawPath(String result, int color);
    Context getContext();

    void startActivity(Intent intent);

    void setTimePreview(int timePreview);
    int  getTimePreview();
}
