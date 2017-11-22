package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.model.product.Image;
import com.example.levanhao.splashapp.model.product.Video;

import java.util.ArrayList;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by thanglv on 8/17/2016.
 * Adapter cho View Pager của view trong Trang chi tiết sản phẩm
 */
public class ViewPagerImageProductsViewAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<Image> images;
    private ArrayList<Video> videos;

    public ViewPagerImageProductsViewAdapter(Context context, ArrayList<Image> images, ArrayList<Video> videos) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_image_products_for_view, container, false);
        ImageViewTouch imageView = itemView.findViewById(R.id.imViewProduct);
        if (images.get(position).getUrl() != null) {
            Glide.with(mContext)
                    .load(StaticVarriable.DOMAIN + "/" +images.get(position).getUrl())
                    .error(R.drawable.no_image)
                    .into(imageView);

        } else if (videos.get(position).getThumb().length() != 0) {
            Glide.with(mContext)
                    .load(StaticVarriable.DOMAIN + "/" +videos.get(position).getThumb())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.no_image);
        }


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
