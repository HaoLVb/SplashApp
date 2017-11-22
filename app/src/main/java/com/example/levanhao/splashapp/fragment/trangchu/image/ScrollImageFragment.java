package com.example.levanhao.splashapp.fragment.trangchu.image;

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
			Glide.with(getActivity()).load("https://moki.vn//files/banner/banner_1507083436_36.png").into(imageView);
		}
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mParam1 == 0) {
					Log.e("123","0");
				} else {
					Log.e("123","1");
				}
			}
		});
		return view;
	}

	private View view;
	private ImageView imageView;


}
