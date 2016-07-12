package br.com.pedrocunial.maptest.model.visits;

/**
 * Created by summerjob on 12/07/16.
 */
public class Node {

    private double lat;
    private double lng;
    private int    passes;

    public Node(double lat, double lng) {
        this.lat    = lat;
        this.lng    = lng;
        this.passes = 0;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getPasses() {
        return passes;
    }

    public void incrementPasses() {
        passes++;
    }
}
