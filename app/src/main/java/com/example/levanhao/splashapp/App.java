package com.example.levanhao.splashapp;

import android.app.Application;

/**
 * Created by HaoLV on 11/21/2017.
 */

public final class App extends Application {
    private static App sInstance;
    private byte[] mCapturedPhotoData;

    // Getters & Setters
    public byte[] getCapturedPhotoData() {
        return mCapturedPhotoData;
    }

    public void setCapturedPhotoData(byte[] capturedPhotoData) {
        mCapturedPhotoData = capturedPhotoData;
    }

    // Singleton code
    public static App getInstance() { return sInstance; }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
