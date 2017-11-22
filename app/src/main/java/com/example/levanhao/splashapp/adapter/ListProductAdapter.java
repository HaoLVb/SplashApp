package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.List;

/**
 * Created by HaoLV on 11/10/2017.
 */

public class ListProductAdapter extends RecyclerView.Adapter<IViewHolder> {

    private ArrayList<ProductItem> mProductItem;
    private Context context;

    public ListProductAdapter(Context context) {
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
                .inflate(R.layout.item_listview_main, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IViewHolder viewHolder, int position) {
        ProductItem productItem = mProductItem.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(context)
                .load(StaticVarriable.DOMAIN + "/" + productItem.getSeller().getAvatar())
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(holder.iconUser);
        holder.userNname.setText(productItem.getSeller().getName());
        holder.nameProduct.setText(productItem.getName());
        holder.priceProduct.setText(StaticMethod.formatPrice(String.valueOf(productItem.getPrice())));
        holder.description.setText(productItem.getDescribed());
        holder.like.setText(String.valueOf(productItem.getLike()));
        holder.comment.setText(String.valueOf(productItem.getNumber_comment()));
        if (productItem.getImages() != null && productItem.getImages().size() > 0) {
//
            Glide.with(context)
                    .load(StaticVarriable.DOMAIN + "/" + productItem.getImages().get(0).getUrl())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imgProduct);


        }
        if (productItem.isIs_liked()) {
            holder.likeImage.setImageResource(R.drawable.icon_like_on);
        } else {
            holder.likeImage.setImageResource(R.drawable.icon_like_off);
        }
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getIAdapterPosition();
                final ProductItem item = mProductItem.get(position);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, item, v);
                }
            }
        });
        holder.likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductItem.size();
    }


    public static class ViewHolder extends IViewHolder {
        public ImageView iconUser;
        public TextView userNname;
        public ImageView imgProduct;
        public TextView nameProduct;
        public TextView priceProduct;
        private TextView like;
        private TextView comment;
        private TextView description;
        private ImageView likeImage;
        private LinearLayout likeLayout;


        public ViewHolder(View view) {
            super(view);
            iconUser = view.findViewById(R.id.iconUser);
            userNname = view.findViewById(R.id.userNname);
            imgProduct = view.findViewById(R.id.imgProduct);
            nameProduct = view.findViewById(R.id.nameProduct);
            priceProduct = view.findViewById(R.id.priceProduct);
            like = view.findViewById(R.id.like);
            comment = view.findViewById(R.id.comment);
            description = view.findViewById(R.id.description);
            likeImage = view.findViewById(R.id.likeImage);
            likeLayout = view.findViewById(R.id.likeLayout);
        }
    }
}
