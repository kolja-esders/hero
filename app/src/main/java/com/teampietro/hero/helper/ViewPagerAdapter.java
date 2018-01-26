package com.teampietro.hero.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teampietro.hero.ChallengeFragment;
import com.teampietro.hero.FeedFragment;
import com.teampietro.hero.RankingFragment;
import com.teampietro.hero.RewardFragment;


/**
 * Created by kolja on 21.01.18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FeedFragment();
        } else if (position == 1) {
            return new ChallengeFragment();
        } else if (position == 2) {
            return new RankingFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
