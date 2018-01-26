package com.teampietro.hero.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolja on 20.01.18.
 */

public class Profile {

    public String name;

    public String email;

    public String profileImageUrl;

    public String token;

    public int tactics;

    public int endurance;

    public int teamplay;

    public int motivation;

    public int style;

    public double starFaceSimilarity;

    public double starPostSimilarity;
    public ArrayList<String> nearBy = new ArrayList<String>();

    public List<String> friends = new ArrayList<>();

    public static class ProfileUpdatedEvent {
    }

    private static Profile instance;

    private Profile() {
    }

    public static Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    public static Profile updateWithJson(JSONObject json) {
        Profile p = getInstance();

        try {
            p.name = json.getString("name");
            p.profileImageUrl = json.getString("profile_pic_url");
            JSONObject skills = json.getJSONObject("skills");
            p.tactics = skills.getInt("tactics");
            p.endurance = skills.getInt("endurance");
            p.motivation = skills.getInt("motivation");
            p.style = skills.getInt("style");
            p.teamplay = skills.getInt("teamplay");

            JSONObject star = json.getJSONObject("star");
            p.starPostSimilarity = star.getDouble("post_sim");
            p.starFaceSimilarity = star.getDouble("face_sim");

            JSONArray jsonFriends = json.getJSONArray("friends");

            List<String> friends = new ArrayList<>();
            for (int i = 0; i < jsonFriends.length(); ++i) {
                friends.add(jsonFriends.getString(i));
            }
            p.friends = friends;
        } catch (JSONException e) {
            Log.e("PROFILE", "JSON exception: " + e);
        }

        return p;
    }

}