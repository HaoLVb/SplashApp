package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.model.ProductItem;

import java.util.ArrayList;

/**
 * Created by LeVanHao on 10/11/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ProductItem> productItems;

    public FavoriteAdapter(Context context, ArrayList<ProductItem> productItems) {
        this.context = context;
        this.productItems = productItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductItem productItem = productItems.get(position);
        Log.e("skjdak", productItem.toString());
        if (productItem.getImages() != null && productItem.getImages().size() > 0) {
            Glide.with(context)
                    .load(StaticVarriable.DOMAIN + "/" +productItem.getImages().get(0).getUrl())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.productImage);
        }
        holder.productName.setText(productItem.getName());
        holder.productPrice.setText(StaticMethod.formatPrice(String.valueOf(productItem.getPrice())));

    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        MyViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
        }
    }
}
