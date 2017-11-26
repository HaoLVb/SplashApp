package com.example.levanhao.splashapp.view.customview.tutorial;

/**
 * Created by HoangNV on 11/15/2017.
 */

public class Frame {
    public int height;
    public int width;
    public int f168x;
    public int f169y;

    public Frame(int x, int y, int with, int height) {
        this.f168x = x;
        this.f169y = y;
        this.width = with;
        this.height = height;
    }

    public static Frame FrameMake(int x, int y, int width, int height) {
        return new Frame(x, y, width, height);
    }
}
