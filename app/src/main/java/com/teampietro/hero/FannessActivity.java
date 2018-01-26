package com.teampietro.hero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teampietro.hero.models.Profile;

import java.util.HashMap;
import java.util.Map;

public class FannessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanness);

        Bundle extras = getIntent().getExtras();



        Bitmap img = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);




        String msg = extras.getString("response");


        while (msg==null){
            try{

                Thread.sleep(200);
            }
            catch(Exception e){
                Log.e("error",e.getMessage());
            }
        }
        Log.e("ssd",msg);

         



        Map<String, Integer> map = new Gson().fromJson(msg, new TypeToken<HashMap<String, Integer>>() {}.getType());
        Log.e("",map.toString());
        ImageView picture = findViewById(R.id.image_share);

        TextView faces = findViewById(R.id.faces);
        TextView redness = findViewById(R.id.redness);
        TextView happiness = findViewById(R.id.happiness);
        TextView score = findViewById(R.id.score);

        try {
            picture.setImageBitmap(img);
            //Social
            faces.setText(map.get("faces").toString());
            //FCB
            redness.setText(map.get("redness").toString());
            //Emotion
            happiness.setText(map.get("happiness").toString());
            //Score
            score.setText(map.get("score").toString());

        } catch (Exception e){
            Log.e( "xxx", "error" );
            faces.setText("1");
            //FCB
            redness.setText("2");
            //Emotion
            happiness.setText("3");
            //Score
            score.setText("5");
        }

        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( FannessActivity.this, MainBoard.class);

                Profile p = Profile.getInstance();
                p.motivation+=6;

                intent.putExtra("FannesActivity", "an Entry");
                startActivity(intent);

            }
        });

    }
}
