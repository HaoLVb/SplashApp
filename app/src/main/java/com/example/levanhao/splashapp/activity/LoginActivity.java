package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.dialog.ViewDialogForNotification;
import com.example.levanhao.splashapp.manage.RequestManager;
import com.example.levanhao.splashapp.manage.SystemManager;
import com.example.levanhao.splashapp.model.LoginInfo;
import com.example.levanhao.splashapp.model.UserInformationModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity activity;
    public static SystemManager systemManager;
    public static RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        systemManager = new SystemManager(this);
        requestManager = new RequestManager(this);
        loginHandler = new LoginHandler();
        LoginActivity.systemManager.getHandlerManager().setLoginHandler(loginHandler);
        initViews();
        LoginHelper loginHelper = new LoginHelper(this);
        if (loginHelper.getLogin() != null) {
            Log.e("hhhh", new LoginHelper(this).getLogin().toString());
            LoginActivity.requestManager.login(loginHelper.getLogin().getPhoneNumber(), loginHelper.getLogin().getPassword(), loginHandler);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        activity = this;
    }

    private void initViews() {
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        passEdittext = findViewById(R.id.passEdittext);
        phoneEdittext = findViewById(R.id.phoneEdittext);
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(this);
    }

    private Button loginButton;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private EditText passEdittext;
    private EditText phoneEdittext;
    private LoginHandler loginHandler;
    private ViewDialogForNotification dialog;
    private Button skipButton;
    private String phoneNumber;
    private String password;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.loginButton:
            phoneNumber = phoneEdittext.getText().toString();
            password = passEdittext.getText().toString();
            dialog = new ViewDialogForNotification();
            StaticMethod.hideKeyboard(this);
            if (TextUtils.isEmpty(phoneNumber)) {
                dialog.showDialog(LoginActivity.this, "Lỗi", "Bạn chưa nhập số điện thoại", R.drawable.tick_box_icon);
            } else if (TextUtils.isEmpty(password)) {
                dialog.showDialog(LoginActivity.this, "Lỗi", "Bạn chưa nhập mật khẩu", R.drawable.tick_box_icon);
            } else {
                vloading.setVisibility(View.VISIBLE);
                LoginActivity.requestManager.login(phoneNumber, password, loginHandler);
            }
            break;
        case R.id.skipButton:
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            break;
        }
    }

    private class LoginHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.LOGIN_SUCCESSFUL:
                LoginInfo loginInfo = new LoginInfo(phoneNumber, password);
                LoginHelper loginHelper = new LoginHelper(LoginActivity.this);
                loginHelper.deleteLogin();
                loginHelper.addUser(loginInfo);
//               loadData
                JSONObject user = (JSONObject) msg.obj;
                UserInformationModel userInformationModel = new UserInformationModel(user);
                //add user information vào db (đảm bảo luôn chỉ có 1 user)
                loginHelper.deleteUser();
                loginHelper.addUser(userInformationModel);
                Log.e("sss2", userInformationModel.toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case StaticVarriable.LOGIN_FAIL:
                dialog.showDialog(LoginActivity.this, "Thông báo", "Số điện thoại hoặc mật khẩu không đúng. Vui lòng nhập lại", R.drawable.tick_box_icon);
                break;
            case StaticVarriable.ERROR_INTERNET:
                break;

            default:
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
