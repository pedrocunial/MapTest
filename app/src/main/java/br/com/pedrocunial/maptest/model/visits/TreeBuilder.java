package br.com.pedrocunial.maptest.model.visits;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by summerjob on 12/07/16.
 */

public class TreeBuilder {

    private Node                       root;
    private LatLng                     leaf;
    private List<Future<Integer>>      threads;
    private List<Callable<Integer>>    callables;
    private HashMap<LatLng[], Integer> path;

    public List<LatLng>             tree;

    public TreeBuilder(LatLng[] addresses, LatLng rootLatLng, LatLng finalLatLng) {
        root      = new Node(rootLatLng);
        leaf      = finalLatLng;
        tree      = new ArrayList<>();
        path      = new HashMap<>();
        threads   = new ArrayList<>();
        callables = new ArrayList<>();
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
        // DONE (?)
        int  i    = 0;
        Node node = root;

        while(i < addresses.length) {
            if(!node.sons.contains(addresses[i])) {
                Node newNode = new Node(addresses[i]);
                node.sons.add(newNode);
                node = newNode;
                if(i != 0) {
                    // stackoverflow.com/questions/9148899/returning-value-from-thread
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    callables.add(new PathGenerator(addresses[i-1], addresses[i]));
                    threads.add(executor.submit(callables.get(i)));
                    try {
                        path.put(new LatLng[]{addresses[i-1], addresses[i]}, threads.get(i).get());
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    } catch(ExecutionException e) {
                        e.printStackTrace();
                    }
                    executor.shutdown();
                }
            } else {
                node = node.sons.get(node.sons.indexOf(addresses[i]));
            }
            i++;
        }
        node.sons.add(new Node(leaf));
    }

    public Node getRoot() {
        return root;
    }

    public HashMap<LatLng[], Integer> getPaths() {
        return this.path;
    }
}
