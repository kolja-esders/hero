package com.teampietro.hero;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.teampietro.hero.helper.ViewPagerAdapter;
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

import java.io.ByteArrayOutputStream;

public class MainBoard extends AppCompatActivity {

    private TextView tvCurrentScore;
    private ImageView ivScoreChange;
    private ViewPagerAdapter adapter;
    private MenuItem prevMenuItem = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    CallbackManager callbackManager;
    String response;
    static boolean hadNotification = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            int currentItem = 0;

            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    currentItem = 0;
                    break;
                case R.id.navigation_challenge:
                    currentItem = 1;
                    break;
                case R.id.navigation_ranking:
                    currentItem = 2;
                    break;
            }
            ((ViewPager) findViewById(R.id.viewpager)).setCurrentItem(currentItem);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);


        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();


        /*findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
            }
        });*/

        updateValues();
        registerChannel();

        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.size()>1) {
            viewPager.setCurrentItem(1);
            for (int i = 0; i < 3; ++i) {
                navigation.getMenu().getItem(i).setChecked(false);
            }
            navigation.getMenu().getItem(1).setChecked(true);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    for (int i = 0; i < 3; ++i) {
                        navigation.getMenu().getItem(i).setChecked(false);
                    }
                }

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.profileImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HelloArActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerChannel() {

        //Get an instance of NotificationManager//

        try {

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // The id of the channel.
            String id = "my_channel_01";
            // The user-visible name of the channel.
            CharSequence name = "xd Not";
            // The user-visible description of the channel.
            String description = "best not channel ever";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(id, name, importance);

                // Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
                // Sets the notification light color for notifications posted to this
                // channel, if the device supports this feature.
                mChannel.enableVibration(true);
                mNotificationManager.createNotificationChannel(mChannel);

            }

        }
        catch (Exception x){}

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void updateValues() {

        tvCurrentScore = (TextView) findViewById(R.id.currentScore);
        //ivScoreChange = (ImageView) findViewById(R.id.scoreChangeInThePast);

        double newScore = Profile.getInstance().tactics + Profile.getInstance().motivation + Profile.getInstance().teamplay + Profile.getInstance().style + Profile.getInstance().endurance;

        tvCurrentScore.setText(((int)newScore) + "");
        //tvCurrentScore.setText("87");
        //ivScoreChange.setImageResource(R.drawable.arrow_right);

        if(!MainBoard.hadNotification && newScore >= 6+5+4){
            MainBoard.hadNotification = true;
            sendNotification();
        }

    }

    public void sendNotification() {

        try {

            // The id of the channel.
            String CHANNEL_ID = "my_channel_01";
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Exclusive Reward")
                            .setContentText("Get your exclusive FC Bayer reward!");
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, RewardFragment.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your app to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainBoard.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // mNotificationId is a unique integer your app uses to identify the
            // notification. For example, to cancel the notification, you can pass its ID
            // number to NotificationManager.cancel().
            mNotificationManager.notify(54, mBuilder.build());

        }
        catch (Exception x){}
    }

    public void setResponse(String resp){
        response = resp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap rawImage = (Bitmap) extras.get("data");

            Bitmap[] bAr = new Bitmap[]{rawImage};
            DownloadFilesTask loader = new DownloadFilesTask(this);
            loader.execute(bAr);




            /*Uri capturedImageUri = (android.net.Uri) extras.get("data");

            String selectedImagePath = getRealPathFromURIPath(capturedImageUri, getActivity());
            Bitmap image = null;
            try {
                ExifInterface exifObject = new ExifInterface(selectedImagePath);
                int orientation = exifObject.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                image = rotateBitmap(rawImage, orientation);
            } catch (IOException e) {
                e.printStackTrace();
            }*/



            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(rawImage)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .setShareHashtag(new ShareHashtag.Builder()
                            .setHashtag("#MiaSanMia")
                            .build())
                    .build();

            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Log.d("FB_SHARE", "Post was successfully created: " + result.toString());
                    Intent intent = new Intent( MainBoard.this, FannessActivity.class);
                    intent.putExtra("response", response);
                    ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                    rawImage.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                    intent.putExtra("byteArray", _bs.toByteArray());
                    startActivity(intent);
                }

                @Override
                public void onCancel() {
                    Log.d("FB_SHARE", "CANCEL");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("FB_SHARE", "ERROR");
                }
            });
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        }


    }
    final Activity act = this;

    public void onHandleSelection() {



        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            if (CameraPermissionHelper.hasCameraPermission(act)) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            } else {
                CameraPermissionHelper.requestCameraPermission(act);
            }
        }

        // ... Start a new Activity here and pass the values
        //Intent secondActivity = new Intent(MainBoard.this, MainBoard.class);

        //startActivityForResult(secondActivity, REQUEST_IMAGE_CAPTURE);
    }


}
