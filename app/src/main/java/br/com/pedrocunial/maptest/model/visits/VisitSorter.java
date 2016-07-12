package br.com.pedrocunial.maptest.model.visits;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by summerjob on 12/07/16.
 */

public class VisitSorter {

    private HashMap<String, Integer> paths;

    private Node        tree;
    private TreeBuilder treeBuilder;

    public VisitSorter(LatLng[] addresses, LatLng rootLatLng, LatLng finalLatLng) {
        paths       = new HashMap<>();
        treeBuilder = new TreeBuilder(addresses, rootLatLng, finalLatLng);
        tree        = treeBuilder.getRoot();
    }

    private void parseTree() {

    }

}
