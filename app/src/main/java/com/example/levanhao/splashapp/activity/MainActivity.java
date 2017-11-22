package com.example.levanhao.splashapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.NavigationItemAdapter;
import com.example.levanhao.splashapp.adapter.ViewPagerIntroSellAdapter;
import com.example.levanhao.splashapp.anim.AnimateScreenTutorial;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.dialog.NotificationExitApp;
import com.example.levanhao.splashapp.dialog.NotificationLogoutDialog;
import com.example.levanhao.splashapp.dialog.ViewDialogForNotification;
import com.example.levanhao.splashapp.fragment.BlankFragment;
import com.example.levanhao.splashapp.fragment.SupportFragment;
import com.example.levanhao.splashapp.fragment.favorite.FavoriteFragment;
import com.example.levanhao.splashapp.fragment.trangchu.MainHomeFragment;
import com.example.levanhao.splashapp.model.NavigationItem;
import com.example.levanhao.splashapp.model.UserInformationModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NotificationLogoutDialog.OnDialogClickListener {

    public static FragmentManager fragmentManager;
    public static boolean is_grid = false;
    public static String token;


    public static void replaceFragment(final Fragment fragment, final int container) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.replace(
                        container, fragment).commit();
            }
        }, 0);
    }

    public static void attachFragment(Fragment fragment, int container) {
        if (fragment != null) {
            int backStackCount = fragmentManager.getBackStackEntryCount();
            for (int i = 0; i < backStackCount; i++) {
                int backStackId = fragmentManager.getBackStackEntryAt(i)
                        .getId();
                fragmentManager.popBackStack(backStackId,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (fragment.isDetached()) {
                fragmentTransaction.attach(fragment);
                fragmentTransaction.commit();
            } else if (!fragment.isAdded()) {
                fragmentTransaction.add(container, fragment, fragment
                        .getClass().getSimpleName() + backStackCount);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.profileLayout:
            if (StaticMethod.checkIsLogin(this)) {
                LoginHelper loginHelper = new LoginHelper(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra(StaticVarriable.USER_ID, loginHelper.getUser().getId());
                intent.putExtra(StaticVarriable.TOKEN, loginHelper.getUser().getToken());
                startActivity(intent);
            } else {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
            break;
        case R.id.searchIcon:
            Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.slideMenuIcon:
            mDrawerLayout.openDrawer(listLayout);
            break;
        case R.id.sellButton:
            if (!StaticMethod.checkIsLogin(this)) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else if (!checkFirstSell()) {
                onFirstSell();
            } else {
                Intent sellIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(sellIntent);
            }
            break;
        case R.id.timelineIcon:
            if (is_grid) {
                timelineIcon.setImageResource(R.drawable.icon_timeline);
                is_grid = false;
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getCategoryProductHandler(),
                        StaticVarriable.TIMELINE);
            } else {
                timelineIcon.setImageResource(R.drawable.icon_grid);
                is_grid = true;

                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getCategoryProductHandler(),
                        StaticVarriable.GIRD);
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();//lây về token dùng cho cả app
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        vloading.setVisibility(View.VISIBLE);
        //
        fragmentManager = getSupportFragmentManager();
        mainHandler = new MainHandler();
        LoginActivity.systemManager.getHandlerManager().setMainHandler(mainHandler);
        this.mainHomeFragment = MainHomeFragment.newInstance();
        this.blankFragment = BlankFragment.newInstance();
        this.supportFragment = SupportFragment.newInstance();
        this.favoriteFragment = FavoriteFragment.newInstance();
        init();
        getUserInfo();
        //đưa dữ liệu vào arraylist
        initArray();

        navigationItemAdapter = new NavigationItemAdapter(this, navigationItems);
        navigationListView.setAdapter(navigationItemAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (!checkFirstTimes()) {
            onFirstTime();
        }
    }

    private void getUserInfo() {
        LoginHelper loginHelper = new LoginHelper(this);
        UserInformationModel userInformationModel = loginHelper.getUser();
        if (userInformationModel != null) {
            navigationItem10 = new NavigationItem(
                    R.drawable.sidemenu_icon_logout_normal, getString(R.string.logout));
            profileTextView.setText(userInformationModel.getUserName());
            Glide.with(this)
                    .load(StaticVarriable.DOMAIN + "/" + userInformationModel.getAvatar())
                    .placeholder(R.drawable.icon_no_avatar)
                    .error(R.drawable.no_image)
                    .into(profileImage);
        } else {
            navigationItem10 = new NavigationItem(
                    R.drawable.sidemenu_icon_logout_normal, getString(R.string.login));
            profileTextView.setText(getString(R.string.login));
            profileImage.setImageResource(R.drawable.icon_no_avatar);
        }

    }

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mainView;
    private ListView navigationListView;
    private ArrayList<NavigationItem> navigationItems;
    private NavigationItemAdapter navigationItemAdapter;
    private RelativeLayout listLayout;
    private LinearLayout toolbarLayout;
    private TextView title;
    private ImageView mokiImage;
    private ImageView slideMenuIcon;
    private MainHomeFragment mainHomeFragment;
    private BlankFragment blankFragment;
    private SupportFragment supportFragment;
    private FavoriteFragment favoriteFragment;
    private LinearLayout profileLayout;
    private ImageView searchIcon;
    private ViewGroup screenTutorial1;
    private ViewGroup screenTutorial2;
    private ViewGroup screenTutorial3;
    private ImageView sellButton;
    private MainHandler mainHandler;
    private ImageView timelineIcon;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private CircleImageView profileImage;
    private TextView profileTextView;
    private NavigationItem navigationItem10;

    private void initArray() {
        navigationItems = new ArrayList<>();
        NavigationItem navigationItem1 = new NavigationItem(
                R.drawable.setting_home_icon, getString(R.string.home));
        navigationItem1.setSelected(true);
        NavigationItem navigationItem2 = new NavigationItem(
                R.drawable.sidemenu_icon_news_normal, getString(R.string.information));
        NavigationItem navigationItem3 = new NavigationItem(
                R.drawable.sidemenu_icon_like_normal, getString(R.string.favorite_list));
        NavigationItem navigationItem4 = new NavigationItem(
                R.drawable.sidemenu_icon_exhibit_normal, getString(R.string.sell_list));
        NavigationItem navigationItem5 = new NavigationItem(
                R.drawable.sidemenu_icon_buy_normal, getString(R.string.buy_list));
        NavigationItem navigationItem6 = new NavigationItem(
                R.drawable.sidemenu_icon_charity, getString(R.string.charity));
        NavigationItem navigationItem7 = new NavigationItem(
                R.drawable.setting_noti_setting_icon, getString(R.string.setup));
        NavigationItem navigationItem8 = new NavigationItem(
                R.drawable.sidemenu_icon_contact_normal, getString(R.string.support_center));
        NavigationItem navigationItem9 = new NavigationItem(
                R.drawable.sidemenu_icon_invite_normal, getString(R.string.about_moki));

        navigationItems.add(navigationItem1);
        navigationItems.add(navigationItem2);
        navigationItems.add(navigationItem3);
        navigationItems.add(navigationItem4);
        navigationItems.add(navigationItem5);
        navigationItems.add(navigationItem6);
        navigationItems.add(navigationItem7);
        navigationItems.add(navigationItem8);
        navigationItems.add(navigationItem9);
        navigationItems.add(navigationItem10);
    }

    private boolean checkFirstTimes() {
        SharedPreferences sharedPreferences = getSharedPreferences("ranBefore", MODE_PRIVATE);
        return sharedPreferences.getBoolean("ranBefore", false);
    }

    private boolean checkFirstSell() {
        SharedPreferences sharedPreferences = getSharedPreferences("hadSell", MODE_PRIVATE);
        return sharedPreferences.getBoolean("hadSell", false);
    }

    private void init() {
        sellButton = findViewById(R.id.sellButton);
        sellButton.setOnClickListener(this);
        mainView = findViewById(R.id.mainView);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        navigationListView = findViewById(R.id.navigationListView);
        listLayout = findViewById(R.id.listLayout);
        toolbarLayout = findViewById(R.id.toolbarLayout);
        profileLayout = findViewById(R.id.profileLayout);
        profileLayout.setOnClickListener(this);
        title = findViewById(R.id.title);
        mokiImage = findViewById(R.id.mokiImage);
        searchIcon = findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(this);
        slideMenuIcon = findViewById(R.id.slideMenuIcon);
        slideMenuIcon.setOnClickListener(this);
        timelineIcon = findViewById(R.id.timelineIcon);
        timelineIcon.setOnClickListener(this);
        profileImage = findViewById(R.id.profileImage);
        profileTextView = findViewById(R.id.profileTextView);

        navigationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < navigationItems.size(); i++) {
                    if (i == position) {
                        navigationItems.get(i).setSelected(true);
                    } else {
                        navigationItems.get(i).setSelected(false);
                    }
                }
                navigationItemAdapter.notifyDataSetChanged();
                displayView(position);
            }
        });
        navigationListView.setItemChecked(0, true);//position 1
        navigationListView.setSelection(0);//position 1
        setDefault();

    }

    private void onClickButtonCloseTutorial() {
        SharedPreferences sharedPreferences = getSharedPreferences("ranBefore", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ranBefore", true);
        editor.commit();
        screenTutorial1.setVisibility(View.GONE);
        screenTutorial2.setVisibility(View.GONE);
        screenTutorial3.setVisibility(View.GONE);
    }

    private void onFirstTime() {
        //init view on first time
        screenTutorial1 = findViewById(R.id.screenTutorial1);
        screenTutorial2 = findViewById(R.id.screenTutorial2);
        screenTutorial3 = findViewById(R.id.screenTutorial3);
        Button btCloseScreenTutorial1 = findViewById(R.id.btCloseScreenTutorial1);
        Button btCloseScreenTutorial2 = findViewById(R.id.btCloseScreenTutorial2);
        Button btCloseScreenTutorial3 = findViewById(R.id.btCloseScreenTutorial3);
        ImageView imageTutorialSwipe = findViewById(R.id.imageTutorialSwipe);
        final ImageView imageArrowChangeViewType = findViewById(R.id.imageArrowChangeViewType);
        final ImageView imageArrowCamera = findViewById(R.id.imageArrowCamera);
        Button btTutorialChangeViewMod = findViewById(R.id.btTutorialChangeViewmod);
        Button btTutorialSell = findViewById(R.id.btTutorialSell);

        if (btCloseScreenTutorial1 != null) {
            btCloseScreenTutorial1.setOnClickListener(v -> onClickButtonCloseTutorial());
        }
        if (btCloseScreenTutorial2 != null) {
            btCloseScreenTutorial2.setOnClickListener(v -> onClickButtonCloseTutorial());
        }
        if (btCloseScreenTutorial3 != null) {
            btCloseScreenTutorial3.setOnClickListener(v -> onClickButtonCloseTutorial());
        }
        if (btTutorialChangeViewMod != null) {
            btTutorialChangeViewMod.setOnClickListener(v -> {

            });
        }
        if (btTutorialSell != null) {
            btTutorialSell.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            });
        }

        screenTutorial1.setVisibility(View.VISIBLE);
        AnimateScreenTutorial.AnimateImageTutorialSwipe(imageTutorialSwipe);
        screenTutorial1.setOnTouchListener((v, event) -> {
                /*float x1=0;
                float x2;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 =event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 =event.getX();
                        float deltaX= x1-x2;
                        //check swipe
                        if (Math.abs(deltaX) >100){
                            //left to right
                            if (x1 < x2){

                            }
                            //right to left
                            else if (x2 >x1){

                            }
                        }*/
            screenTutorial1.setVisibility(View.GONE);
            screenTutorial2.setVisibility(View.VISIBLE);
            //break;
            // }
            AnimateScreenTutorial.AnimateImageArrowChangeViewType(imageArrowChangeViewType);
            return false;
        });
        screenTutorial2.setOnTouchListener((v, event) -> {
            screenTutorial2.setVisibility(View.GONE);
            screenTutorial3.setVisibility(View.VISIBLE);

            AnimateScreenTutorial.AnimateImageArrowCamera(imageArrowCamera);
            return false;
        });
        screenTutorial3.setOnTouchListener((v, event) -> {
            screenTutorial3.setVisibility(View.GONE);
            SharedPreferences sharedPreferences = getSharedPreferences("ranBefore", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("ranBefore", true);
            editor.commit();
            return false;
        });
    }

    private void onFirstSell() {
        //init view for intro sell
        ViewPager viewPagerIntroSell = findViewById(R.id.viewPapersIntroSell);
        final Button btBeginSell = findViewById(R.id.btBeginSell);
        Button btCloseIntroSell = findViewById(R.id.btCloseIntroSell);
        ViewPagerIntroSellAdapter introSellAdapter = new ViewPagerIntroSellAdapter(getSupportFragmentManager());
        viewPagerIntroSell.setAdapter(introSellAdapter);
        final RelativeLayout layoutIntroSell = findViewById(R.id.layoutIntroSell);
        final ImageView dot1 = findViewById(R.id.dot1);
        final ImageView dot2 = findViewById(R.id.dot2);
        final ImageView dot3 = findViewById(R.id.dot3);
        if (layoutIntroSell != null) {
            layoutIntroSell.setVisibility(View.VISIBLE);
        }


        viewPagerIntroSell.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                case 0:
                    if (dot1 != null) {
                        dot1.setBackgroundResource(R.drawable.dot_is_selected);
                    }
                    if (dot2 != null) {
                        dot2.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    if (dot3 != null) {
                        dot3.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    break;
                case 1:
                    if (dot1 != null) {
                        dot1.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    if (dot2 != null) {
                        dot2.setBackgroundResource(R.drawable.dot_is_selected);
                    }
                    if (dot3 != null) {
                        dot3.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    break;
                case 2:
                    if (dot1 != null) {
                        dot1.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    if (dot2 != null) {
                        dot2.setBackgroundResource(R.drawable.dot_is_not_selected);
                    }
                    if (dot3 != null) {
                        dot3.setBackgroundResource(R.drawable.dot_is_selected);
                    }
                    break;
                }
                if (position == 2) {
                    if (btBeginSell != null) {
                        btBeginSell.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (btBeginSell != null) {
            btBeginSell.setOnClickListener(v -> {
                SharedPreferences sharedPreferences = getSharedPreferences("hadSell", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hadSell", true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            });
        }
        if (btCloseIntroSell != null) {
            btCloseIntroSell.setOnClickListener(v -> {
                layoutIntroSell.setVisibility(View.GONE);
                SharedPreferences sharedPreferences = getSharedPreferences("hadSell", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hadSell", true);
                editor.commit();
            });
        }
    }

    private void setDefault() {
        toolbarLayout.setVisibility(View.VISIBLE);
        mokiImage.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        attachFragment(mainHomeFragment, R.id.mainFragment);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = mainHomeFragment;
            toolbarLayout.setVisibility(View.VISIBLE);
            mokiImage.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            sellButton.setVisibility(View.VISIBLE);
            changeScreen(fragment);
            break;
        case 1:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.information));
            sellButton.setVisibility(View.GONE);
            changeScreen(fragment);
            break;
        case 2:
            fragment = favoriteFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.favorite_list));
            changeScreen(fragment);
            break;
        case 3:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.sell_list));
            changeScreen(fragment);
            break;
        case 4:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.buy_list));
            changeScreen(fragment);
            break;
        case 5:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.charity));
            changeScreen(fragment);
            break;
        case 6:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.setup));
            changeScreen(fragment);
            break;
        case 7:
            fragment = supportFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            sellButton.setVisibility(View.GONE);
            title.setText(getString(R.string.support_center));
            changeScreen(fragment);
            break;
        case 8:
            fragment = blankFragment;
            toolbarLayout.setVisibility(View.GONE);
            mokiImage.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.about_moki));
            sellButton.setVisibility(View.GONE);
            changeScreen(fragment);
            break;
        case 9:
            if (StaticMethod.checkIsLogin(this)) {
                NotificationLogoutDialog dialog = new NotificationLogoutDialog(this, this);
                dialog.show();

            } else {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }


            break;
        default:
            break;
        }

    }

    private void changeScreen(Fragment fragment) {
        final Fragment fragments = fragment;
        if (fragments != null) {
            mDrawerLayout.closeDrawer(listLayout);
            replaceFragment(fragments, R.id.mainFragment);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClickOk() {
        LoginActivity.requestManager.logout(token, mainHandler);
    }

    private class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.SCROLL_TOP:
                hideViews();
                break;
            case StaticVarriable.SCROLL_BOTTOM:
                showViews();
                break;
            case StaticVarriable.GET_LIST_PRODUCT:
                vloading.setVisibility(View.GONE);
                break;
            case StaticVarriable.LOGOUT:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                LoginHelper loginHelper = new LoginHelper(MainActivity.this);
                loginHelper.deleteUser();
                loginHelper.deleteLogin();
                break;
            case StaticVarriable.ERROR_INTERNET:
                vloading.setVisibility(View.GONE);
                ViewDialogForNotification dialog = new ViewDialogForNotification();
                dialog.showDialog(MainActivity.this, "Thông báo", "Kiểm tra kết nối internet", R.drawable.tick_box_icon);
                break;
            case StaticVarriable.SHOW_LOADING:
                vloading.setVisibility(View.VISIBLE);
                break;
            case StaticVarriable.HIDE_LOADING:
                vloading.setVisibility(View.GONE);
                break;


            default:
                break;
            }
        }
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

    @Override
    public void onBackPressed() {
        NotificationExitApp notificationExitApp = new NotificationExitApp(this);
        notificationExitApp.show();
    }

    private void getToken() {
        LoginHelper loginHelper = new LoginHelper(this);
        try {
            token = loginHelper.getUser().getToken();
        } catch (NullPointerException e) {
            Log.e("exception", e.toString());
        }
    }

}
