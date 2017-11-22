package com.example.levanhao.splashapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.activity.UserInfoActivity;
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
                .load(StaticVarriable.DOMAIN + "/" + commentItem.getPoster().getAvatar())
                .placeholder(R.drawable.icon_no_avatar)
                .error(R.drawable.icon_no_avatar)
                .into(holder.posterAvatar);
        holder.comment.setText(commentItem.getComment());
//        holder.time.setText(StaticMethod.getTimerBefore(commentItem.getCreated_at()));
        holder.posterAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(context, UserInfoActivity.class);
                userIntent.putExtra(StaticVarriable.TOKEN, MainActivity.token);
                userIntent.putExtra(StaticVarriable.USER_ID, commentItem.getPoster().getId());
                ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
                context.startActivity(userIntent);
            }
        });

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
