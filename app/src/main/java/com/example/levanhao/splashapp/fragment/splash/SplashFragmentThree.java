package com.example.levanhao.splashapp.fragment.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levanhao.splashapp.R;


public class SplashFragmentThree extends Fragment {

	public SplashFragmentThree() {
		// Required empty public constructor
	}


	public static SplashFragmentThree newInstance() {
		SplashFragmentThree fragment = new SplashFragmentThree();

		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_splash_three, container, false);
	}


}
