package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;

public class Utils {


    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (VERSION.SDK_INT < 13) {
            return display.getWidth();
        }
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }


}
