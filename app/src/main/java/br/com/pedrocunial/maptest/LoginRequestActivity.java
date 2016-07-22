package br.com.pedrocunial.maptest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by summerjob on 21/07/16.
 */
public class LoginRequestActivity extends AsyncTask<String, String, String> {

    public interface AsyncResponse {
        void processFinish(Object output);
    }
    public AsyncResponse delegate = null;//Call back interface

    public LoginRequestActivity(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }
    BufferedReader reader = null;
    HttpURLConnection urlConnection = null;
    @Override
    protected String doInBackground(String... userData) {
        String st="falhou";
        try {
            String f_url = userData[2];
            String username = userData[0];
            String password = userData[1];
            URL url = new URL(f_url);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("username", username);
                jsonParam.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                st = "ok";
                System.out.println("enviou");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            try {
                if(reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st;
    }
    @Override
    protected void onPostExecute(String result) {
        Log.i("Output", result);
        delegate.processFinish(result);
    }

}
