package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;


public abstract class PackDetailLayout extends RelativeLayout implements OnClickListener, OnGlobalLayoutListener {
    public static final double PARALLAX_RATIO = 1.5d;

    public PackDetailLayout(Context context) {
        super(context);
    }
}
