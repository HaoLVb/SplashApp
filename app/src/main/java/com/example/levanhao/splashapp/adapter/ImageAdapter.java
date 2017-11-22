package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.levanhao.splashapp.R;

import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by haova on 10/20/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Bitmap> bitmaps;

    public ImageAdapter(Context context, ArrayList<Bitmap> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Bitmap bitmap = bitmaps.get(position);
        holder.image.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}
