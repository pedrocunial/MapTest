package br.com.pedrocunial.maptest.utils;

import android.util.Log;
import android.view.View;

/**
 * Created by summerjob on 07/07/16.
 */
public class FooterOnClickListener implements View.OnClickListener {

    private final String TAG = this.toString();

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Clicou no footer");
    }
}
