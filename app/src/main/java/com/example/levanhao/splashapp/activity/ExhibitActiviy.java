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
import com.example.levanhao.splashapp.adapter.ExhibitAdapter;
import com.example.levanhao.splashapp.view.customview.DividerItemDecoration;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;
import com.example.levanhao.splashapp.model.ExhibitItem;

import java.util.ArrayList;

public class ExhibitActiviy extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_list_activiy);
        add = getIntent().getBooleanExtra(StaticVarriable.EXHIBIT_BOOLEAN, false);
        initArray();
        init();
    }

    private RecyclerView recyclerView;
    private ArrayList<ExhibitItem> exhibitItems;
    private ExhibitAdapter exhibitAdapter;
    private ImageView backIcon;
    private boolean add;

    private void initArray() {
        exhibitItems = new ArrayList<>();
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.kid_eat), 1));
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.kid_wear), 2));
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.kid_sleep), 3));
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.kid_bathe), 4));
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.kid_hygiene), 5));
        exhibitItems.add(new ExhibitItem(getResources().getString(R.string.strong_kid), 6));
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        exhibitAdapter = new ExhibitAdapter(exhibitItems);
        this.recyclerView.setAdapter(exhibitAdapter);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(StaticVarriable.EXHIBIT_STRING, exhibitItems.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                onBackPressed();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
