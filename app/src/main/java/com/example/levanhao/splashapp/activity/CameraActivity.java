package com.example.levanhao.splashapp.activity;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
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

import java.io.IOException;
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
                if (!isReturn) {
                    Intent intent = new Intent(CameraActivity.this, SellProductActivity.class);
                    intent.putExtra(StaticVarriable.IMAGE, data);
                    startActivity(intent);
                } else {
//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra(StaticVarriable.IMAGE_RETURN, data);
//                    setResult(Activity.RESULT_OK, returnIntent);
//                    Log.e("12ss33_return", data.toString());
                    App.getInstance().setCapturedPhotoData(data);
                    setResult(RESULT_OK, new Intent());
                    finish();
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
}
