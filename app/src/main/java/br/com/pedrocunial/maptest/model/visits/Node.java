package br.com.pedrocunial.maptest.model.visits;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by summerjob on 12/07/16.
 */

public class Node {

    private int    passes;
    private LatLng position;

    public List<Node> sons;

    public Node(LatLng position) {
        this.position = position;
        this.passes   = 0;
        this.sons     = new ArrayList<>();
    }

    public LatLng getPosition() {
        return position;
    }

    public int getPasses() {
        return passes;
    }

    public void incrementPasses() {
        passes++;
    }
}
