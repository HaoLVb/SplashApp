package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.model.product.CommentItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaoLV on 11/13/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private ArrayList<CommentItem> commentItems;
    private Context context;

    public CommentAdapter(ArrayList<CommentItem> commentItems, Context context) {
        this.commentItems = commentItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CommentItem commentItem = commentItems.get(position);
        holder.posterName.setText(commentItem.getPoster().getName());
        Glide.with(context)
                .load(StaticVarriable.DOMAIN + "/" +commentItem.getPoster().getAvatar())
                .placeholder(R.drawable.icon_no_avatar)
                .error(R.drawable.icon_no_avatar)
                .into(holder.posterAvatar);
        holder.comment.setText(commentItem.getComment());
//        holder.time.setText(StaticMethod.getTimerBefore(commentItem.getCreated_at()));

    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView posterName;
        CircleImageView posterAvatar;
        TextView comment;
        TextView time;

        MyViewHolder(View view) {
            super(view);
            posterName = view.findViewById(R.id.posterName);
            posterAvatar = view.findViewById(R.id.posterAvatar);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
        }
    }
}
