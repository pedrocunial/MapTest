package br.com.pedrocunial.maptest.model.visits;

import com.google.android.gms.maps.model.LatLng;

import br.com.pedrocunial.maptest.model.JSONParser;
import br.com.pedrocunial.maptest.model.PathGoogleMap;

/**
 * Created by summerjob on 13/07/16.
 */
public class PathGeneratorThread extends Thread {
    /**
     * This thread should generate the shortest path between
     * two geographical points.
     * */

    private int    time;
    private LatLng start;
    private LatLng dest;

    public PathGeneratorThread(LatLng start, LatLng dest) {
        this.time  = 0;
        this.start = start;
        this.dest  = dest;
    }

    public void run() {
        String url  = PathGoogleMap.makeURL(start, dest);
        String json = JSONParser.getJSONFromUrl(url);
        this.time   = JSONParser.getTime(json);
    }

    public int getTime() throws TimeNotFoundException, NullTimeException {
        if(time > 0) {
            return time;
        } else if(time == 0) {
            throw new NullTimeException();
        } else {
            throw new TimeNotFoundException();
        }
    }

}
