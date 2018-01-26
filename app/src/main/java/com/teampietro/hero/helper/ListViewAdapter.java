package com.teampietro.hero.helper;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.teampietro.hero.helper.Constants.FIRST_COLUMN;
import static com.teampietro.hero.helper.Constants.FOURTH_COLUMN;
import static com.teampietro.hero.helper.Constants.SECOND_COLUMN;
import static com.teampietro.hero.helper.Constants.THIRD_COLUMN;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import com.teampietro.hero.ChallengeFragment;
import com.teampietro.hero.Connect2PeopleFragment;
import com.teampietro.hero.ConnectFragment;
import com.teampietro.hero.MainBoard;

import com.teampietro.hero.BarcodeCaptureActivity;
import com.teampietro.hero.CameraPermissionHelper;
import com.teampietro.hero.DownloadFilesTask;
import com.teampietro.hero.MainBoard;
import com.teampietro.hero.MatchFragment;
import com.teampietro.hero.MotivationActivity;
import com.teampietro.hero.QRCodeActivity;
import com.teampietro.hero.R;
import com.teampietro.hero.StepFragment;
import com.teampietro.hero.models.Profile;

public class ListViewAdapter extends BaseAdapter {

    private static final int RC_BARCODE_CAPTURE = 9001;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageView skillImage;
    TextView skill;
    TextView skillPoints;
    Button improveScore;
    String tmpSkill;
    Context mContext;

    public ListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list, Context context) {
        super();
        this.activity = activity;
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.challenge_entry_line, null);
        }

        skillImage = convertView.findViewById(R.id.coverImage);
        skill = convertView.findViewById(R.id.skill);
        skillPoints = convertView.findViewById(R.id.points);
        improveScore = convertView.findViewById(R.id.improveScore);

        HashMap<String, String> map = list.get(position);
        String skillName = map.get(FIRST_COLUMN);

        /*switch (skillName) {
            case "tactics":
                skillImage.setImageResource(R.drawable.tactics);
                skill.setText("Tactics");
                break;
            case "motivation":
                skillImage.setImageResource(R.drawable.motivation);
                skill.setText("Motivation");
                break;
            case "endurance":
                skillImage.setImageResource(R.drawable.endurance);
                skill.setText("Endurance");
                break;
            case "style":
                skillImage.setImageResource(R.drawable.style);
                skill.setText("Style");
                break;
            case "teamplay":
                skillImage.setImageResource(R.drawable.teamplay);
                skill.setText("Team play");
                break;
        }*/

        //InputStream is = getClass().getResourceAsStream("/drawable/" + map.get(FIRST_COLUMN));
        //skillImage.setImageDrawable(Drawable.createFromStream(is, ""));

        int id = convertView.getResources().getIdentifier(map.get(FIRST_COLUMN), "drawable", convertView.getContext().getPackageName());
        skillImage.setImageResource(id);
        //skillImage.setBackgroundResource(id);

        skill.setText(map.get(SECOND_COLUMN));
        skillPoints.setText(map.get(THIRD_COLUMN));

        improveScore.setText(map.get(FOURTH_COLUMN));

        tmpSkill = map.get(FIRST_COLUMN);

        if (tmpSkill.equals("tactics")) {
            improveScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        updateTactics();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (tmpSkill.equals("motivation")) {
            improveScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        updateMotivation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (tmpSkill.equals("teamplay")) {
            improveScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        updateTeamPlay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (tmpSkill.equals("style")) {
            improveScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        updateStyle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (tmpSkill.equals("endurance")) {
            improveScore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        updateEndurance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        return convertView;
    }

    private void updateTactics() {

        Toast.makeText(getApplicationContext(), "You solved the quiz.", Toast.LENGTH_SHORT).show();
        Profile p = Profile.getInstance();
        p.tactics+=3;
        //TODO: static
        Intent intent = new Intent(activity, MainBoard.class);
        intent.putExtra("QR_code", "test");
        activity.startActivity(intent);

    }

    public interface IMethodCaller{
        void findViewById();
    }

    private void updateMotivation() {

        System.out.println("Motivation");
        ((MainBoard)mContext).onHandleSelection();
        /**
        CallbackManager callbackManager = CallbackManager.Factory.create();

        ((MainBoard)mContext).setContentView(R.layout.fragment_facebook_share);

        final Activity act = ((MainBoard)mContext);
        ((MainBoard)mContext).findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

**/

        //Intent intent = new Intent(activity, MotivationActivity.class);
        //((MainBoard) mContext).startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);



        //activity.startActivity(intent);

    }

    private void updateTeamPlay() {
        Intent intent = new Intent(activity, ConnectFragment.class);
        activity.startActivity(intent);
    }

    private void updateStyle() {
        Intent intent = new Intent(activity, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

        activity.startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    private void updateEndurance() {

        Intent intent = new Intent(activity, StepFragment.class);
        activity.startActivity(intent);
        System.out.println("Step");

    }

}
