package com.example.levanhao.splashapp.view.customview.tutorial;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Auth {
    private static final String ADD_PRODUCT = "_add_product_";
    private static final String ADD_PRODUCT_PREF = "_add_product_pref_";
    private static final String AUTH_PREF = "auth";
    private static final String AVATAR = "avatar";
    private static final String BACK_TIMESTAMP_FOR_CONFIRM_CODE = "confirm_code_timeStamp";
    private static final String BACK_TIMESTAMP_FOR_WITHDRAW = "back_timeStamp";
    private static final String BUY_TUT_CHECK = "buy_tut";
    private static final String CATEGORY_KEY = "_category_";
    private static final String CONFIRM_CODE_KEY = "confirm_code_";
    private static final String COVERIMAGE = "coverImage";
    private static final String DEFAULT_BANK = "default_bank";
    private static final String ID = "id";
    private static final String INTRO_TUT_CHECK = "intro_tut";
    private static final String LAST_PURCHASE_ADDRESS = "last_purchase_address";
    private static final String LAST_UPDATE = "last_update";
    private static final String NAME_TRANSACTION = "_name_transaction_";
    private static final String NOTIFICATION_ID = "noti_id";
    private static final String NUMBER_LOGIN = "_number_login_";
    private static final String NUMBER_LOGIN_PREF = "_number_login_";
    private static final String PAYMENT_TRANSACTION = "_payment_transaction_";
    private static final String RECENTLY_INTRO_PREF = "_recently_intro_pref_";
    private static final String RECENTLY_SEARCH = "_recently_search_";
    private static final String RECENTLY_SEARCH_PREF = "_recently_search_pref_";
    private static final String RELOAD_MAIN_VIEW = "reload_main_";
    private static final String REMAIN_TIME_FOR_CONFIRM_CODE = "confirm_code_remain_time";
    private static final String REMAIN_TIME_FOR_WITHDRAW = "remain_time";
    private static final String SHOW = "_show_";
    private static final String SHOW_POPUP = "show_popup";
    private static final String START_UP_INTRO_TUT_CHECK = "intro_tut";
    private static final String STATUS = "active";
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";

    public static long getBackTimestampForConfirmCode(Context context, String phoneNumber) {
        return context.getSharedPreferences(CONFIRM_CODE_KEY + phoneNumber, 0).getLong(BACK_TIMESTAMP_FOR_CONFIRM_CODE, 0);
    }

    public static boolean setBackTimestampForConfirmCode(Context mContext, long timestamp, String phoneNumber) {
        Editor auth_editor = mContext.getSharedPreferences(CONFIRM_CODE_KEY + phoneNumber, 0).edit();
        auth_editor.putLong(BACK_TIMESTAMP_FOR_CONFIRM_CODE, timestamp);
        return auth_editor.commit();
    }

    public static long getRemainTimeForConfirmCode(Context context, String phoneNumber) {
        return context.getSharedPreferences(CONFIRM_CODE_KEY + phoneNumber, 0).getLong(REMAIN_TIME_FOR_CONFIRM_CODE, 0);
    }

    public static boolean setRemainTimeForConfirmCode(Context mContext, long remainTime, String phoneNumber) {
        Editor auth_editor = mContext.getSharedPreferences(CONFIRM_CODE_KEY + phoneNumber, 0).edit();
        auth_editor.putLong(REMAIN_TIME_FOR_CONFIRM_CODE, remainTime);
        return auth_editor.commit();
    }

    public static String getIntroTutCheck(Context context) {
        return context.getSharedPreferences(RECENTLY_INTRO_PREF, 0).getString("intro_tut", AppEventsConstants.EVENT_PARAM_VALUE_NO);
    }

    public static boolean setIntroTutCheck(Context mContext, String check) {
        Editor auth_editor = mContext.getSharedPreferences(RECENTLY_INTRO_PREF, 0).edit();
        auth_editor.putString("intro_tut", check);
        return auth_editor.commit();
    }

    public static String getStartUpIntroTutCheck(Context context) {
        return context.getSharedPreferences("intro_tut", 0).getString("intro_tut", AppEventsConstants.EVENT_PARAM_VALUE_NO);
    }

    public static boolean setStartUpIntroTutCheck(Context mContext, String check) {
        Editor auth_editor = mContext.getSharedPreferences("intro_tut", 0).edit();
        auth_editor.putString("intro_tut", check);
        return auth_editor.commit();
    }

    public static String getBuyTutCheck(Context context) {
        return context.getSharedPreferences(BUY_TUT_CHECK, 0).getString(BUY_TUT_CHECK, AppEventsConstants.EVENT_PARAM_VALUE_NO);
    }

    public static boolean setBuyTutCheck(Context mContext, String check) {
        Editor auth_editor = mContext.getSharedPreferences(BUY_TUT_CHECK, 0).edit();
        auth_editor.putString(BUY_TUT_CHECK, check);
        return auth_editor.commit();
    }

    public static int getNotiId(Context context) {
        return context.getSharedPreferences("auth", 0).getInt(NOTIFICATION_ID, 0);
    }

    public static boolean setNotiId(Context mContext, int id) {
        Editor auth_editor = mContext.getSharedPreferences("auth", 0).edit();
        auth_editor.putInt(NOTIFICATION_ID, id);
        return auth_editor.commit();
    }



    public static boolean eraseAuthData(Context mContext) {
        boolean z = false;
        try {
            z = mContext.getSharedPreferences("auth", 0).edit().clear().commit();
        } catch (Exception e) {
        }
        return z;
    }
}
