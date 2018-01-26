package com.teampietro.hero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teampietro.hero.models.Profile;


public class Connect2PeopleFragment extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_connect2people);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv2);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        Connect2People connect2People = new Connect2People("test", R.drawable.video_chat, "test");

        C2PAdapter adapter = new C2PAdapter(this, connect2People.initializeData());

        rv.setAdapter(adapter);
       // Toast.makeText(getApplicationContext(), adapter.getItemCount(), Toast.LENGTH_SHORT).show();




    }




}

