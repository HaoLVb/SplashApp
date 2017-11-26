package com.example.levanhao.splashapp.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.fragment.IntroFragment;
import com.example.levanhao.splashapp.view.customview.tutorial.AppEventsConstants;
import com.example.levanhao.splashapp.view.customview.tutorial.Auth;
import com.example.levanhao.splashapp.view.customview.tutorial.BaseActivity;
import com.example.levanhao.splashapp.view.customview.tutorial.BordersPanel;
import com.example.levanhao.splashapp.view.customview.tutorial.CircularImageViewFrame;
import com.example.levanhao.splashapp.view.customview.tutorial.Frame;
import com.example.levanhao.splashapp.view.customview.tutorial.ImageViewFrame;
import com.example.levanhao.splashapp.view.customview.tutorial.MotionImage;
import com.example.levanhao.splashapp.view.customview.tutorial.MotionTextView;
import com.example.levanhao.splashapp.view.customview.tutorial.OnPageChangeListener;
import com.example.levanhao.splashapp.view.customview.tutorial.PackDetailLayout;
import com.example.levanhao.splashapp.view.customview.tutorial.PageIndicator;
import com.example.levanhao.splashapp.view.customview.tutorial.Pager;
import com.example.levanhao.splashapp.view.customview.tutorial.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends BaseActivity {
    private static final int NUM_PAGES = 4;
    private MotionImage bgImage;
    int currentZoomingIconIndex;
    private int devHeight;
    private int devWidth;
    private MotionImage deviceImage;
    //    private ImageViewFrame ensureImg;
    private CircularImageViewFrame fifthZoomImg;
    private MotionImage firstImg;
    private CircularImageViewFrame firstZoomImg;
    private CircularImageViewFrame fouthZoomImg;
    private ArrayList<MotionImage> iconList = new ArrayList();
    private PageIndicator indicator;
    private boolean isStoppedZooming;
    private ScrollView productImgScroll;
    private Pager scroller;
    private MotionImage secondImg;
    private CircularImageViewFrame secondZoomImg;
    private Button skipButton;
    private boolean stopZoom;
    private boolean stopZoomReverse;
    private MotionImage thirdImg;
    private ImageViewFrame thirdPageDeviceImage;
    private CircularImageViewFrame thirdZoomImg;
    private Timer timer;
    private TextView tvFourthPage;
    private MotionTextView tvSecondPage;
    private TextView tvThirdPage;
    private Frame zoomFrame;
    int zoomImgSize;
    CircularImageViewFrame zoomingIcon = null;
    private ArrayList<ImageView> zoomingListImg = new ArrayList();

    class C29471 implements View.OnClickListener {
        C29471() {
        }

        public void onClick(View v) {
            Auth.setStartUpIntroTutCheck(SplashActivity.this, AppEventsConstants.EVENT_PARAM_VALUE_YES);
            SplashActivity.this.startActivityFromRight(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }

    class C29482 implements View.OnTouchListener {
        C29482() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    class C29524 implements Runnable {

        class C29511 extends TimerTask {

            class C29501 implements Runnable {
                C29501() {
                }

                public void run() {
                    if (SplashActivity.this.scroller.getScrollX() < Utils.getScreenWidth(SplashActivity.this)) {
                        SplashActivity.this.scroller.smoothScrollBy(10, 0);
                    } else if (SplashActivity.this.timer != null) {
                        SplashActivity.this.timer.cancel();
                        SplashActivity.this.timer = null;
                    }
                }
            }

            C29511() {
            }

            public void run() {
                SplashActivity.this.runOnUiThread(new C29501());
            }
        }

        C29524() {
        }

        public void run() {
            SplashActivity.this.timer.schedule(new C29511(), 0, 10);
        }
    }

    class C29545 implements Animator.AnimatorListener {

        class C29531 implements Runnable {
            C29531() {
            }

            public void run() {
                SplashActivity.this.zoomIcon();
            }
        }

        C29545() {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
            if (!(SplashActivity.this.currentZoomingIconIndex == 0 && SplashActivity.this.stopZoomReverse) && (SplashActivity.this.currentZoomingIconIndex != 4 || SplashActivity.this.stopZoomReverse)) {
                SplashActivity.this.zoomIcon();
            } else {
                new Handler().postDelayed(new C29531(), 1000);
            }
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }
    }

    private void zoomIcon() {
        if (this.stopZoom) {
            this.isStoppedZooming = true;
            return;
        }
        Log.d("idicon", this.currentZoomingIconIndex + "");
        this.isStoppedZooming = false;


        if (stopZoomReverse) {
            switch (this.currentZoomingIconIndex) {
            case 0:
                this.zoomingIcon = this.firstZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 1:
                this.zoomingIcon = this.secondZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 2:
                this.zoomingIcon = this.thirdZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 3:
                this.zoomingIcon = this.fouthZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 4:
                this.zoomingIcon = this.fifthZoomImg;
                this.stopZoomReverse = false;
                break;
            }


        } else {
            switch (this.currentZoomingIconIndex) {
            case 0:
                this.zoomingIcon = this.firstZoomImg;
                this.stopZoomReverse = true;
                break;
            case 1:
                this.zoomingIcon = this.secondZoomImg;
                this.currentZoomingIconIndex--;
                break;
            case 2:
                this.zoomingIcon = this.thirdZoomImg;
                this.currentZoomingIconIndex--;
                break;
            case 3:
                this.zoomingIcon = this.fouthZoomImg;
                this.currentZoomingIconIndex--;
                break;
            case 4:
                this.zoomingIcon = this.fifthZoomImg;
                this.currentZoomingIconIndex--;
                break;
            default:
                break;
            }

        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this.zoomingIcon, PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.5f), PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.5f));
        objectAnimator.setDuration(250);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.addListener(new C29545());
        objectAnimator.start();
    }

    private void autoScrollProductList() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this.productImgScroll, "scrollY", new int[]{0, this.productImgScroll.getChildAt(0).getHeight() - this.productImgScroll.getHeight()}).setDuration(2000);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View fouthPage = layoutInflater.inflate(R.layout.start_up_tutorial_page3, null);
        View thirdPage = layoutInflater.inflate(R.layout.start_up_tutorial_page2, null);

        this.skipButton = findViewById(R.id.skip_button);
        this.skipButton.setOnClickListener(new C29471());
        this.skipButton.setVisibility(View.INVISIBLE);
        DisplayMetrics metrics = getApplication().getResources().getDisplayMetrics();
        this.devWidth = metrics.widthPixels;
        this.devHeight = metrics.heightPixels;
        int device_image_top_margin = this.devHeight / 20;
        this.scroller = findViewById(R.id.scrollView);
        this.indicator = findViewById(R.id.indicator);
        this.indicator.setPager(this.scroller);
        final FrameLayout zoomAndMotionLayout = findViewById(R.id.layout);
        this.deviceImage = findViewById(R.id.deviceImage);
        int device_image_width = this.devWidth - ((int) (((double) this.devWidth) / 1.7d));
        int device_image_height = (int) (((double) device_image_width) * 2.1d);
        ViewGroup.LayoutParams indicatorParams = this.indicator.getLayoutParams();
        indicatorParams.height = this.devHeight / 25;
        this.indicator.setLayoutParams(indicatorParams);

        //page 0:
        this.zoomFrame = Frame.FrameMake((this.devWidth - device_image_width) / 2, device_image_top_margin, device_image_width, device_image_height);
        this.deviceImage.setupZoomArea(Frame.FrameMake((-this.devWidth) / 11, -((int) (((double) this.devHeight) / 3.8d)), (int) (((double) this.devWidth) + (((double) this.devWidth) / 5.9d)), (int) (((double) this.devHeight) + (((double) this.devHeight) / 2.25d))), this.zoomFrame);
        this.firstImg = findViewById(R.id.firstImg);
        this.firstImg.setupPostion(Frame.FrameMake((int) (((double) (this.zoomFrame.f168x + this.zoomFrame.width)) - (((double) this.zoomFrame.f168x) / BordersPanel.ANCHOR_MAX_WIDTH_RATIO)), this.zoomFrame.f169y - 10, 0, 0), 3.2f);
        this.firstImg.setLayoutParams(new FrameLayout.LayoutParams(this.zoomFrame.width / 4, this.zoomFrame.width / 4));
        this.secondImg = findViewById(R.id.secondImg);
        this.secondImg.setupPostion(Frame.FrameMake(this.zoomFrame.f168x - (this.zoomFrame.f168x / 5), (int) (((double) this.zoomFrame.f169y) + (((double) this.zoomFrame.height) / BordersPanel.ANCHOR_MAX_WIDTH_RATIO)), 0, 0), 3.2f);
        this.secondImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 3.5d), (int) (((double) this.zoomFrame.width) / 3.5d)));
        this.thirdImg = findViewById(R.id.thirdImg);
        this.thirdImg.setupPostion(Frame.FrameMake((this.zoomFrame.f168x + this.zoomFrame.width) - (this.zoomFrame.f168x / 8), (int) (((double) this.zoomFrame.f169y) + (((double) this.zoomFrame.height) / PackDetailLayout.PARALLAX_RATIO)), 0, 0), 3.2f);
        this.thirdImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 4.5d), (int) (((double) this.zoomFrame.width) / 4.5d)));
        this.iconList.add(this.firstImg);
        this.iconList.add(this.secondImg);
        this.iconList.add(this.thirdImg);
        this.deviceImage.moving((float) this.devWidth);

        //page 1:
        this.tvSecondPage = findViewById(R.id.second_page_text);
        this.tvSecondPage.setupPostion(Frame.FrameMake(0, 0, 0, 0), 3.2f);
        this.tvSecondPage.setVisibility(View.INVISIBLE);
        this.tvSecondPage.setText(getResources().getString(R.string.startup_tutorial_page1));
        //page 2:
        this.tvThirdPage = thirdPage.findViewById(R.id.third_page_text);
        this.tvThirdPage.setText(getResources().getString(R.string.startup_tutorial_page2));
        FrameLayout layout = thirdPage.findViewById(R.id.thirdPageLayout);
        this.thirdPageDeviceImage = thirdPage.findViewById(R.id.thirdPageDeviceImage);
        this.thirdPageDeviceImage.setFrame(this.zoomFrame);
        this.productImgScroll = thirdPage.findViewById(R.id.productImageScroll);
        this.productImgScroll.setX((float) (((double) this.zoomFrame.f168x) + (((double) this.zoomFrame.width) / 12.9d)));
        this.productImgScroll.setY((float) (((double) this.zoomFrame.f169y) + (((double) this.zoomFrame.height) / 5.5d)));
        this.productImgScroll.setOnTouchListener(new C29482());
        ViewGroup.LayoutParams productImgScrollParams = this.productImgScroll.getLayoutParams();
        productImgScrollParams.width = (int) (((double) this.zoomFrame.width) - (((double) this.zoomFrame.width) / 7.1d));
        productImgScrollParams.height = (int) (((double) this.zoomFrame.height) - (((double) this.zoomFrame.height) / 3.14d));
        this.productImgScroll.setLayoutParams(productImgScrollParams);
        this.zoomImgSize = device_image_width / 4;
        this.firstZoomImg = thirdPage.findViewById(R.id.firstZoomIcon);
        this.firstZoomImg.setFrame(Frame.FrameMake((this.zoomFrame.f168x + this.zoomFrame.width) - (this.zoomFrame.f168x / 5), (int) (((double) this.zoomFrame.f169y) + (((double) this.zoomFrame.height) / 1.8d)), this.zoomImgSize, this.zoomImgSize));
        this.secondZoomImg = thirdPage.findViewById(R.id.secondZoomIcon);
        this.secondZoomImg.setFrame(Frame.FrameMake(this.zoomFrame.f168x - (this.zoomFrame.f168x / 5), this.zoomFrame.f169y + (this.zoomFrame.f168x / 2), this.zoomImgSize, this.zoomImgSize));
        layout.bringChildToFront(this.secondZoomImg);
        this.thirdZoomImg = thirdPage.findViewById(R.id.thirdZoomIcon);
        this.thirdZoomImg.setFrame(Frame.FrameMake((this.zoomFrame.f168x + this.zoomFrame.width) - (this.zoomFrame.f168x / 5), this.zoomFrame.f169y + (this.zoomFrame.f168x / 3), this.zoomImgSize, this.zoomImgSize));
        layout.bringChildToFront(this.thirdZoomImg);
        this.fouthZoomImg = thirdPage.findViewById(R.id.fouthZoomIcon);
        this.fouthZoomImg.setFrame(Frame.FrameMake((int) (((double) this.zoomFrame.f168x) + (((double) this.zoomFrame.width) / 2.2d)), (int) (((double) this.zoomFrame.f169y) - (((double) this.zoomImgSize) / 2.2d)), this.zoomImgSize, this.zoomImgSize));
        this.fifthZoomImg = thirdPage.findViewById(R.id.fifthZoomIcon);
        this.fifthZoomImg.setFrame(Frame.FrameMake((int) (((double) this.zoomFrame.f168x) - (((double) this.zoomFrame.f168x) / 3.5d)), (int) (((double) this.zoomFrame.f169y) + (((double) this.zoomFrame.height) / PackDetailLayout.PARALLAX_RATIO)), this.zoomImgSize, this.zoomImgSize));
        layout.bringChildToFront(this.fifthZoomImg);
        this.zoomingListImg.add(this.firstZoomImg);
        this.zoomingListImg.add(this.secondZoomImg);
        this.zoomingListImg.add(this.thirdZoomImg);
        this.zoomingListImg.add(this.fouthZoomImg);
        this.zoomingListImg.add(this.fifthZoomImg);
        this.isStoppedZooming = true;
        autoScrollProductList();
        //page3 :
        this.tvFourthPage = fouthPage.findViewById(R.id.fouth_page_text);
        this.tvFourthPage.setText(getResources().getString(R.string.startup_tutorial_page3));
//                int ensureImgWidth = (int) (((double) this.devWidth) / 3.5d);
//                this.ensureImg = fouthPage.findViewById(R.id.ensureImg);
//                this.ensureImg.setFrame(Frame.FrameMake((int) (((double) this.devWidth) - (((double) ensureImgWidth) * PackDetailLayout.PARALLAX_RATIO)), ensureImgWidth / 2, ensureImgWidth, ensureImgWidth));
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                this.scroller.addPage(new View(this));
            } else if (i == 1) {
                this.scroller.addPage(new View(this));

            } else if (i == 2) {

                this.scroller.addPage(thirdPage);
            } else if (i == 3) {

                this.scroller.addPage(fouthPage);
            }
        }
        this.scroller.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageChange(Pager scroller) {
            }

            public void onPageCountChange(Pager scroller) {
            }

            public void pageScroll(int l, int t, int oldl, int oldt) {
                int page = (int) Math.floor((double) (l / SplashActivity.this.devWidth));
                SplashActivity.this.skipButton.setText("Bỏ qua");
                Iterator it = SplashActivity.this.iconList.iterator();
                while (it.hasNext()) {
                    ((MotionImage) it.next()).moving((float) (SplashActivity.this.devWidth - l));
                }
                SplashActivity.this.tvSecondPage.moving((float) (SplashActivity.this.devWidth - l));
                if (page == 0) {
                    SplashActivity.this.skipButton.setVisibility(View.INVISIBLE);
                    SplashActivity.this.deviceImage.motionRate = 0.0f;
                } else if (page > 0) {
                    zoomAndMotionLayout.bringChildToFront(SplashActivity.this.firstImg);
                    zoomAndMotionLayout.bringChildToFront(SplashActivity.this.secondImg);
                    zoomAndMotionLayout.requestLayout();
                    SplashActivity.this.skipButton.setVisibility(View.VISIBLE);
                    SplashActivity.this.deviceImage.motionRate = 1.0f;
                }
                SplashActivity.this.deviceImage.moving((float) (SplashActivity.this.devWidth - l));
                if (page == 1) {
                    SplashActivity.this.tvSecondPage.setVisibility(View.VISIBLE);
                }
                SplashActivity.this.stopZoom = true;
                if (page == 2) {
                    SplashActivity.this.stopZoom = false;
                    SplashActivity.this.tvSecondPage.setVisibility(View.INVISIBLE);
                    if (SplashActivity.this.isStoppedZooming) {
                        SplashActivity.this.zoomIcon();
                        SplashActivity.this.autoScrollProductList();
                    }
                }
                if (page == 3) {
                    SplashActivity.this.skipButton.setText("Bắt đầu");
                }
            }
        });

    }

}
