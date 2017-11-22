package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.irecyclerview.IViewHolder;
import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.interfaces.OnItemClickListener;
import com.example.levanhao.splashapp.interfaces.OnLoadMoreListener;
import com.example.levanhao.splashapp.model.ProductItem;

import java.util.ArrayList;

/**
 * Created by LeVanHao on 10/6/2017.
 */

public class GirdProductAdapter extends RecyclerView.Adapter<IViewHolder> {

    private ArrayList<ProductItem> mProductItem;
    private Context context;

    public GirdProductAdapter(Context context) {
        this.context = context;
        this.mProductItem = new ArrayList<>();
    }

    private OnItemClickListener<ProductItem> mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener<ProductItem> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setList(ArrayList<ProductItem> loadItems) {
        this.mProductItem.clear();
        this.mProductItem.addAll(loadItems);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<ProductItem> loadItems) {
        this.mProductItem.addAll(loadItems);
        notifyDataSetChanged();
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gird_product_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IViewHolder viewHolder, int position) {
        ProductItem productItem = mProductItem.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.title.setText(productItem.getName());
        holder.commentTextview.setText(String.valueOf(productItem.getNumber_comment()));
        holder.likeTextView.setText(String.valueOf(productItem.getLike()));
//        holder.priceTextView.setText((productItem.getPrice() / 1000) + "K");
        holder.priceTextView.setText(StaticMethod.formatPriceGird(String.valueOf(productItem.getPrice())));
        Log.e("prhhh", productItem.toString());
        if (productItem.getImages() != null && productItem.getImages().size() > 0) {

            Glide.with(context)
                    .load(StaticVarriable.DOMAIN + "/" + productItem.getImages().get(0).getUrl())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.productImage);
        }

        if (productItem.isIs_liked()) {
            holder.likeImage.setImageResource(R.drawable.grid_heart_on);
        } else {
            holder.likeImage.setImageResource(R.drawable.grid_heart_off);
        }
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getIAdapterPosition();
                final ProductItem item = mProductItem.get(position);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, item, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductItem.size();
    }


    public static class ViewHolder extends IViewHolder {
        public ImageView productImage;
        public TextView title;
        public TextView likeTextView;
        public TextView commentTextview;
        public TextView priceTextView;
        public ImageView likeImage;


        public ViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            title = view.findViewById(R.id.title);
            likeTextView = view.findViewById(R.id.likeTextView);
            commentTextview = view.findViewById(R.id.commentTextview);
            priceTextView = view.findViewById(R.id.priceTextView);
            likeImage = view.findViewById(R.id.likeImage);
        }
    }
}

