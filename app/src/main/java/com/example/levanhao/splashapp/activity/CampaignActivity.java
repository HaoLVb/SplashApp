package com.example.levanhao.splashapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.GirdProductAdapter;
import com.example.levanhao.splashapp.interfaces.OnItemClickListener;
import com.example.levanhao.splashapp.model.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CampaignActivity extends AppCompatActivity {
    private ImageView image;
    private IRecyclerView recyclerView;
    private GirdProductAdapter girdProductAdapter;
    private ArrayList<ProductItem> productItems;
    private CampaignHanlder campaignHanlder;
    public static String CAMPAIGN = "CAMPAIGN";
    public static String IMAGE_URL = "IMAGE_URL";
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        campaignId = getIntent().getIntExtra(CAMPAIGN, -1);
        imgUrl = getIntent().getStringExtra(IMAGE_URL);
        init();
        campaignHanlder = new CampaignHanlder();
        LoginActivity.requestManager.getListCampaignProduct(campaignId, 0, 30, MainActivity.token, campaignHanlder);
    }

    private int campaignId;
    private String imgUrl;
    private ImageView backIcon;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.trans_right_out);
    }

    private void init() {
        title = findViewById(R.id.title);
        if (campaignId == 1) {
            title.setText("Halloween");
        } else {
            title.setText("Giáng sinh");
        }
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        image = findViewById(R.id.image);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this)
                .load(imgUrl)
                .into(image);
        productItems = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        girdProductAdapter = new GirdProductAdapter(this);
        recyclerView.setIAdapter(girdProductAdapter);
        girdProductAdapter.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(int position, ProductItem productItem, View v) {
                Intent myIntent = new Intent(CampaignActivity.this, DetailProductActivity.class);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoginActivity.requestManager.getListCampaignProduct(campaignId, 0, 30, MainActivity.token, campaignHanlder);

            }
        });
    }

    private class CampaignHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.GET_LIST_CAMPAIGN_PROODUCT:
                JSONObject listProducts = (JSONObject) msg.obj;
                loadData(listProducts);
                break;
            case StaticVarriable.ERROR_INTERNET:
                recyclerView.setRefreshing(false);
                break;
            default:
                break;
            }
        }
    }

    private void loadData(JSONObject listProducts) {
        productItems.clear();
        try {
            JSONArray jsonArray = listProducts.getJSONArray("products");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                ProductItem productItem = new ProductItem(jsonObject);
                productItems.add(productItem);
            }
            recyclerView.setRefreshing(false);
            girdProductAdapter.setList(productItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
