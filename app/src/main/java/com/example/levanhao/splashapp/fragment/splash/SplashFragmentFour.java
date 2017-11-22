package com.example.levanhao.splashapp.fragment.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.levanhao.splashapp.R;

public class SplashFragmentFour extends Fragment implements Animation.AnimationListener {

	public SplashFragmentFour() {
		// Required empty public constructor
	}

	public static SplashFragmentFour newInstance() {
		SplashFragmentFour fragment = new SplashFragmentFour();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_splash_four, container, false);
		animMoveToTop = AnimationUtils.loadAnimation(context, R.anim.move);

		// set animation listener
		animMoveToTop.setAnimationListener(this);
		this.imageView = (ImageView) view.findViewById(R.id.imageView);
//		this.imageView2 = (ImageView) view.findViewById(R.id.imageView2);
		//this.imageView.setAnimation(animMoveToTop);
		//this.imageView.startAnimation(animMoveToTop);
		return view;
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
//		this.imageView2.startAnimation(animMoveToTop);
	}

	private Animation animMoveToTop;
	private Context context;
	private ImageView imageView;
	private ImageView imageView2;
	private View view;
}
