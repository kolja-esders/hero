package com.teampietro.hero.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kolja on 20.01.18.
 */

public class GPSLocation {

    public double lat;

    public double lng;

    public static class LocationUpdatedEvent {
    }

    private static GPSLocation instance;

    private GPSLocation() {
    }

    public static GPSLocation getInstance() {
        if (instance == null) {
            instance = new GPSLocation();
        }
        return instance;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        try {
            json.put("lat", this.lat);
            json.put("lng", this.lng);
        } catch (JSONException e) {
            Log.e("GPS_LOCATION", "Json exception: " + e);
        }

        return json;
    }
}
