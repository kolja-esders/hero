package com.teampietro.hero;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.Downloader;

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

import static com.facebook.FacebookSdk.getCacheDir;
import static com.teampietro.hero.helper.APIClient.BASE_URL;

public class DownloadFilesTask extends AsyncTask<Bitmap, Long, Long> {

    public MainBoard act;

    public DownloadFilesTask(MainBoard mb){
        this.act = mb;
    }


    String msg = "";

    protected Long doInBackground(Bitmap[] bm) {

        msg = sendImage(bm[0], "xD");
        return 0L;
    }


    protected void onPostExecute(Long result) {
        Log.e("xP", msg);
        act.setResponse(msg);
        //set stuff in activity
    }



    private String sendImage(Bitmap bitmap, String id) {
        final String ENDPOINT_IMAGE = BASE_URL + "/msm_image/";
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        URL url = null;
        try {
            url = new URL(ENDPOINT_IMAGE);
        } catch (Exception e) {
            // Ignore
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        long timeNow = System.currentTimeMillis();
        String filename = timeNow + ".png";
        FileOutputStream outputStream;
        File file = null;
        try {
            file = File.createTempFile(filename, null, getCacheDir());
            outputStream = new FileOutputStream(file);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody req = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", id)
                .addFormDataPart("file", filename, RequestBody.create(MEDIA_TYPE_PNG, file)).build();
        Request request = new Request.Builder()
                .url(url)
                .post(req)
                .build();
        OkHttpClient client = new OkHttpClient();
        String responseStr = "";
        try {
            Response response = client.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("[sendImage]", "Response: " + responseStr);

        return responseStr;

    }

}
