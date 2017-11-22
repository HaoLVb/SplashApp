package com.example.levanhao.splashapp.fragment.splash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.adapter.ViewPagerAdapter;

public class MainFragment extends Fragment implements View.OnClickListener {
	private Context mContext;

	public MainFragment() {
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();

	}

	public static MainFragment newInstance() {
		MainFragment fragment = new MainFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_main, container, false);
		findViews();
		splashViewPager.setOffscreenPageLimit(4);
		//Initializing the tablayout
		setupViewPager(splashViewPager);
		splashViewPager.setCurrentItem(0);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				splashViewPager.setCurrentItem(1, true);
			}
		}, 3000);

		splashTabLayout.setupWithViewPager(splashViewPager, true);
		splashViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					skipButton.setVisibility(View.GONE);
					mainLayout.setBackground(getResources().getDrawable(R.drawable.image_splash_1));
					break;
				case 1:
					skipButton.setVisibility(View.VISIBLE);
					skipButton.setText("Bỏ Qua");
					mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
					break;
				case 2:
					skipButton.setVisibility(View.VISIBLE);
					skipButton.setText("Bỏ Qua");
					mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
					break;
				case 3:
					skipButton.setVisibility(View.VISIBLE);
					skipButton.setText("Băt đầu");
					mainLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		return view;
	}

	private void setupViewPager(ViewPager viewPager) {
		pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
		pagerAdapter.addFrag(SplashFragmentOne.newInstance(), null);
		pagerAdapter.addFrag(SplashFragmentTwo.newInstance(), null);
		pagerAdapter.addFrag(SplashFragmentThree.newInstance(), null);
		pagerAdapter.addFrag(SplashFragmentFour.newInstance(), null);
		viewPager.setAdapter(pagerAdapter);
	}

	private void findViews() {
		skipButton = (Button) view.findViewById(R.id.skipButton);
		skipButton.setVisibility(View.GONE);
		skipButton.setOnClickListener(this);
		splashViewPager = (ViewPager) view.findViewById(R.id.splashViewPager);
		splashTabLayout = (TabLayout) view.findViewById(R.id.splashTabLayout);
		mainLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
	}

	private View view;
	private ViewPager splashViewPager;
	private TabLayout splashTabLayout;
	private Button skipButton;
	private ViewPagerAdapter pagerAdapter;
	private RelativeLayout mainLayout;

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);

	}
}
