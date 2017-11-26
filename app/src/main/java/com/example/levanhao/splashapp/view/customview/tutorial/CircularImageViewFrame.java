package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by HoangNV on 11/15/2017.
 */

public class CircularImageViewFrame extends CircularImageView {

    Frame frame;

    public CircularImageViewFrame(Context context) {
        super(context);
    }

    public CircularImageViewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFrame(Frame frame) {
        ViewGroup.LayoutParams param = getLayoutParams();
        if (param != null) {
            param.width = frame.width;
            param.height = frame.height;
            setX((float) frame.f168x);
            setY((float) frame.f169y);
            setLayoutParams(param);
        }
    }

    public Frame getFrame() {
        return this.frame;
    }

    public float getY() {
        if (this.frame != null) {
            return (float) this.frame.f169y;
        }
        return super.getY();
    }

    public float getX() {
        if (this.frame != null) {
            return (float) this.frame.f168x;
        }
        return super.getX();
    }
}
