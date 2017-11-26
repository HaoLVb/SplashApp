package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by HoangNV on 11/15/2017.
 */

public class MotionTextView extends android.support.v7.widget.AppCompatTextView {

    private static final int MOTION_TYPE_SLICE = 1;
    private static final int MOTION_TYPE_ZOOM = 0;
    public Frame desirePosition;
    public float motionRate;
    ViewGroup.LayoutParams param;
    public Frame startPosition;

    public MotionTextView(Context context) {
        super(context);
    }

    public MotionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void moving(float distance) {
        if ((this.motionRate != 0.0f ? 1 : 0) == 1) {
            setX(((float) this.desirePosition.f168x) + (this.motionRate * distance));
        } else if (distance < 0.0f) {
            setX(((float) this.desirePosition.f168x) + distance);
        } else {
            float zoom = distance / ((float) getContext().getResources().getDisplayMetrics().widthPixels);
            int x = (int) (((float) this.desirePosition.f168x) + (((float) (this.startPosition.f168x - this.desirePosition.f168x)) * zoom));
            int y = (int) (((float) this.desirePosition.f169y) + (((float) (this.startPosition.f169y - this.desirePosition.f169y)) * zoom));
            int width = (int) (((float) this.desirePosition.width) + (((float) (this.startPosition.width - this.desirePosition.width)) * zoom));
            int height = (int) (((float) this.desirePosition.height) + (((float) (this.startPosition.height - this.desirePosition.height)) * zoom));
            this.param = getLayoutParams();
            this.param.width = width;
            this.param.height = height;
            setX((float) x);
            setY((float) y);
            setLayoutParams(this.param);
        }
    }

    public void setupZoomArea(Frame startPosition, Frame endPosition) {
        this.startPosition = startPosition;
        this.desirePosition = endPosition;
    }

    public void setupPostion(Frame positionAndSize, float motionRate) {
        this.desirePosition = positionAndSize;
        this.motionRate = motionRate;
        int devWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        setX(((float) positionAndSize.f168x) * motionRate);
        setY((float) positionAndSize.f169y);
    }
}
