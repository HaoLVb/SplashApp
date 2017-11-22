package com.example.levanhao.splashapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.StatusItem;

import java.util.ArrayList;

/**
 * Created by haova on 10/23/2017.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
    private ArrayList<StatusItem> statusItems;

    public StatusAdapter( ArrayList<StatusItem> statusItems) {
        this.statusItems = statusItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StatusItem statusItem = statusItems.get(position);
        holder.name.setText(statusItem.getName());
        if (statusItem.getDetail() != null) {
            holder.detail.setText(statusItem.getDetail());
        } else {
            holder.detail.setVisibility(View.GONE);
            holder.name.setPadding(0, 20, 0, 20);
        }

    }

    @Override
    public int getItemCount() {
        return statusItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView detail;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            detail = (TextView) view.findViewById(R.id.detail);
        }
    }
}
