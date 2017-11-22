package com.example.levanhao.splashapp.fragment.favorite;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.activity.DetailProductActivity;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.adapter.FavoriteAdapter;
import com.example.levanhao.splashapp.view.customview.DividerItemDecoration;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.product.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }


    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteHandler = new FavoriteHandler();
        LoginActivity.systemManager.getHandlerManager().setFavoriteHandler(favoriteHandler);

        this.favoriteRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        this.favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.favoriteRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        productItems = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(context, productItems);
        this.favoriteRecyclerView.setAdapter(favoriteAdapter);
        this.favoriteRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, this.favoriteRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent myIntent = new Intent(context, DetailProductActivity.class);
                ProductItem productItem = productItems.get(position);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        LoginActivity.requestManager.getMyLike(MainActivity.token, String.valueOf(0), String.valueOf(20), favoriteHandler);
        LoginActivity.systemManager.getHandlerManager().sendMessage(
                LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                StaticVarriable.SHOW_LOADING);
        return view;
    }

    private View view;
    private Context context;
    private RecyclerView favoriteRecyclerView;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<ProductItem> productItems;
    private FavoriteHandler favoriteHandler;

    private class FavoriteHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.GET_MY_LIKE:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadData(jsonArray);
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.HIDE_LOADING);
                break;
            case StaticVarriable.ERROR_INTERNET:
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.HIDE_LOADING);
                break;

            default:
                break;
            }
        }
    }

    private void loadData(JSONArray jsonArray) {
        Log.e("dashdajk", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.e("andas", jsonObject.toString());
                ProductItem productItem = new ProductItem();
                productItem.setId(jsonObject.getInt("id"));
                JSONArray imageArray = jsonObject.getJSONArray("images");
                ArrayList<Image> images = new ArrayList<>();
                for (int j = 0; j < imageArray.length(); j++) {
                    Image image = new Image(imageArray.getJSONObject(i));
                    images.add(image);
                }
                productItem.setName(jsonObject.getString("name"));
                productItem.setImages(images);
                productItem.setPrice(jsonObject.getInt("price"));
                productItems.add(productItem);
                Log.e("ádaaa", String.valueOf(productItem.getPrice()));
                Log.e("favorite4", productItem.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        favoriteAdapter.notifyDataSetChanged();
    }


}
