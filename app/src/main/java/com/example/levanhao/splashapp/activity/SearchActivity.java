package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.backIcon:
            onBackPressed();
            break;
        case R.id.deleteIcon:
            break;
        case R.id.statusLayout:
            Intent intent = new Intent(SearchActivity.this, StatusActivity.class);
            startActivityForResult(intent, StaticVarriable.STATUS_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.exhibitLayout:
            Intent exhibitIntent = new Intent(SearchActivity.this, ExhibitActiviy.class);
            exhibitIntent.putExtra(StaticVarriable.EXHIBIT_BOOLEAN, true);
            startActivityForResult(exhibitIntent, StaticVarriable.EXHIBIT_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.sizeLayout:
            Intent sizeIntent = new Intent(SearchActivity.this, SizeActivity.class);
            startActivityForResult(sizeIntent, StaticVarriable.SIZE_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
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
        setContentView(R.layout.activity_search);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case StaticVarriable.STATUS_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.STATUS_STRING);
                statusText.setText(result);
                statusText.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            break;
        case StaticVarriable.EXHIBIT_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.EXHIBIT_STRING);
                exhibitText.setText(result);
                exhibitText.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            break;
        case StaticVarriable.SIZE_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.SIZE_STRING);
                sizeText.setText(result);
                sizeText.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            break;
        }
    }

    private ImageView backIcon;
    private ImageView deleteIcon;
    private TextView statusText;
    private LinearLayout statusLayout;
    private TextView exhibitText;
    private LinearLayout exhibitLayout;
    private LinearLayout sizeLayout;
    private TextView sizeText;

    private void init() {
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        deleteIcon = findViewById(R.id.deleteIcon);
        deleteIcon.setOnClickListener(this);
        statusText = findViewById(R.id.statusText);
        statusLayout = findViewById(R.id.statusLayout);
        statusLayout.setOnClickListener(this);
        exhibitText = findViewById(R.id.exhibitText);
        exhibitLayout = findViewById(R.id.exhibitLayout);
        exhibitLayout.setOnClickListener(this);
        sizeText = findViewById(R.id.sizeText);
        sizeLayout = findViewById(R.id.sizeLayout);
        sizeLayout.setOnClickListener(this);
    }
}
