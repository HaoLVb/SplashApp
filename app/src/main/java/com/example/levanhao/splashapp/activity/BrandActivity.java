package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.BrandAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.model.product.Brand;
import com.example.levanhao.splashapp.model.product.Size;
import com.example.levanhao.splashapp.view.customview.DividerItemDecoration;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrandActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.backIcon:
            onBackPressed();
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.trans_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        brandHandler = new BrandHandler();
        LoginActivity.systemManager.getHandlerManager().setBrandHandler(brandHandler);
        init();
    }

    private RecyclerView recyclerView;
    private ArrayList<Brand> brands;
    private ImageView backIcon;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private BrandAdapter adapter;
    private BrandHandler brandHandler;
    private SwipeRefreshLayout refreshLayout;

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        brands = new ArrayList<>();
        adapter = new BrandAdapter(brands);
        recyclerView.setAdapter(adapter);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        backIcon = findViewById(R.id.backIcon);
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        backIcon.setOnClickListener(this);
        vloading.setVisibility(View.VISIBLE);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(StaticVarriable.BRAND_STRING, brands.get(position).getBrand_name());
                setResult(Activity.RESULT_OK, returnIntent);
                onBackPressed();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vloading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        LoginActivity.requestManager.getBrand(brandHandler);
                    }
                }, 2500);
            }
        });
        LoginActivity.requestManager.getBrand(brandHandler);

    }

    private class BrandHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_BRAND:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadBrand(jsonArray);
            default:
                break;
            }
        }
    }

    private void loadBrand(JSONArray jsonArray) {
        brands.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Brand brand = new Brand(jsonObject);
                brands.add(brand);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
