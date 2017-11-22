package com.example.levanhao.splashapp.view.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class HRecycleView extends RecyclerView {
    public HRecycleView(Context context) {
        super(context);
    }

    public HRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        super.setOnScrollListener(listener);
    }
}
