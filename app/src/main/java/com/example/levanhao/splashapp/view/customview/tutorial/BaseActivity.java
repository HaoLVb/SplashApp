package com.example.levanhao.splashapp.view.customview.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.levanhao.splashapp.R;


/**
 * Created by HoangNV on 11/16/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public void startActivityFromRight(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_fix);
    }

    public void startActivityFromRight(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.left_in, R.anim.left_fix);
    }

}
