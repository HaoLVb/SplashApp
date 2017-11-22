package com.example.levanhao.splashapp.manage;

import android.content.Context;
import android.os.Handler;

import com.example.levanhao.splashapp.LoginHelper;

/**
 * Created by LeVanHao on 10/16/2017.
 */

public class SystemManager {

    public SystemManager(Context context) {
        this.handlerManager = new HandlerManager();
    }

    public HandlerManager getHandlerManager() {
        return handlerManager;
    }

    private HandlerManager handlerManager;
    private Context context;
}
