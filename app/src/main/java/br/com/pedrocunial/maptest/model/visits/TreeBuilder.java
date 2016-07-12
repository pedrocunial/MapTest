package br.com.pedrocunial.maptest.model.visits;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by summerjob on 12/07/16.
 */

public class TreeBuilder {

    private Node   root;
    private LatLng leaf;

    public List<LatLng> tree;

    public TreeBuilder(LatLng[] addresses, LatLng rootLatLng, LatLng finalLatLng) {
        root = new Node(rootLatLng);
        leaf = finalLatLng;
        tree = new ArrayList<>();
        permute(addresses, 0, addresses.length-1);
    }

    // http://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
    private void permute(LatLng[] addresses, int listStart, int listEnd) {
        if(listStart == listEnd) {
            build(addresses);
        } else {
            for(int i=listStart; i <= listEnd; i++) {
                swap(addresses, listStart, i);
                permute(addresses, listStart+1, listEnd);
                swap(addresses, listStart, i);
            }
        }
    }

    private void swap(LatLng[] array, int i, int j) {
        LatLng tmp = array[i];
        array[i]   = array[j];
        array[j]   = tmp;
    }

    private void build(LatLng[] addresses) {
        // TODO
        int i = 0;

        Node node = root;

        while(i < addresses.length) {

            if(!node.sons.contains(addresses[i])) {

                Node newNode = new Node(addresses[i]);
                node.sons.add(newNode);
                node = newNode;
                i++;

            } else {

                node = node.sons.get(node.sons.indexOf(addresses[i]));
            }

        }

    }

}
