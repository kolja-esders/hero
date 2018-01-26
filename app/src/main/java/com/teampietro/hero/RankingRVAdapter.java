package com.teampietro.hero;


import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingRVAdapter extends RecyclerView.Adapter<RankingRVAdapter.RankingViewHolder>{

    private Activity listContext;

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cv;
        TextView rankingRank;
        TextView rankingName;
        CircleImageView rankingImage;

        RankingViewHolder(View itemView) {
            super(itemView);

            Log.d("myTag", "rankingholder");
            cv = itemView.findViewById(R.id.cv);
            rankingRank = itemView.findViewById(R.id.ranking_rank);
            rankingName = itemView.findViewById(R.id.ranking_name);
            rankingImage = itemView.findViewById(R.id.ranking_image);
        }
    }
    List<Ranking> rankings;


    RankingRVAdapter(Activity context, List<Ranking> rankings){
        super();
        listContext = context;
        this.rankings = rankings;
    }

    /**
     RVAdapter(List<Reward> rewards){
     this.rewards = rewards;
     }
     **/

    @Override
    public int getItemCount() {
        return rankings.size();
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(listContext).inflate(R.layout.fragment_ranking, viewGroup, false);
        RankingViewHolder pvh = new RankingViewHolder(v);
        Log.d("pvh", pvh.toString());
        return pvh;
    }


    @Override
    public void onBindViewHolder(RankingViewHolder rewardViewHolder, int i) {
        rewardViewHolder.rankingRank.setText("#"+String.valueOf(rankings.get(i).rank));
        rewardViewHolder.rankingName.setText(rankings.get(i).name);
        rewardViewHolder.rankingImage.setImageResource(rankings.get(i).image);
        Log.d("myTag", "onbinder");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("myTag", "onAttached");
    }


}
