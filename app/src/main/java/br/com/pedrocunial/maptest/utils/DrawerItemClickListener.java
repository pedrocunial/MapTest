package br.com.pedrocunial.maptest.utils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by summerjob on 05/07/16.
 */

public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        Log.i("jiboia", "clicou!");
    }


}
