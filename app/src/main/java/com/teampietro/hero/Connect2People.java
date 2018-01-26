package com.teampietro.hero;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.teampietro.hero.helper.APIClient;
import com.teampietro.hero.models.Profile;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class Connect2People {
    String name;
    int image;
    String btn;

    Connect2People(String name, int image, String _buttonText){
        this.name = name;
        this.image = image;
        this.btn= _buttonText;

    }

    private List<Connect2People> rewards;

    public  List<Connect2People> initializeData(){
        rewards = new ArrayList<>();
//Profile.getInstance().
/*      APIClient.getNearbyPeople("help me");


        System.out.println(Profile.getInstance().nearBy.size());

        for (String name: Profile.getInstance().nearBy) {
            rewards.add(new Connect2People(name, R.drawable.arjen_robben, "meet"));
        }
*/
        rewards.add(new Connect2People("Arjen Robben", R.drawable.arjen_robben, "meet"));

        rewards.add(new Connect2People("Peter Lustig", R.drawable.peter_lustig, "meet"));
        return rewards;
    }


}