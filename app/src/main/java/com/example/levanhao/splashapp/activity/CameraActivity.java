package com.example.levanhao.splashapp.activity;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.os.Environment;
import android.view.SurfaceHolder.Callback;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


import com.example.levanhao.splashapp.App;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CameraActivity extends Activity implements Callback, View.OnClickListener {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private ImageButton imageBtTakeVideo;
    private ImageButton imageBtTakePhoto;
    private Button btCameraPhoto;
    private Button btCameraVideo;
    private Button btFlashCamera;
    private Button btRotateCamera;
    private Button btCloseCamera;
    private Button btLibCamera;
    private Switch switchCamera;
    private int cameraId;
    private boolean flashmode = false;
    private int rotation;
    private boolean isReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        isReturn = getIntent().getBooleanExtra(StaticVarriable.CAMERA_ACTIVITY, false);
        cameraId = CameraInfo.CAMERA_FACING_BACK;
        initView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Camera.getNumberOfCameras() > 1) {
            btRotateCamera.setVisibility(View.VISIBLE);
        }
        if (!getBaseContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
            btFlashCamera.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btFlashCamera:
            flashOnButton();
            break;
        case R.id.btRotateCamera:
            flipCamera();
            break;
        case R.id.imageBtTakePhoto:
            takeImage();
            break;
        case R.id.btCloseCamera:
            this.finish();
        default:
            break;
        }
    }

    private void takeImage() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (file == null) {
                    Log.e("nullss", "null");
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.close();

                } catch (FileNotFoundException e) {
                    Log.d("LOI", "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("LOI", "Error accessing file: " + e.getMessage());
                }
                if (!isReturn) {
                    Intent intent = new Intent(CameraActivity.this, SellProductActivity.class);
                    intent.putExtra("image", file);
                    startActivity(intent);
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(StaticVarriable.IMAGE_RETURN, file);
                    setResult(Activity.RESULT_OK, returnIntent);
                }
                finish();
            }
        });
    }

    private void initView() {
        imageBtTakeVideo = (ImageButton) findViewById(R.id.imageBtTakeVideo);
        imageBtTakePhoto = (ImageButton) findViewById(R.id.imageBtTakePhoto);
        btCameraPhoto = (Button) findViewById(R.id.btCameraPhoto);
        btCameraVideo = (Button) findViewById(R.id.btCameraVideo);
        btFlashCamera = (Button) findViewById(R.id.btFlashCamera);
        btCloseCamera = (Button) findViewById(R.id.btCloseCamera);
        btRotateCamera = (Button) findViewById(R.id.btRotateCamera);
        btLibCamera = (Button) findViewById(R.id.btLibCamera);
        switchCamera = (Switch) findViewById(R.id.switchCamera);
        imageBtTakeVideo.setOnClickListener(this);
        imageBtTakePhoto.setOnClickListener(this);
        btCameraVideo.setOnClickListener(this);
        btCameraPhoto.setOnClickListener(this);
        btLibCamera.setOnClickListener(this);
        btCloseCamera.setOnClickListener(this);
        btRotateCamera.setOnClickListener(this);
        btFlashCamera.setOnClickListener(this);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!openCamera(CameraInfo.CAMERA_FACING_BACK)) {
            Toast.makeText(this, "Không thể mở camera ", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean openCamera(int id) {
        boolean result = false;
        cameraId = id;
        releaseCamera();
        try {
            camera = Camera.open(cameraId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (camera != null) {
            try {
                setUpCamera(camera);
                camera.setErrorCallback(new Camera.ErrorCallback() {

                    @Override
                    public void onError(int error, Camera camera) {

                    }
                });
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                releaseCamera();
            }
        }
        return result;
    }

    private void setUpCamera(Camera c) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
        case Surface.ROTATION_0:
            degree = 0;
            break;
        case Surface.ROTATION_90:
            degree = 90;
            break;
        case Surface.ROTATION_180:
            degree = 180;
            break;
        case Surface.ROTATION_270:
            degree = 270;
            break;

        default:
            break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // frontFacing
            rotation = (info.orientation + degree) % 330;
            rotation = (360 - rotation) % 360;
        } else {
            // Back-facing
            rotation = (info.orientation - degree + 360) % 360;
        }
        c.setDisplayOrientation(rotation);
        Camera.Parameters params = c.getParameters();

        showFlashButton(params);

        List<String> focusModes = params.getSupportedFlashModes();
        if (focusModes != null) {
            if (focusModes
                    .contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                params.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        }

        params.setRotation(rotation);
    }

    private void showFlashButton(Camera.Parameters params) {
        boolean showFlash = (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH) && params.getFlashMode() != null)
                && params.getSupportedFlashModes() != null
                && params.getSupportedFocusModes().size() > 1;

        btFlashCamera.setVisibility(showFlash ? View.VISIBLE
                : View.INVISIBLE);

    }

    private void releaseCamera() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void flipCamera() {
        int id = (cameraId == CameraInfo.CAMERA_FACING_BACK ? CameraInfo.CAMERA_FACING_FRONT
                : CameraInfo.CAMERA_FACING_BACK);
        if (!openCamera(id)) {
            Toast.makeText(this, "Không thể mở camera ", Toast.LENGTH_SHORT).show();
        }
    }


    private void flashOnButton() {
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Camera.Parameters.FLASH_MODE_TORCH
                        : Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
}
