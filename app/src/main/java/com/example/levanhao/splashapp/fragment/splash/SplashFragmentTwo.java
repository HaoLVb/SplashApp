package com.example.levanhao.splashapp.fragment.splash;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levanhao.splashapp.R;


public class SplashFragmentTwo extends Fragment {



	public SplashFragmentTwo() {
	}
	public static SplashFragmentTwo newInstance() {
		SplashFragmentTwo fragment = new SplashFragmentTwo();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_splash_two, container, false);
	}

}
