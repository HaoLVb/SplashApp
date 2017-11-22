package com.example.levanhao.splashapp.fragment.introsell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levanhao.splashapp.R;


/**
 * Created by vokhuyet on 13/07/2016.
 */
public class ViewPagerIntroSellFragment extends Fragment {
    public static ViewPagerIntroSellFragment newInstance(int position) {
        ViewPagerIntroSellFragment fragment = new ViewPagerIntroSellFragment();
        Bundle b = new Bundle();
        b.putInt("positionPage", position);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int positionPage = getArguments().getInt("positionPage");
        int layoutID = 0;
        switch (positionPage) {
            case 0:
                layoutID = R.layout.fragment_intro_sell_1;
                break;
            case 1:
                layoutID = R.layout.fragment_intro_sell_2;
                break;
            case 2:
                layoutID = R.layout.fragment_intro_sell_3;
                break;
        }
        return inflater.inflate(layoutID, container, false);
    }
}
