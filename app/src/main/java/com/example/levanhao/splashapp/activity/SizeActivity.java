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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.ExhibitAdapter;
import com.example.levanhao.splashapp.adapter.SizeAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.model.product.CommentItem;
import com.example.levanhao.splashapp.model.product.Size;
import com.example.levanhao.splashapp.view.customview.DividerItemDecoration;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SizeActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_size);
        sizeHandler = new SizeHandler();
        LoginActivity.systemManager.getHandlerManager().setSizeHandler(sizeHandler);
        init();
    }

    private RecyclerView recyclerView;
    private ArrayList<Size> sizeItems;
    private ImageView backIcon;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private SizeAdapter adapter;
    private SizeHandler sizeHandler;
    private SwipeRefreshLayout refreshLayout;


    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sizeItems = new ArrayList<>();
        adapter = new SizeAdapter(sizeItems);
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
                returnIntent.putExtra(StaticVarriable.SIZE_STRING, sizeItems.get(position));
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vloading.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        LoginActivity.requestManager.getSize(sizeHandler);
                    }
                }, 2500);
            }
        });
        LoginActivity.requestManager.getSize(sizeHandler);

    }

    private class SizeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_SIZE:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadSize(jsonArray);
            default:
                break;
            }
        }
    }

    private void loadSize(JSONArray jsonArray) {
        sizeItems.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Size size = new Size(jsonObject);
                sizeItems.add(size);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
}