package com.example.levanhao.splashapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.model.ExhibitItem;
import com.example.levanhao.splashapp.model.product.Brand;

import java.util.ArrayList;

/**
 * Created by HaoLV on 11/23/2017.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {
    private ArrayList<Brand> brands;

    public BrandAdapter(ArrayList<Brand> brands) {
        this.brands = brands;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibit_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.name.setText(brand.getBrand_name());
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

        }
    }
}

