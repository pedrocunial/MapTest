package br.com.pedrocunial.maptest.utils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by summerjob on 05/07/16.
 */

public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

    private final String TAG = this.toString();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
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
            case 4:
                Log.i(TAG, "Sair");
                break;
        }
    }
}
