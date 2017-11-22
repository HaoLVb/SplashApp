package com.example.levanhao.splashapp.anim;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by vokhuyet on 25/06/2016.
 */
public class AnimateScreenTutorial {
    public static void AnimateImageTutorialSwipe(View imageTutorialSwipe) {
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(imageTutorialSwipe, "translationX", -100, 100);
        animatorTranslateX.setDuration(1000);
        animatorTranslateX.setRepeatCount(ValueAnimator.INFINITE);
        animatorTranslateX.setRepeatMode(ValueAnimator.REVERSE);
        animatorTranslateX.start();
    }

    public static void AnimateImageArrowChangeViewType(View imageArrowChangeViewType) {
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(imageArrowChangeViewType, "translationX", 0, 80);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(imageArrowChangeViewType, "translationY", 0, -80);
        animatorTranslateX.setDuration(1000);
        animatorTranslateX.setRepeatMode(ValueAnimator.RESTART);
        animatorTranslateX.setRepeatCount(ValueAnimator.INFINITE);
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.setRepeatMode(ValueAnimator.RESTART);
        animatorTranslateY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animatorTranslateX, animatorTranslateY);
        set.start();
    }

    public static void AnimateImageArrowCamera(View imageArrowCamera) {
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(imageArrowCamera, "translationX", 0, 80);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(imageArrowCamera, "translationY", 0, 80);
        animatorTranslateX.setDuration(1000);
        animatorTranslateX.setRepeatMode(ValueAnimator.RESTART);
        animatorTranslateX.setRepeatCount(ValueAnimator.INFINITE);
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.setRepeatMode(ValueAnimator.RESTART);
        animatorTranslateY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animatorTranslateX, animatorTranslateY);
        set.start();
    }
}
