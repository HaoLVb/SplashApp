package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by HoangNV on 11/15/2017.
 */

public class ImageViewFrame extends android.support.v7.widget.AppCompatImageView {
    Frame frame;

    public ImageViewFrame(Context context) {
        super(context);
    }

    public ImageViewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
