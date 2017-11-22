package com.example.levanhao.splashapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.model.NavigationItem;

public class NavigationItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<NavigationItem> arr;
    private Typeface fontRobotoLight;

    public NavigationItemAdapter(Context context, ArrayList<NavigationItem> arr) {
        super();
        this.context = context;
        this.arr = arr;
        mInflater = LayoutInflater.from(context);
        this.fontRobotoLight = Typeface.createFromAsset(this.context.getAssets(), "fonts/Roboto-Light.ttf");

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = 0;
        if (arr != null) {
            count = arr.size();
        }
        return count;
    }

    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return arr.get(pos);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        viewHolder holder;
        if (convertView == null) {
            holder = new viewHolder();
            convertView = mInflater.inflate(R.layout.layout_navigation_item, parent, false);
            holder.itemIcon = (ImageView) convertView.findViewById(R.id.itemIcon);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.nextIcon = (ImageView) convertView.findViewById(R.id.nextIcon);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        final NavigationItem navigationItem = arr.get(position);

        if (navigationItem != null) {
            holder.itemIcon.setImageResource(navigationItem.getItemIcon());
            holder.itemName.setText(navigationItem.getItemName());
            holder.itemName.setTypeface(fontRobotoLight);
            if (navigationItem.isSelected()) {
                holder.itemName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.nextIcon.setImageResource(R.drawable.sidemenu_next_arrow_pressed);
            } else {
                holder.itemName.setTextColor(ContextCompat.getColor(context, R.color.black));
                holder.nextIcon.setImageResource(R.drawable.sidemenu_next_arrow_normal);
            }
        }
        return convertView;
    }

    public class viewHolder {
        public TextView itemName;
        public ImageView itemIcon;
        public ImageView nextIcon;
    }
}
