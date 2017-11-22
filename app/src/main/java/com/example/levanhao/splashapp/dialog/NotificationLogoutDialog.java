package com.example.levanhao.splashapp.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.activity.LoginActivity;

/**
 * Created by HaoLV on 11/7/2017.
 */

public class NotificationLogoutDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private RelativeLayout viewGroup;
    private Button btDialogExit;
    private Button btDialogAccept;
    private OnDialogClickListener onDialogClickListener;

    public interface OnDialogClickListener {
        void onClickOk();
    }

    public NotificationLogoutDialog(Context context, OnDialogClickListener onDialogClickListener) {
        super(context);
        this.context = context;
        this.onDialogClickListener = onDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_notification_dialog);
        viewGroup = (RelativeLayout) findViewById(R.id.layoutNotificationDialog);
        btDialogAccept = (Button) findViewById(R.id.btDialogAccept);
        btDialogExit = (Button) findViewById(R.id.btDialogExit);
        animateDialog();
        btDialogExit.setOnClickListener(this);
        btDialogAccept.setOnClickListener(this);
    }

    private void animateDialog() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewGroup, "ScaleX", 0.7f, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewGroup, "ScaleY", 0.7f, 1);
        set.playTogether(animatorX, animatorY);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(500);
        set.start();
    }

    @Override
    public void onClick(View v) {
        if (v == btDialogAccept) {
            dismiss();
            onDialogClickListener.onClickOk();
        } else if (v == btDialogExit) {
            dismiss();
        }
    }
}
