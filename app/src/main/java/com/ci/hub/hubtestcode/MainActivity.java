package com.ci.hub.hubtestcode;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends Activity {
    public static final String EXTRA_STUFF = "MainActivity.EXTRA_STUFF";
    public static final String TAG = "MainActivity";

    private static final int TAKE_PICTURE_REQUEST = 100;

    private Button mSaveImageButton;

    private OnClickListener mCaptureImageButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startImageCapture();
        }
    };

    private OnClickListener mSaveImageButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView mCameraImageView = (ImageView) findViewById(R.id.camera_image_view);

        findViewById(R.id.capture_image_button).setOnClickListener(mCaptureImageButtonClickListener);

        mSaveImageButton = (Button) findViewById(R.id.save_image_button);
        mSaveImageButton.setOnClickListener(mSaveImageButtonClickListener);
        mSaveImageButton.setEnabled(false);
    }

    private void startImageCapture() {
        //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE_REQUEST);
        //startActivityForResult(new Intent(MainActivity.this, CameraActivity.class), TAKE_PICTURE_REQUEST);

        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(EXTRA_STUFF, "IT WORKS!");
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == TAKE_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Recycle the previous bitmap.
                if (mCameraBitmap != null) {
                    mCameraBitmap.recycle();
                    mCameraBitmap = null;
                }
                Bundle extras = data.getExtras();
                mCameraBitmap = (Bitmap) extras.get("data");
                byte[] cameraData = extras.getByteArray(CameraActivity.EXTRA_CAMERA_DATA);
                if (cameraData != null) {
                    mCameraBitmap = BitmapFactory.decodeByteArray(cameraData, 0, cameraData.length);
                    mCameraImageView.setImageBitmap(mCameraBitmap);
                    mSaveImageButton.setEnabled(true);
                }
            } else {
                mCameraBitmap = null;
                mSaveImageButton.setEnabled(false);
            }
        }*/
        if (requestCode == TAKE_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                String returnData = data.getData().toString();
                Log.d(TAG, "The result was " + returnData);
                Log.d(TAG, "Image data: " + data.getExtras().getString(CameraActivity.IMAGE_DATA));
            }
        }
    }

    public void gotResponse(JSONObject data) {
        try {
            Log.d(TAG, data.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotSilentResponse(JSONObject data) {
        gotResponse(data);
    }
}
