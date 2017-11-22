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
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.model.UserInformationModel;
import com.example.levanhao.splashapp.model.UserItemFollowModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaoLV on 11/17/2017.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserItemFollowModel> userInformationModels;

    public FollowAdapter(Context context, ArrayList<UserItemFollowModel> userInformationModels) {
        this.context = context;
        this.userInformationModels = userInformationModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_follow_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        UserItemFollowModel model = userInformationModels.get(position);

        Glide.with(context)
                .load(StaticVarriable.DOMAIN + "/" +model.getAvatar())
                .placeholder(R.drawable.icon_no_avatar)
                .error(R.drawable.icon_no_avatar)
                .into(holder.avatar);

        holder.name.setText(model.getUsername());
    }

    @Override
    public int getItemCount() {
        return userInformationModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        ImageView add;
        TextView name;

        MyViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            add = view.findViewById(R.id.add);
            name = view.findViewById(R.id.name);
        }
    }
}

