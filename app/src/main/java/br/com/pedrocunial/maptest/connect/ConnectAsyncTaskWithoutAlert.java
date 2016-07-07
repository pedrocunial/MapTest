package br.com.pedrocunial.maptest.connect;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;

import java.util.Random;

import br.com.pedrocunial.maptest.MapsInterface;

import static br.com.pedrocunial.maptest.model.JSONParser.getJSONFromUrl;

/**
 * Created by summerjob on 05/07/16.
 */

public class ConnectAsyncTaskWithoutAlert extends AsyncTask<Void, Void, String> {
    private String        url;
    private MapsInterface activity;
    private Random        random;

    public ConnectAsyncTaskWithoutAlert(String urlPass, MapsInterface activity) {
        this.random   = new Random();
        this.url      = urlPass;
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Void... params) {
        return getJSONFromUrl(url);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result != null) {
            activity.drawPath(result, Color.BLUE);
        }
    }
}
