package com.example.levanhao.splashapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {


    private View view;
    private int position;
    private TextView txtTitleIntro;

    private View view1;
    private View view2;
    private View view3;

    private View ava1;
    private View ava2;
    private View ava3;

    private RelativeLayout rllIntro3;
    private ImageView imgIntro1;
    private ImageView imgIntro2;
    private ImageView imgIntro3;
    private Button btnBoQua;


    private Animation animation;


    public IntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.test);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        view1 = view.findViewById(R.id.view_1);
        view2 = view.findViewById(R.id.view_2);
        view3 = view.findViewById(R.id.view_3);

        ava1 = view.findViewById(R.id.img_ava_1);
        ava2 = view.findViewById(R.id.img_ava_2);
        ava3 = view.findViewById(R.id.img_ava_3);
        rllIntro3 = view.findViewById(R.id.rll_intro_3);
        imgIntro1 = view.findViewById(R.id.img_intro1);
        imgIntro2 = view.findViewById(R.id.img_intro2);
        imgIntro3 = view.findViewById(R.id.img_intro3);
        btnBoQua = view.findViewById(R.id.btn_bo_qua);

        txtTitleIntro = view.findViewById(R.id.txt_title_intro);


        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("POSITION", -1);
        }
        if (position < 3) {
            switch (position) {
            case 0:
                view1.setBackgroundResource(R.drawable.indicator_selected);
                view2.setBackgroundResource(R.drawable.indicator_unselected);
                view3.setBackgroundResource(R.drawable.indicator_unselected);

                imgIntro1.setVisibility(View.VISIBLE);
                rllIntro3.setVisibility(View.GONE);
                imgIntro2.setVisibility(View.GONE);
                btnBoQua.setVisibility(View.INVISIBLE);

                break;

            case 1:
                view1.setBackgroundResource(R.drawable.indicator_unselected);
                view2.setBackgroundResource(R.drawable.indicator_selected);
                view3.setBackgroundResource(R.drawable.indicator_unselected);

                txtTitleIntro.setVisibility(View.VISIBLE);
                txtTitleIntro.setText("Vì cuộc đời không nơi đâu có nút back lại...");

                imgIntro1.setVisibility(View.GONE);
                rllIntro3.setVisibility(View.VISIBLE);
                imgIntro3.setVisibility(View.GONE);

                imgIntro2.setImageResource(R.drawable.tutorial_devicebg2);
                btnBoQua.setVisibility(View.VISIBLE);

                ava1.startAnimation(animation);
                ava2.startAnimation(animation);
                ava3.startAnimation(animation);
                break;
            case 2:
                view1.setBackgroundResource(R.drawable.indicator_unselected);
                view2.setBackgroundResource(R.drawable.indicator_unselected);
                view3.setBackgroundResource(R.drawable.indicator_selected);


                txtTitleIntro.setVisibility(View.VISIBLE);
                txtTitleIntro.setText("Không yêu đừng nói lời cay đắng.");

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.com_adobe_image_toolbar_out);

                imgIntro3.startAnimation(animation);
                imgIntro1.setVisibility(View.GONE);
                rllIntro3.setVisibility(View.GONE);
                imgIntro3.setVisibility(View.VISIBLE);
                btnBoQua.setVisibility(View.VISIBLE);


                break;
            }

        }
    }

}
