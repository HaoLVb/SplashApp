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
        this.reportHandler = null;

        this.kidEatHandler = null;
        this.kidWearHandler = null;
        this.kidSleepHandler = null;
        this.kidShowerHandler = null;
        this.allFragmentHandler = null;

        this.sizeHandler = null;
        this.brandHandler = null;
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


    public Handler getKidEatHandler() {
        return kidEatHandler;
    }

    public void setKidEatHandler(Handler kidEatHandler) {
        this.kidEatHandler = kidEatHandler;
    }

    public Handler getKidWearHandler() {
        return kidWearHandler;
    }

    public void setKidWearHandler(Handler kidWearHandler) {
        this.kidWearHandler = kidWearHandler;
    }

    public Handler getKidSleepHandler() {
        return kidSleepHandler;
    }

    public void setKidSleepHandler(Handler kidSleepHandler) {
        this.kidSleepHandler = kidSleepHandler;
    }

    public Handler getKidShowerHandler() {
        return kidShowerHandler;
    }

    public void setKidShowerHandler(Handler kidShowerHandler) {
        this.kidShowerHandler = kidShowerHandler;
    }

    public Handler getAllFragmentHandler() {
        return allFragmentHandler;
    }

    public void setAllFragmentHandler(Handler allFragmentHandler) {
        this.allFragmentHandler = allFragmentHandler;
    }

    public Handler getSizeHandler() {
        return sizeHandler;
    }

    public void setSizeHandler(Handler sizeHandler) {
        this.sizeHandler = sizeHandler;
    }

    public Handler getBrandHandler() {
        return brandHandler;
    }

    public void setBrandHandler(Handler brandHandler) {
        this.brandHandler = brandHandler;
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
    private Handler reportHandler;

    private Handler kidEatHandler;
    private Handler kidWearHandler;
    private Handler kidSleepHandler;
    private Handler kidShowerHandler;
    private Handler allFragmentHandler;

    private Handler sizeHandler;
    private Handler brandHandler;

}
