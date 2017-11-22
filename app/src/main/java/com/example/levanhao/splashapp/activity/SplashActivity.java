package com.example.levanhao.splashapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.fragment.splash.MainFragment;
import com.example.levanhao.splashapp.manage.FragmentController;


public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		FragmentController.addFragment(getSupportFragmentManager(), MainFragment.newInstance(), R.id.splashContainer);
		SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
		if (pref.getBoolean("activity_executed", false)) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else {
			SharedPreferences.Editor ed = pref.edit();
			ed.putBoolean("activity_executed", true);
			ed.commit();
		}
	}


}
