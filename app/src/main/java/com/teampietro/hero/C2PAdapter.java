package com.teampietro.hero;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teampietro.hero.models.Profile;

import java.util.List;

public class C2PAdapter extends RecyclerView.Adapter<C2PAdapter.C2PViewHolder> {

    private Activity listContext;

    public static class C2PViewHolder extends RecyclerView.ViewHolder {
   //     CardView cv;
        TextView rewardName;
        ImageView rewardImage;
        Button btn;

        C2PViewHolder(View itemView) {
            super(itemView);

            Log.d("myTag", "rewardholder");
//            cv = (CardView) itemView.findViewById(R.id.cv);
            rewardName = (TextView) itemView.findViewById(R.id.reward_name);
            rewardImage = (ImageView) itemView.findViewById(R.id.reward_image);
            btn = (Button) itemView.findViewById(R.id.button3);
        }


    }

    List<Connect2People> rewards;


    C2PAdapter(Activity context, List<Connect2People> connect2People) {
        super();
        listContext = context;
        this.rewards = connect2People;
    }

    /**
     * RVAdapter(List<Reward> rewards){
     * this.rewards = rewards;
     * }
     **/

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    @Override
    public C2PViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(listContext).inflate(R.layout.fragment_connect2people, viewGroup, false);
        C2PViewHolder pvh = new C2PViewHolder(v);
        Log.d("pvh", pvh.toString());
        return pvh;
    }


    @Override
    public void onBindViewHolder(C2PViewHolder rewardViewHolder, int i) {
        rewardViewHolder.rewardName.setText(rewards.get(i).name);

        rewardViewHolder.rewardImage.setImageResource(rewards.get(i).image);

        rewardViewHolder.btn.setText(rewards.get(i).btn);

        rewardViewHolder.btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Profile p = Profile.getInstance();
                p.teamplay+=4;
                Intent intent = new Intent(listContext, MainBoard.class);
                intent.putExtra("QR_code", "meetHim");
                listContext.startActivity(intent);
            }
        });

        Log.d("myTag", "onbinder");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("myTag", "onAttached");
    }


}
