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
//
// Asynchronous class to request user login using API
public class LoginRequest extends AsyncTask<String, String, String> {
    BufferedReader reader = null;
    HttpURLConnection urlConnection = null;
    String status="";
    @Override
    protected String doInBackground(String... userData) {
        startPostConnection(userData[0],userData[1],userData[2]);
        return status;
    }
    private void startPostConnection(String username, String password, String post_url){
        try {
            //Start API connection
            URL url = new URL(post_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");  //Start post request
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();
            sendJsonPost(username, password);//Send json to server
            status = checkConnection(urlConnection); //Get server response code and return status to LoginActivity
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //Nothing left to do, close connection and reader
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Sends JSON params
    private void sendJsonPost(String username, String password){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", username);
            jsonParam.put("password", password);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Returns connection response code
    private String checkConnection(HttpURLConnection urlConnection){
        int HttpResult = 0;
        String status="";
        try {
            HttpResult = urlConnection.getResponseCode();
            //if username and password exists on database, then return 200 status
            if(HttpResult == HttpURLConnection.HTTP_OK)
                return status = "200";
            //if username or password doesn't exists on database, then return 404 status
            if(HttpResult == HttpURLConnection.HTTP_NOT_FOUND)
                return status = "404";
            //if server not available
            if(HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR)
                return status="500";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    @Override
    protected void onPostExecute(String result) {
        Log.i("Output", result);
        delegate.processFinish(result);
    }
    //Login Interface
    public interface LoginResponse {
        void processFinish(Object output);
    }
    //Callback Interface
    public LoginResponse delegate = null;
    //LoginRequestActivity constructor
    public LoginRequest(LoginResponse loginResponse) {
        delegate = loginResponse;
    }

}
