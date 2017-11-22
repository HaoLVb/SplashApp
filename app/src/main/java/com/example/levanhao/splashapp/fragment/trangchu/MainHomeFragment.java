package com.example.levanhao.splashapp.fragment.trangchu;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.activity.SellProductActivity;
import com.example.levanhao.splashapp.adapter.ViewPagerAdapter;
import com.example.levanhao.splashapp.fragment.BlankFragment;
import com.example.levanhao.splashapp.fragment.trangchu.image.ScrollImageFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.Timer;
import java.util.TimerTask;

public class MainHomeFragment extends Fragment {

    public MainHomeFragment() {
    }

    public static MainHomeFragment newInstance() {
        MainHomeFragment fragment = new MainHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHomeHandler = new MainHomeHandler();
        LoginActivity.systemManager.getHandlerManager().setMainHomeHandler(mainHomeHandler);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        this.homeTablayout = (TabLayout) view.findViewById(R.id.homeTablayout);
        this.hơmViewpager = (ViewPager) view.findViewById(R.id.hơmViewpager);
        this.imageViewPager = (ViewPager) view.findViewById(R.id.imageViewPager);
        this.imageTablayout = (TabLayout) view.findViewById(R.id.imageTablayout);
        setupViewPagerImage(imageViewPager);
        setupViewPager(hơmViewpager);
        this.homeTablayout.setupWithViewPager(hơmViewpager);
        this.imageTablayout.setupWithViewPager(imageViewPager);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                imageViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
        return view;
    }

    private static int currentPage = 0;
    private static int NUM_PAGES = 2;
    private View view;
    private TabLayout homeTablayout;
    private ViewPager hơmViewpager;
    private ViewPager imageViewPager;
    private TabLayout imageTablayout;
    private MainHomeHandler mainHomeHandler;


    private void setupViewPagerImage(ViewPager imageViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(ScrollImageFragment.newInstance(0), null);
        adapter.addFrag(ScrollImageFragment.newInstance(1), null);

        imageViewPager.setAdapter(adapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(AllFragment.newInstance(), getResources().getString(R.string.all));
        adapter.addFrag(KidEatFragment.newInstance(StaticVarriable.BE_AN), getResources().getString(R.string.kid_eat));
        adapter.addFrag(KidWearFragment.newInstance(), getResources().getString(R.string.kid_wear));
        adapter.addFrag(KidSleepFragment.newInstance(), getResources().getString(R.string.kid_sleep));
        adapter.addFrag(KidShowerFragment.newInstance(), getResources().getString(R.string.kid_bathe));
//        adapter.addFrag(BlankFragment.newInstance(), getResources().getString(R.string.kid_hygiene));
//        adapter.addFrag(BlankFragment.newInstance(), getResources().getString(R.string.strong_kid));
        viewPager.setAdapter(adapter);
    }

    private class MainHomeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            default:
                break;
            }
        }
    }


}
