package com.example.levanhao.splashapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by HaoLV on 11/13/2017.
 */

public class StaticMethod {
    public static String getTimerBefore(String created) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        long second = calendar.getTimeInMillis() / 1000L;
        long secondBetween = second - Long.parseLong(created);
        if (secondBetween < 60) {
            return (secondBetween + " giây trước");
        } else {
            if ((secondBetween / 60) < 60) {
                return (secondBetween / 60) + " phút trước";
            } else {
                if ((secondBetween / 3600) < 24) {
                    return (secondBetween / 3600) + " giờ trước";
                } else {
                    if ((secondBetween / (3600 * 24)) < 30) {
                        return (secondBetween / (3600 * 24)) + " ngày trước";
                    } else {
                        if ((secondBetween / (3600 * 24 * 30)) < 12) {
                            return (secondBetween / (3600 * 24 * 30)) + " tháng trước";
                        } else {
                            return (secondBetween / (3600 * 24 * 30 * 12)) + " năm trước";
                        }
                    }

                }
            }
        }
    }

    public static void showKeyboard(EditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText,
                InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String formatPrice(String price) {
        DecimalFormat formatter = new DecimalFormat("#,###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return formatter.format(Double.parseDouble(price)) + " VNĐ";
    }


    public static String[] formatVNĐ(String[] pk) {
        String[] formatPrice = new String[12];
        for (int i = 0; i < pk.length; i++) {
            formatPrice[i] = formatPrice(pk[i]);
        }
        return formatPrice;
    }


    public static String formatPriceGird(String price) {
        String fomat = "";
        int cost = Integer.parseInt(price);
        if ((cost >= 1000) && (cost < 1000000)) {
            fomat = "" + (cost / 1000) + "k";
        } else if (cost >= 1000000) {
            fomat = "" + (cost / 1000000) + "M";
        } else fomat = "" + cost;
        return fomat;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean checkIsLogin(Context context) {
        LoginHelper helper = new LoginHelper(context);
        return helper.getUser() != null;
    }

}
