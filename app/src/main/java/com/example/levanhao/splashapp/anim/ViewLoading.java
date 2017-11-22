package com.example.levanhao.splashapp.anim;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by thanglv on 7/2/2016.
 */
public class ViewLoading {
    private AnimationDrawable loadingViewAnim = null;
    private ImageView loadingIcon = null;

    public ViewLoading(AnimationDrawable loadingViewAnim, ImageView loadingIcon) {
        this.loadingViewAnim = loadingViewAnim;
        this.loadingIcon = loadingIcon;
    }

    public void createAnim() {
        if (loadingIcon != null) {
            loadingIcon.setVisibility(View.VISIBLE);
            loadingViewAnim = (AnimationDrawable) loadingIcon.getDrawable();
            loadingViewAnim.setCallback(loadingIcon);
            loadingViewAnim.setVisible(true, true);
        }

        if (!loadingViewAnim.isRunning()) {
            loadingViewAnim.start();
        } else {
            loadingViewAnim.stop();
        }
    }
    /**
     * Không cho phép tương tác tới layout
     *
     * @param layout
     */
    public static void disableOrEnable(ViewGroup layout, boolean check) {
        layout.setEnabled(check);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableOrEnable((ViewGroup) child, check);
            } else {
                child.setEnabled(check);
            }
        }
    }
}
