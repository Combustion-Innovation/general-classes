package com.ci.hub.hubtestcode;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alexbbb.uploadservice.AbstractUploadServiceReceiver;
import com.alexbbb.uploadservice.UploadRequest;
import com.alexbbb.uploadservice.UploadService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Alex on 12/28/14.
 */
public class ImageUploader {
    private final String TAG = "ImageUploader";

    private Activity activity;
    private String serverUrl;

    private final AbstractUploadServiceReceiver uploadReceiver;

    public ImageUploader(String serverUrl, Activity activity) {
        this.activity = activity;
        this.serverUrl = serverUrl;

        uploadReceiver = new AbstractUploadServiceReceiver() {

            @Override
            public void onProgress(String uploadId, int progress) {
                Log.i(TAG, "The progress of the upload with ID "
                        + uploadId + " is: " + progress);
                JSONObject data = new JSONObject();
                try {
                    data.put("progress", progress);
                    data.put("uploadID", uploadId);
                    data.put("status", "progress");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageUploader.this.onProgress(data);
            }

            @Override
            public void onError(String uploadId, Exception exception) {
                Log.e(TAG, "Error in upload with ID: " + uploadId + ". "
                        + exception.getLocalizedMessage(), exception);
                JSONObject data = new JSONObject();
                try {
                    data.put("exception", exception);
                    data.put("uploadID", uploadId);
                    data.put("status", "fail");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageUploader.this.onFail(data);
            }

            @Override
            public void onCompleted(String uploadId,
                                    int serverResponseCode,
                                    String serverResponseMessage) {
                Log.i(TAG, "Upload with ID " + uploadId
                        + " is completed: " + serverResponseCode
                        + ", " + serverResponseMessage);
                JSONObject data = new JSONObject();
                try {
                    data.put("serverResponseCode", serverResponseCode);
                    data.put("serverResponseMessage", serverResponseMessage);
                    data.put("uploadID", uploadId);
                    data.put("status", "success");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageUploader.this.onSuccess(data);
            }
        };
    }

    public void upload(Uri imageUri, final Context context) {
        String imageName = imageUri.getPath() + "_" + new Date().getTime();
        final UploadRequest request = new UploadRequest(context,
                imageName,
                serverUrl);

    /*
     * parameter-name: is the name of the parameter that will contain file's data.
     * Pass "uploaded_file" if you're using the test PHP script
     *
     * custom-file-name.extension: is the file name seen by the server.
     * E.g. value of $_FILES["uploaded_file"]["name"] of the test PHP script
     */
        request.addFileToUpload(imageUri.getPath(),
                "uploaded_file",
                "file",
                "image/*");

        //configure the notification
        request.setNotificationConfig(android.R.drawable.ic_menu_upload,
                "notification title",
                "upload in progress text",
                "upload completed successfully text",
                "upload error text",
                false);

        try {
            //Start upload service and display the notification
            Log.d(TAG, "Will this work?");
            UploadService.startUpload(request);

        } catch (Exception exc) {
            //You will end up here only if you pass an incomplete UploadRequest
            Log.e("AndroidUploadService", exc.getLocalizedMessage(), exc);
        }
    }

    public AbstractUploadServiceReceiver getUploadReceiver() {
        return uploadReceiver;
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
}
