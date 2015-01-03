package com.ci.hub.hubtestcode;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Alex on 12/30/14.
 */
public class VideoUploader {
    private final String TAG = "VideoUploader";

    private Activity activity;
    private String serverUrl;

    public VideoUploader(String serverUrl, Activity activity) {
        this.activity = activity;
        this.serverUrl = serverUrl;
    }

    public void upload(Uri videoUri, Context context) throws UnsupportedEncodingException, ClientProtocolException, IOException {
        VideoUploaderTask task = new VideoUploaderTask();
        task.execute(videoUri, context);
    }

    private void taskUpload(Uri videoUri, Context context) throws UnsupportedEncodingException, ClientProtocolException, IOException {
        String videoPath = videoUri.getPath();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(serverUrl);

        FileBody filebodyVideo = new FileBody(new File(videoPath));
        StringBody title = new StringBody("Filename: " + videoPath);
        StringBody description = new StringBody("This is a video of the agent");

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("videoFile", filebodyVideo);
        reqEntity.addPart("title", title);
        reqEntity.addPart("description", description);
        httppost.setEntity(reqEntity);

        // DEBUG
        System.out.println("");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("#######################");
        System.out.println("");

        System.out.println("Executing request: " + httppost.getRequestLine( ) );
        HttpResponse response = httpclient.execute( httppost );
        HttpEntity resEntity = response.getEntity( );

        // DEBUG
        System.out.println( response.getStatusLine( ) );
        if (resEntity != null) {
            System.out.println( EntityUtils.toString(resEntity) );
        } // end if

        if (resEntity != null) {
            resEntity.consumeContent( );
        } // end if

        httpclient.getConnectionManager( ).shutdown( );
    }

    private void onProgress(JSONObject data) {
        ((Communicator) activity).gotResponse(data);
    }

    private void onFail(JSONObject data) {
        ((Communicator) activity).gotResponse(data);
    }

    private void onSuccess(JSONObject data) {
        ((Communicator) activity).gotResponse(data);
    }

    private class VideoUploaderTask extends AsyncTask<Object, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                VideoUploader.this.taskUpload((Uri) params[0], (Context) params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            try {
                onProgress(new JSONObject("{'status': 'progress' }"));
            } catch (Exception e) {
                e.printStackTrace();
            };
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == "SUCCESS") {
                try {
                    onSuccess(new JSONObject("{'status': 'success'}"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    onFail(new JSONObject("{'status': 'fail'}"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
