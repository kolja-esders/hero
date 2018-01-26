package com.teampietro.hero;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.teampietro.hero.models.Profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.teampietro.hero.helper.APIClient.BASE_URL;

public class MotivationActivity extends Activity {

    CallbackManager callbackManager;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public MotivationActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.fragment_facebook_share);

        final Activity act = this;
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    if (CameraPermissionHelper.hasCameraPermission(act)) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    } else {
                        CameraPermissionHelper.requestCameraPermission(act);
                    }


                }
            }
        });
        **/
    }
/**
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap rawImage = (Bitmap) extras.get("data");

            Bitmap[] bAr = new Bitmap[]{rawImage};
            new DownloadFilesTask().execute(bAr);


            // this.sendImage(rawImage, "000");


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


/**
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
                    //Intent intent = new Intent( this, MainBoard.class);
                    //startActivity(intent);
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
**/


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        Profile p = Profile.getInstance();
        p.motivation+=1;
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra("Motivation", "empty");
        startActivity(intent);

    }
}
