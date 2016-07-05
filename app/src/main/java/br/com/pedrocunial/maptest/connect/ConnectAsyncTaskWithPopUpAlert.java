package br.com.pedrocunial.maptest.connect;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.Random;

import br.com.pedrocunial.maptest.MapsInterface;

import static br.com.pedrocunial.maptest.model.JSONParser.getJSONFromUrl;

/**
 * Created by summerjob on 05/07/16.
 */

public class ConnectAsyncTaskWithPopUpAlert extends AsyncTask<Void, Void, String> {
    private String         url;
    private MapsInterface  activity;
    private Random         random;
    private ProgressDialog progressDialog;

    public ConnectAsyncTaskWithPopUpAlert(String urlPass,
                                          MapsInterface activity) {
        this.random   = new Random();
        this.url      = urlPass;
        this.activity = activity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity.getContext());
        progressDialog.setMessage("Traçando a rota, por favor aguarde...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }
    @Override
    protected String doInBackground(Void... params) {
        return getJSONFromUrl(url);
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.hide();
        super.onPostExecute(result);

        if(result != null) {
            int color = random.nextInt(0xFFFFFF) + 0xFF000000;
            activity.drawPath(result, color);
        }
    }
}
