package com.teampietro.hero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.teampietro.hero.models.Profile;


public class StepFragment extends Activity {

    public static boolean gameOver = false;
    public static long steps = -1;
    public static long stepsTarget = -1;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    0x1001,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        }

        subscribe();

        readStepData();

        setContentView(R.layout.fragment_step);



    }

    public void onResume() {
        super.onResume();
        this.updateSteps();

        new StepThread().execute("");

    }


    public boolean updateSteps(){

        readStepData();

        Log.w("Step", "Total steps: " +  steps);
        Log.w("Step", "Target steps: " +  stepsTarget);

        TextView sc = (TextView) findViewById(R.id.textView3);

        if( steps <= 0 &&  stepsTarget <= 0){

        }
        else if( steps > 0 &&  stepsTarget == -1 ){
             stepsTarget = (( steps + 15) / 5 ) * 5;
            sc.setText( steps + " / " +  stepsTarget);
        }
        else {


            if ( steps >  stepsTarget) {
                sc.setText( stepsTarget + " / " +  stepsTarget);
            } else {
                sc.setText( steps + " / " +  stepsTarget);
            }

            if ( steps >=  stepsTarget) {

                ImageView ic = (ImageView) findViewById(R.id.imageView2);
                ic.setImageResource(R.drawable.success);



                Button btn = (Button) findViewById(R.id.claimPoints);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainBoard.class);
                        intent.putExtra("Endurance", "empty");
                        Profile.getInstance().endurance += 4;
                        startActivity(intent);
                    }
                });




                gameOver = true;

            }

        }

        return false;


    }



    /** Records step data by requesting a subscription to background step data. */
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i("Step", "Successfully subscribed!");
                                } else {
                                    Log.w("Step", "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */
    public void readStepData() {

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                 steps =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                Log.i("Step", "Total steps: " +  steps);


                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Step", "There was a problem getting the step count.", e);
                            }
                        });
    }


    private class StepThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            for(int i=0; i < 10000; i++) {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateSteps();}});
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }

                if(gameOver){
                    break;
                }

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateSteps();}});

            Log.w("Step", "Game Over !!!!!");

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    public void onBackPressed() {
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra("Step", "empty");
        Profile p = Profile.getInstance();
        p.endurance+=4;
        startActivity(intent);

    }

}
