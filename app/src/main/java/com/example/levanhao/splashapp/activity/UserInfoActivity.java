package com.example.levanhao.splashapp.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.ViewPagerAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.fragment.BlankFragment;
import com.example.levanhao.splashapp.fragment.user.FollowingFragment;
import com.example.levanhao.splashapp.fragment.user.ListFollowedFragment;
import com.example.levanhao.splashapp.fragment.user.UserListingFragment;
import com.example.levanhao.splashapp.model.UserInfoPage;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.backIcon:
            onBackPressed();
            break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        token = getIntent().getStringExtra(StaticVarriable.TOKEN);
        userId = getIntent().getIntExtra(StaticVarriable.USER_ID, -1);
        userInfoHandler = new UserInfoHandler();
        LoginActivity.systemManager.getHandlerManager().setUserInfoHandler(userInfoHandler);
        LoginActivity.requestManager.getUserInfo(token, userId, userInfoHandler);
        init();
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
                if (tab.getPosition() == 0) {
                    showViews();
                } else {
                    hideViews();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private ImageView backIcon;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView sellButton;
    private String token;
    private int userId;
    private UserInfoHandler userInfoHandler;
    private UserInfoPage userInfoPage;
    private AnimationDrawable loadingViewAnim = null;
    private View vloading;
    private TextView userName;
    private CircleImageView profileImage;

    private void init() {
        this.backIcon = findViewById(R.id.backIcon);
        this.sellButton = findViewById(R.id.sellButton);
        this.backIcon.setOnClickListener(this);
        this.tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(0);
        this.viewPager = findViewById(R.id.viewPager);
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = (ImageView) findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        vloading.setVisibility(View.VISIBLE);
        userName = findViewById(R.id.userName);
        profileImage = findViewById(R.id.profileImage);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter mainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainAdapter.addFrag(UserListingFragment.newInstance(userId, token), null);
        mainAdapter.addFrag(FollowingFragment.newInstance(userId), null);
        mainAdapter.addFrag(ListFollowedFragment.newInstance(userId), null);
        viewPager.setAdapter(mainAdapter);
    }


    private void hideViews() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sellButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        sellButton.animate().translationY(sellButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
        sellButton.animate().translationX(sellButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        sellButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        sellButton.animate().translationX(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private class UserInfoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_USER_INFO:
                userInfoPage = new UserInfoPage((JSONObject) msg.obj);
                updateUI();
                break;
            default:
                break;
            }
        }
    }

    private void updateUI() {
        Log.e("yyy2", userInfoPage.toString());
        userName.setText(userInfoPage.getUsername());
        Glide.with(this)
                .load(StaticVarriable.DOMAIN + "/" +userInfoPage.getAvatar())
                .placeholder(R.drawable.icon_no_avatar)
                .error(R.drawable.icon_no_avatar)
                .into(profileImage);
    }
}
