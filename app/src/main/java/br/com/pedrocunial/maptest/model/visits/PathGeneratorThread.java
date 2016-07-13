package br.com.pedrocunial.maptest.model.visits;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by summerjob on 13/07/16.
 */
public class PathGeneratorThread extends Thread {

    private int    time;
    private LatLng start;
    private LatLng dest;

    public PathGeneratorThread(LatLng start, LatLng dest) {
        this.time  = 0;
        this.start = start;
        this.dest  = dest;
    }

    public void run() {

    }

}
