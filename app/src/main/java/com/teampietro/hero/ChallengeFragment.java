package com.teampietro.hero;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.teampietro.hero.helper.ListViewAdapter;
import com.teampietro.hero.models.Profile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import static com.teampietro.hero.helper.Constants.FIRST_COLUMN;
import static com.teampietro.hero.helper.Constants.FOURTH_COLUMN;
import static com.teampietro.hero.helper.Constants.SECOND_COLUMN;
import static com.teampietro.hero.helper.Constants.THIRD_COLUMN;


public class ChallengeFragment extends Fragment {
    private ArrayList<HashMap<String, String>> list;

    private ListViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateFromProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, parent, false);

        ListView listView = view.findViewById(R.id.listView1);


        View footer = inflater.inflate(R.layout.challenge_footer, null);
        listView.addFooterView(footer);


        Button btn = footer.findViewById(R.id.claimBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RewardFragment.class);
                startActivity(intent);
            }
        });

        list = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "endurance");
        temp.put(SECOND_COLUMN, "Endurance");
        temp.put(THIRD_COLUMN, "89");
        temp.put(FOURTH_COLUMN, "Track steps");
        list.add(temp);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "motivation");
        temp2.put(SECOND_COLUMN, "Motivation");
        temp2.put(THIRD_COLUMN, "69");
        temp2.put(FOURTH_COLUMN, "Share picture");
        list.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "style");
        temp3.put(SECOND_COLUMN, "Style");
        temp3.put(THIRD_COLUMN, "99");
        temp3.put(FOURTH_COLUMN, "Register purchase");
        list.add(temp3);

        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put(FIRST_COLUMN, "teamplay");
        temp4.put(SECOND_COLUMN, "Team Play");
        temp4.put(THIRD_COLUMN, "61");
        temp4.put(FOURTH_COLUMN, "Meet up");
        list.add(temp4);

        HashMap<String, String> temp5 = new HashMap<String, String>();
        temp5.put(FIRST_COLUMN, "tactics");
        temp5.put(SECOND_COLUMN, "Tactics");
        temp5.put(THIRD_COLUMN, "40");
        temp5.put(FOURTH_COLUMN, "Solve quiz");
        list.add(temp5);


        adapter = new ListViewAdapter(getActivity(),list, getContext());

        listView.setAdapter(adapter);

        updateFromProfile();

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileUpdatedEvent(Profile.ProfileUpdatedEvent e) {
        updateFromProfile();
    }

    private void updateFromProfile() {
        for (HashMap<String, String> l : list) {
            String skill = l.get(FIRST_COLUMN);

            if (skill.equals("tactics")) {
                l.put(THIRD_COLUMN, String.valueOf(Profile.getInstance().tactics));
            } else if (skill.equals("motivation")) {
                l.put(THIRD_COLUMN, String.valueOf(Profile.getInstance().motivation));
            } else if (skill.equals("teamplay")) {
                l.put(THIRD_COLUMN, String.valueOf(Profile.getInstance().teamplay));
            } else if (skill.equals("style")) {
                l.put(THIRD_COLUMN, String.valueOf(Profile.getInstance().style));
            } else if (skill.equals("endurance")) {
                l.put(THIRD_COLUMN, String.valueOf(Profile.getInstance().endurance));
            }
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}

