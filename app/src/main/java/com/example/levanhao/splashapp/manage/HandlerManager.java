package com.example.levanhao.splashapp.manage;

import android.os.Handler;
import android.os.Message;

/**
 * Created by LeVanHao on 10/16/2017.
 */

public class HandlerManager {
    public HandlerManager() {
        this.mainHomeHandler = null;
        this.mainHandler = null;
        this.loginHandler = null;
        this.detailHandler = null;
        this.commentHandler = null;
        this.userInfoHandler = null;
        this.favoriteHandler = null;
        this.followingHandler = null;
        this.followedHandler = null;
        this.userListingHandler = null;
        this.categoryHandler = null;
        this.categoryProductHandler = null;
        this.reportHandler = null;
    }

    public void sendMessage(Handler handler, int what, Object object) {
        Message message = new Message();
        message.what = what;
        message.obj = object;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    public void sendMessage(Handler handler, int what) {
        Message message = new Message();
        message.what = what;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    public Handler getMainHomeHandler() {
        return mainHomeHandler;
    }

    public void setMainHomeHandler(Handler mainHomeHandler) {
        this.mainHomeHandler = mainHomeHandler;
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    public Handler getLoginHandler() {
        return loginHandler;
    }

    public void setLoginHandler(Handler loginHandler) {
        this.loginHandler = loginHandler;
    }

    public Handler getDetailHandler() {
        return detailHandler;
    }

    public void setDetailHandler(Handler detailHandler) {
        this.detailHandler = detailHandler;
    }

    public Handler getCommentHandler() {
        return commentHandler;
    }

    public void setCommentHandler(Handler commentHandler) {
        this.commentHandler = commentHandler;
    }

    public Handler getUserInfoHandler() {
        return userInfoHandler;
    }

    public void setUserInfoHandler(Handler userInfoHandler) {
        this.userInfoHandler = userInfoHandler;
    }

    public Handler getFavoriteHandler() {
        return favoriteHandler;
    }

    public void setFavoriteHandler(Handler favoriteHandler) {
        this.favoriteHandler = favoriteHandler;
    }

    public Handler getFollowingHandler() {
        return followingHandler;
    }

    public void setFollowingHandler(Handler followingHandler) {
        this.followingHandler = followingHandler;
    }

    public Handler getReportHandler() {
        return reportHandler;
    }

    public void setReportHandler(Handler reportHandler) {
        this.reportHandler = reportHandler;
    }

    public Handler getUserListingHandler() {
        return userListingHandler;
    }

    public void setUserListingHandler(Handler userListingHandler) {
        this.userListingHandler = userListingHandler;
    }

    public Handler getFollowedHandler() {
        return followedHandler;
    }

    public void setFollowedHandler(Handler followedHandler) {
        this.followedHandler = followedHandler;
    }

    public Handler getCategoryHandler() {
        return categoryHandler;
    }

    public void setCategoryHandler(Handler categoryHandler) {
        this.categoryHandler = categoryHandler;
    }

    public Handler getCategoryProductHandler() {
        return categoryProductHandler;
    }

    public void setCategoryProductHandler(Handler categoryProductHandler) {
        this.categoryProductHandler = categoryProductHandler;
    }


    private Handler mainHomeHandler;
    private Handler mainHandler;
    private Handler loginHandler;
    private Handler detailHandler;
    private Handler commentHandler;
    private Handler userInfoHandler;
    private Handler favoriteHandler;
    private Handler followingHandler;
    private Handler followedHandler;
    private Handler userListingHandler;
    private Handler categoryHandler;
    private Handler categoryProductHandler;
    private Handler reportHandler;

}
