package com.teampietro.hero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teampietro.hero.helper.Credentials;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, parent, false);

    }

    View localview;
    ListView listView;
    UserTimeline userTimeline;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        localview = view;
        // View view = inflater.inflate(R.layout.fragment_feed, parent, false);

        listView = (ListView) view.findViewById(R.id.android_list);

        TwitterConfig config = new TwitterConfig.Builder(view.getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(Credentials.CONSUMERKEY, Credentials.CONSUMERSECRET))
                .debug(true)
                .build();

        Twitter.initialize(config);

        //UserTimeline userTimeline = new UserTimeline.Builder().screenName("twitterdev").build();

          userTimeline = new UserTimeline.Builder()
                .screenName("@realrobbe")//@Manuel_Neuer
                .build();
        userTimeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(localview.getContext())
                        .setTimeline(userTimeline)
                        .build();
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });


    }

    public void onResume() {
        super.onResume();
        Log.d("xD", "Resuming....");
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d("xD", "Hidden...." + hidden);

    }




}
