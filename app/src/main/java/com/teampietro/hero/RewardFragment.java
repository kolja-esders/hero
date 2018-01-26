package com.teampietro.hero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RewardFragment extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_reward);
//        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//
//        Reward rewards = new Reward("test", R.drawable.video_chat);
//
//        RVAdapter adapter = new RVAdapter(this, rewards.initializeData());
//
//        rv.setAdapter(adapter);
       // Toast.makeText(getApplicationContext(), adapter.getItemCount(), Toast.LENGTH_SHORT).show();


    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra("Reward", "empty");
        startActivity(intent);
    }


}

