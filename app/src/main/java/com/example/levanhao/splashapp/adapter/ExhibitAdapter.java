package com.example.levanhao.splashapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.model.ExhibitItem;

import java.util.ArrayList;

/**
 * Created by HaoVanLe on 10/29/2017.
 */
public class ExhibitAdapter extends RecyclerView.Adapter<ExhibitAdapter.MyViewHolder> {
    private ArrayList<ExhibitItem> exhibitItems;

    public ExhibitAdapter(ArrayList<ExhibitItem> exhibitItems) {
        this.exhibitItems = exhibitItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibit_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ExhibitItem exhibitItem = exhibitItems.get(position);
        holder.name.setText(exhibitItem.getName());
    }

    @Override
    public int getItemCount() {
        return exhibitItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

        }
    }
}

