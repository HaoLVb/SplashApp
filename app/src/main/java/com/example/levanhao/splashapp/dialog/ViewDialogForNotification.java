package com.example.levanhao.splashapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;


/**
 * Created by thanglv on 7/5/2016.
 */
public class ViewDialogForNotification {

    public void showDialog(Activity activity, String prob, String msg, int im) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_noti);

        TextView textProb = dialog.findViewById(R.id.tv_prob_dialog);
        textProb.setText(prob);
        TextView text = dialog.findViewById(R.id.tv_noti_dialog);
        text.setText(msg);
        ImageView imDialog = dialog.findViewById(R.id.imDialog);

        imDialog.setImageResource(im);
        imDialog.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_icon_dialog));

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
}