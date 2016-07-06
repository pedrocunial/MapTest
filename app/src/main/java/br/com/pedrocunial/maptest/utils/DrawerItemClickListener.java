package br.com.pedrocunial.maptest.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import br.com.pedrocunial.maptest.AboutActivity;

/**
 * Created by summerjob on 05/07/16.
 */

public class DrawerItemClickListener extends AppCompatActivity implements android.widget.AdapterView.OnItemClickListener {

    private Context mainContext;

    private final String TAG = this.toString();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    public void setContext(Context context) {
        mainContext = context;
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
            case 4: // Sair
                Log.i(TAG, "Sair");
                break;
        }
       startActivity(new Intent(mainContext, AboutActivity.class));
    }
}
