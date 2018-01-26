package com.teampietro.hero;

import java.util.ArrayList;
import java.util.List;




public class Reward{
    String name;
    int image;

    Reward(String name, int image){
        this.name = name;
        this.image = image;
    }

    private List<Reward> rewards;

    public  List<Reward> initializeData(){
        rewards = new ArrayList<>();
        rewards.add(new Reward("Attend soccer match", R.drawable.stadium));
        rewards.add(new Reward("Video Chat", R.drawable.video_chat));
        return rewards;
    }


}