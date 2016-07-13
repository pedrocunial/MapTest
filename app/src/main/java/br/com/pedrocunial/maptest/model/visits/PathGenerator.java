package br.com.pedrocunial.maptest.model.visits;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import br.com.pedrocunial.maptest.model.JSONParser;
import br.com.pedrocunial.maptest.model.PathGoogleMap;

/**
 * Created by summerjob on 13/07/16.
 */
public class PathGenerator implements Callable<Integer> {
    /**
     * This thread should generate the shortest path between
     * two geographical points.
     * */

    private int    time;
    private LatLng start;
    private LatLng dest;

    public PathGenerator(LatLng start, LatLng dest) {
        this.time  = 0;
        this.start = start;
        this.dest  = dest;
    }

    public void run() {
        String url  = PathGoogleMap.makeURL(start, dest);
        String json = JSONParser.getJSONFromUrl(url);
        this.time   = JSONParser.getTime(json);
    }

    @Override
    public Integer call() throws Exception {
        run();
        if(time > 0) {
            return (time / 60);
        } else if(time == 0) {
            throw new NullTimeException();
        } else {
            throw new TimeNotFoundException();
        }
    }
}
