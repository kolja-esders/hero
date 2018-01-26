package com.teampietro.hero.helper;

import android.util.Log;

import com.facebook.login.LoginManager;
import com.teampietro.hero.models.GPSLocation;
import com.teampietro.hero.models.Profile;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kolja on 20.01.18.
 */

public class APIClient {
    // public static final String BASE_URL = "https://9e16dc09.ngrok.io";
    public static final String BASE_URL = "https://heroporcupine.cfapps.eu10.hana.ondemand.com";

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setConnectTimeout(30);
        client.setResponseTimeout(30);
    }

    // Updates global profile instance
    public static void fetchProfile(String token) {
        // Get access token
        RequestParams params = new RequestParams();
        params.add("token", token);

        client.get(BASE_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Profile p = Profile.updateWithJson(response);
                Log.i("API_CLIENT", "Successfully fetched profile.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("API_CLIENT", "Failure during profile fetch.");
            }
        });
    }

    public static void fetchProfile(String token, JsonHttpResponseHandler handler) {
        // Get access token
        RequestParams params = new RequestParams();
        params.add("token", token);
        client.get(BASE_URL + "/login", params, handler);
    }

    public static void sendLocation(String token, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        //params.add("lat", String.valueOf(GPSLocation.getInstance().lat));
        params.add("lng", String.valueOf(GPSLocation.getInstance().lng));
        params.add("token", token);
        client.get(BASE_URL + "/loc", params, handler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void fetchRanking(JsonHttpResponseHandler handler) {
        // Get access token
        RequestParams params = new RequestParams();
        client.get(BASE_URL + "/rank", params, handler);
    }


    public static ArrayList<String> getNearbyPeople(String token){


        sendLocation(token, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println ("asdf");
                    JSONArray x = response.getJSONArray("nearby");
                    for(int n = 0; n < x.length(); n++)
                    {
                        String p = x.getString(n);
                        ArrayList<String> pp = Profile.getInstance().nearBy;
                        System.out.println(p);
                        pp.add(p);
                    }

                }
                catch (Exception x){}

                Log.i("API_CLIENT", "Successfully fetched nearby people.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("API_CLIENT", "Failure during profile fetch.");
            }
        });
        int i=0;
        while (Profile.getInstance().nearBy.size() ==0){
            i++;
            try{
                Thread.sleep(100);
            }
            catch (Exception e){

            }}
        System.out.println("counter"+ i);

        return Profile.getInstance().nearBy;

    }



    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
