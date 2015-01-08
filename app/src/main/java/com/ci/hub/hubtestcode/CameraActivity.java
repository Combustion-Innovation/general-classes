package com.ci.hub.hubtestcode;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class CameraActivity extends Activity implements PictureCallback, SurfaceHolder.Callback {
    public static final String TAG = "CameraActivity";
    public static final String IMAGE_DATA = "com.ci.hub.IMAGE_DATA";
    public static final String IMAGE_TAKEN = "com.ci.hub.IMAGE_TAKEN";

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    private Intent returnData;

    private SurfaceHolder sHolder;

    private OnClickListener captureImageButtonOCL = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startCamera();
        }
    };

    private OnClickListener doneButtonOCL = new OnClickListener() {
        @Override
        public void onClick(View v) {
            done();
        }
    };

    private void startCamera() {
        camera.takePicture(null, this, null);
        Log.d(TAG, "startCamera");
    }

    private void done() {
        Log.d(TAG, "done");

        setResult(Activity.RESULT_OK, returnData);
        finish();
    }

    private void initCamera() {
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);

        camera = Camera.open(0);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        camera.startPreview();

        // Get a surface
        sHolder = surfaceView.getHolder();

        // add the callback interface methods defined below as the Surface
        // View
        // callbacks
        sHolder.addCallback(this);

        // tells Android that this surface will have its data constantly
        // replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent data = getIntent();
        String extra = data.getStringExtra(MainActivity.EXTRA_STUFF);
        setContentView(R.layout.activity_camera);

        returnData = new Intent("com.ci.hub.SQUARE_CAMERA_RESULT");
        returnData.putExtra(IMAGE_TAKEN, false);
        initCamera();
        findViewById(R.id.capture_image_button).setOnClickListener(captureImageButtonOCL);
        Log.d(TAG, "onCreate");
        Log.d(TAG, extra);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(TAG, "onPictureTaken: " + data.toString());

        returnData.putExtra(IMAGE_DATA, data);
        returnData.putExtra(IMAGE_TAKEN, true);
    }
}