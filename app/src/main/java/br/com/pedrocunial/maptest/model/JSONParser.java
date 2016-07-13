package br.com.pedrocunial.maptest.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pedro Cunial on 7/3/16.
 */

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public static String getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            URL urlnew = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlnew.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            json = sb.toString();
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        Log.d("JSON_ROUTE", json);
        return json;
    }

    public static int getTimeMinutes(String json) {
        // Converts the time from seconds to minutes
        return (getTime(json) / 60);
    }

    public static int getTime(String json) {
        int time = 0;

        if(!json.isEmpty()) {
            try {
                JSONObject jsonObject  = new JSONObject(json);
                // Get the object that contains all routes
                JSONArray  routesArray = jsonObject.getJSONArray("routes");
                // Grab the first route (best)
                JSONObject route       = routesArray.getJSONObject(0);
                // Get all "legs" from the route
                JSONArray  legsArray   = route.getJSONArray("legs");
                // The first one is the one we want
                JSONObject leg            = legsArray.getJSONObject(0);
                JSONObject durationObject = leg.getJSONObject("duration");
                time += Integer.valueOf(durationObject.getString("value"));
                // Return the value in seconds
                return time;

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
