package com.teampietro.hero;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teampietro.hero.models.Profile;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MatchFragment extends Fragment {


    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        TextView post_sim = view.findViewById(R.id.postsim);
        TextView face_sim = view.findViewById(R.id.face_sim);

        NumberFormat formatter = new DecimalFormat("#0.00");


        post_sim.setText(formatter.format(Profile.getInstance().starPostSimilarity) + "/ 10.00");
        face_sim.setText(formatter.format(Profile.getInstance().starFaceSimilarity) + "/ 10.00");


        Button btn = view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), MainBoard.class);
                startActivity(intent);

                /**
                MainBoard fragment = new MainBoard();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment, "MAINBOARD_TAG");
                transaction.commit();
                 **/
            }
        });
        return view;
    }

    public void change(View v){

        TextView tv = (TextView) v.findViewById(R.id.postsim);
        tv.setText("XYZ");
    }
}
