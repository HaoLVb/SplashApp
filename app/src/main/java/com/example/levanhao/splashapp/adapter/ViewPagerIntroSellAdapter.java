package com.example.levanhao.splashapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.levanhao.splashapp.fragment.introsell.ViewPagerIntroSellFragment;


/**
 * Created by vokhuyet on 14/07/2016.
 */
public class ViewPagerIntroSellAdapter extends FragmentPagerAdapter {
    public ViewPagerIntroSellAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerIntroSellFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
