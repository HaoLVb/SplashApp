package com.example.levanhao.splashapp.fragment.trangchu.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.activity.CampaignActivity;


public class ScrollImageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public ScrollImageFragment() {
        // Required empty public constructor
    }


    public static ScrollImageFragment newInstance(int param1) {
        ScrollImageFragment fragment = new ScrollImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scroll_image, container, false);
        this.imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (mParam1 == 0) {
            Glide.with(getActivity()).load("https://moki.vn//files/banner/banner_1497926374_35.png").into(imageView);
        } else {
            Glide.with(getActivity()).load("https://moki.vn/files/banner/banner_1510908466_42.jpg").into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CampaignActivity.class);
                if (mParam1 == 0) {
                    intent.putExtra(CampaignActivity.CAMPAIGN, 1);
                    intent.putExtra(CampaignActivity.IMAGE_URL, "https://moki.vn//files/banner/banner_1497926374_35.png");
                } else {
                    intent.putExtra(CampaignActivity.CAMPAIGN, 2);
                    intent.putExtra(CampaignActivity.IMAGE_URL, "https://moki.vn/files/banner/banner_1510908466_42.jpg");
                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        return view;
    }

    private View view;
    private ImageView imageView;
    private Context context;


}
