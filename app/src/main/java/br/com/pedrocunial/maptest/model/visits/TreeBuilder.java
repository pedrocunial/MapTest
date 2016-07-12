package br.com.pedrocunial.maptest.model.visits;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by summerjob on 12/07/16.
 */
public class TreeBuilder {

    private String[] addresses;
    private HashMap<String, Integer> paths;

    public TreeBuilder(String[] addresses) {
        this.addresses = addresses;
        this.paths     = new HashMap<>();
    }

    public HashMap<String, Integer> getPaths() {
        return paths;
    }

    private void build() {

    }

}
