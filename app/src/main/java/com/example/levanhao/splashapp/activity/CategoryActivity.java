package com.example.levanhao.splashapp.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.GirdProductAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.UserInfoPage;
import com.example.levanhao.splashapp.model.product.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        category = (Category) getIntent().getSerializableExtra("categoryId");
        categoryHandler = new CategoryHandler();
        LoginActivity.systemManager.getHandlerManager().setCategoryHandler(categoryHandler);
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        vloading.setVisibility(View.VISIBLE);
        init();
        LoginActivity.requestManager.getListCategoryProduct(category.getId(), 0, 30, MainActivity.token, categoryHandler);

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        girdProductAdapter = new GirdProductAdapter(this);
        recyclerView.setAdapter(girdProductAdapter);
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title = findViewById(R.id.title);
        title.setText(category.getName());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.trans_right_out);
    }

    private Category category;
    private CategoryHandler categoryHandler;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private RecyclerView recyclerView;
    private GirdProductAdapter girdProductAdapter;
    private TextView title;
    private ImageView backIcon;

    private class CategoryHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_LIST_CATEGORY_PROODUCT:
                JSONObject listProducts = (JSONObject) msg.obj;
                loadData(listProducts);
                break;
            default:
                break;
            }
        }
    }

    private void loadData(JSONObject listProducts) {
        JSONArray jsonArray = null;
        ArrayList<ProductItem> productItems = new ArrayList<>();
        try {
            jsonArray = listProducts.getJSONArray("products");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                ProductItem productItem = new ProductItem(jsonObject);
                Log.e("productItem", productItem.toString());
                productItems.add(productItem);
            }
            girdProductAdapter.setList(productItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
