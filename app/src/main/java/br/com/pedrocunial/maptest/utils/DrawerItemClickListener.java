package br.com.pedrocunial.maptest.utils;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import br.com.pedrocunial.maptest.AboutActivity;
import br.com.pedrocunial.maptest.MapsInterface;

/**
 * Created by summerjob on 05/07/16.
 */

public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

    private MapsInterface activity;

    private final String TAG = this.toString();

    public DrawerItemClickListener(MapsInterface activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        // Getting chosen option
        switch(position) {
            case 0: // Profile
                Log.i(TAG, "Profile");
                break;
            case 1: // Visitas do dia
                Log.i(TAG, "Visitas do dia");
                break;
            case 2: // Ajuda
                Log.i(TAG, "Ajuda");
                break;
            case 3: // Sobre
                Log.i(TAG, "Sobre");
                break;
            case 4: // Sair
                Log.i(TAG, "Sair");
                break;
        }
        activity.startActivity(new Intent(activity.getContext(), AboutActivity.class));
    }
}
