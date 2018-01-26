package com.teampietro.hero;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.teampietro.hero.helper.APIClient;
import com.teampietro.hero.models.Profile;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Ranking{
    int rank;
    String name;
    int image;

    Ranking(int rank, String name, int image){
        this.rank = rank;
        this.name = name;
        this.image = image;
    }

    private List<Ranking> rankings;

    public  List<Ranking> initializeData(){

        rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "Gerardo Millhouse", R.drawable.fan1));
        rankings.add(new Ranking(2, "Kolja Esders", R.drawable.kolja));
        rankings.add(new Ranking(3, "Edith Zahler", R.drawable.fan2));
        rankings.add(new Ranking(4, "Roscoe Saidi", R.drawable.fan3));
        rankings.add(new Ranking(5, "Dusti Justice", R.drawable.fan4));
        rankings.add(new Ranking(6, "Noriko Fielden", R.drawable.fan5));

        return rankings;


    }


}