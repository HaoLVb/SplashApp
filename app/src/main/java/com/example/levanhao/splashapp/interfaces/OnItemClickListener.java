package com.example.levanhao.splashapp.interfaces;

import android.view.View;


public interface OnItemClickListener<T> {
    void onItemClick(int position, T t, View v);
}
