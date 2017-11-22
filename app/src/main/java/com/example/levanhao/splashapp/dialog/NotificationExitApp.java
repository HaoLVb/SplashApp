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


public class NotificationExitApp extends Dialog implements View.OnClickListener {
    private Context context;
    private RelativeLayout viewGroup;
    private Button btDialogExit;
    private Button btDialogAccept;

    public NotificationExitApp(Context context) {
        super(context);
        this.context = context;
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exitapp_notification_dialog);
        viewGroup = findViewById(R.id.layoutNotificationDialog);
        btDialogAccept = findViewById(R.id.btDialogAccept);
        btDialogExit = findViewById(R.id.btDialogExit);
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
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getOwnerActivity().startActivity(homeIntent);

        } else if (v == btDialogExit) {
            dismiss();
        }
    }
}

