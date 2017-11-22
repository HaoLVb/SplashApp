package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;

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
        initArray();
        init();
    }

    private ListView listView;
    private ArrayList<String> sizeItems;
    private ArrayAdapter<String> sizeAdapter;
    private ImageView backIcon;


    private void initArray() {
        sizeItems = new ArrayList<>();
        sizeItems.add(getResources().getString(R.string.all));
        sizeItems.add(getResources().getString(R.string._0_3));
        sizeItems.add(getResources().getString(R.string._1));
        sizeItems.add(getResources().getString(R.string._1_2));
        sizeItems.add(getResources().getString(R.string._10));
        sizeItems.add(getResources().getString(R.string._10_12));
        sizeItems.add(getResources().getString(R.string._11));
        sizeItems.add(getResources().getString(R.string._11_12));
    }

    private void init() {
        listView = findViewById(R.id.listView);
        sizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sizeItems);
        this.listView.setAdapter(sizeAdapter);
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(StaticVarriable.SIZE_STRING, sizeItems.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                onBackPressed();
            }
        });

    }
}
