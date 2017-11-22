package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.StatusAdapter;
import com.example.levanhao.splashapp.view.customview.DividerItemDecoration;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;
import com.example.levanhao.splashapp.model.StatusItem;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_status);
        initArray();
        init();
    }

    private RecyclerView recyclerView;
    private ArrayList<StatusItem> statusItems;
    private StatusAdapter statusAdapter;
    private ImageView backIcon;


    private void initArray() {
        statusItems = new ArrayList<>();
        statusItems.add(new StatusItem(getResources().getString(R.string.new_status), getResources().getString(R.string.new_detail)));
        statusItems.add(new StatusItem(getResources().getString(R.string.new2_status), getResources().getString(R.string.new2_detail)));
        statusItems.add(new StatusItem(getResources().getString(R.string.good_status), getResources().getString(R.string.good_detail)));
        statusItems.add(new StatusItem(getResources().getString(R.string.good2_status), getResources().getString(R.string.good2_detail)));
        statusItems.add(new StatusItem(getResources().getString(R.string.old_status), getResources().getString(R.string.old_detail)));
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        statusAdapter = new StatusAdapter(statusItems);
        this.recyclerView.setAdapter(statusAdapter);
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(StaticVarriable.STATUS_STRING, statusItems.get(position).getName());
                setResult(Activity.RESULT_OK, returnIntent);
                onBackPressed();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
