package com.example.levanhao.splashapp.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageButton;

/**
 * Created by vokhuyet on 22/07/2016.
 */
public class AnimateButtonSell {
    public static void showButton(ImageButton imageBtSell) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageBtSell, "translationX", imageBtSell.getX(), 0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageBtSell, "translationY", imageBtSell.getY(), 0);
        set.playTogether(animatorX, animatorY);
        set.setDuration(500);
        set.start();
    }

    public static void hideButton(ImageButton imageBtSell) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageBtSell, "translationX", 0, imageBtSell.getX());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageBtSell, "translationY", 0, imageBtSell.getY());
        set.playTogether(animatorX, animatorY);
        set.setDuration(500);
        set.start();
    }
}
