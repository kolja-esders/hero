package com.teampietro.hero;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RankingFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, parent, false);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        Ranking rankings = new Ranking(10,"test", R.drawable.video_chat);

        RankingRVAdapter adapter = new RankingRVAdapter(getActivity(), rankings.initializeData());

        rv.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = rv.getLayoutManager().getChildCount();

                LinearLayout user =  (LinearLayout)rv.getLayoutManager().findViewByPosition(1);
                ((TextView)user.findViewById(R.id.ranking_rank)).setTypeface(((TextView)user.findViewById(R.id.ranking_rank)).getTypeface(), Typeface.BOLD);
                ((TextView)user.findViewById(R.id.ranking_name)).setTypeface(((TextView)user.findViewById(R.id.ranking_name)).getTypeface(), Typeface.BOLD);}
        }, 250);





        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}

