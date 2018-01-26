//package com.teampietro.hero;
//
//
//import android.app.Activity;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RewardViewHolder>{
//
//    private Activity listContext;
//
//    public static class RewardViewHolder extends RecyclerView.ViewHolder {
//        CardView cv;
//        TextView rewardName;
//        ImageView rewardImage;
//
//        RewardViewHolder(View itemView) {
//            super(itemView);
//
//            Log.d("myTag", "rewardholder");
//            cv = (CardView)itemView.findViewById(R.id.cv);
//            rewardName = (TextView)itemView.findViewById(R.id.rewardtext);
//            rewardImage = (ImageView)itemView.findViewById(R.id.reward_image);
//        }
//    }
//    List<Reward> rewards;
//
//
//    RVAdapter(Activity context, List<Reward> rewards){
//        super();
//        listContext = context;
//        this.rewards = rewards;
//    }
//
//    /**
//    RVAdapter(List<Reward> rewards){
//        this.rewards = rewards;
//    }
//    **/
//
//    @Override
//    public int getItemCount() {
//        return rewards.size();
//    }
//
//    @Override
//    public RewardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(listContext).inflate(R.layout.fragment_reward, viewGroup, false);
//        RewardViewHolder pvh = new RewardViewHolder(v);
//        Log.d("pvh", pvh.toString());
//        return pvh;
//    }
//
//
//    @Override
//    public void onBindViewHolder(RewardViewHolder rewardViewHolder, int i) {
//        rewardViewHolder.rewardName.setText(rewards.get(i).name);
//
//        rewardViewHolder.rewardImage.setImageResource(rewards.get(i).image);
//        Log.d("myTag", "onbinder");
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        Log.d("myTag", "onAttached");
//    }
//
//
//}
