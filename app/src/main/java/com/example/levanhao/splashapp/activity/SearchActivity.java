package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.GirdProductAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.interfaces.OnItemClickListener;
import com.example.levanhao.splashapp.model.ExhibitItem;
import com.example.levanhao.splashapp.model.LoginInfo;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.UserInformationModel;
import com.example.levanhao.splashapp.model.product.Brand;
import com.example.levanhao.splashapp.model.product.Size;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.backIcon:
            onBackPressed();
            Log.e("click", "backIcon");
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
            startActivityForResult(exhibitIntent, StaticVarriable.EXHIBIT_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.sizeLayout:
            Intent sizeIntent = new Intent(SearchActivity.this, SizeActivity.class);
            startActivityForResult(sizeIntent, StaticVarriable.SIZE_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.brandLayout:
            Intent brandIntent = new Intent(SearchActivity.this, BrandActivity.class);
            startActivityForResult(brandIntent, StaticVarriable.BRAND_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.quitText:
            pickerLayout.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_finish_view));
            pickerLayout.setVisibility(View.GONE);
            break;
        case R.id.selectText:
            min = Integer.parseInt(pk1[numberPicker1.getValue()]);
            max = Integer.parseInt(pk2[numberPicker2.getValue()]);
            Log.e("position", pk1[numberPicker1.getValue()] + "---" + pk2[numberPicker2.getValue()]);
//            String price = s1.substring(0, s1.lastIndexOf(",")) + " K - " + s2.substring(0, s2.lastIndexOf(",")) + " K";
            String price = String.valueOf(min / 1000) + " K - " + String.valueOf(max / 1000) + " K";
            priceTextView.setText(price);
            pickerLayout.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_finish_view));
            pickerLayout.setVisibility(View.GONE);
            break;
        case R.id.priceLayout:
            pickerLayout.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_start_view));
            pickerLayout.setVisibility(View.VISIBLE);
            break;
        case R.id.searchButton:
            seachProducts();
            break;
        case R.id.searchBack:
            resultSearch.setVisibility(View.GONE);
//            resultSearch.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_finish_view_0x));
            Log.e("click", "searchBack");
            break;
        default:
            break;
        }
    }

    private void seachProducts() {
        LoginActivity.requestManager.searchProducts(/*MainActivity.token, keyword.getText().toString(), exhibitItem.getId(), brand.getId(), size.getId(), min, max, statusText.getText().toString(), 0, 20,*/ searchHandler);
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
        searchHandler = new SearchHandler();
        LoginActivity.systemManager.getHandlerManager().setSearchHandler(searchHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case StaticVarriable.STATUS_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                status = data.getStringExtra(StaticVarriable.STATUS_STRING);
                statusText.setText(status);
                if (status.equals("Tất cả")) {
                    statusText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    statusText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;
        case StaticVarriable.EXHIBIT_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                exhibitItem = (ExhibitItem) data.getSerializableExtra(StaticVarriable.EXHIBIT_STRING);
                exhibitText.setText(exhibitItem.getName());
                if (exhibitItem.getName().equals("Tất cả")) {
                    exhibitText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    exhibitText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;
        case StaticVarriable.SIZE_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                size = (Size) data.getSerializableExtra(StaticVarriable.SIZE_STRING);
                sizeText.setText(size.getSize_name());
                if (size.getSize_name().equals("Tất cả")) {
                    sizeText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    sizeText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }
            }
            break;

        case StaticVarriable.BRAND_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                brand = (Brand) data.getSerializableExtra(StaticVarriable.BRAND_STRING);
                brandText.setText(brand.getBrand_name());
                if (brand.getBrand_name().equals("Tất cả")) {
                    brandText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    brandText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;
        default:
            break;
        }
    }

    private SearchHandler searchHandler;
    private String status;
    private int min = 0;
    private int max = 0;
    private Brand brand = new Brand();
    private Size size = new Size();
    private ExhibitItem exhibitItem = new ExhibitItem();
    private ImageView backIcon;
    private ImageView deleteIcon;

    private TextView statusText;
    private LinearLayout statusLayout;

    private TextView exhibitText;
    private LinearLayout exhibitLayout;
    private LinearLayout sizeLayout;
    private TextView sizeText;

    private TextView brandText;
    private LinearLayout brandLayout;
    private TextView priceTextView;

    private NumberPicker numberPicker1, numberPicker2;
    private LinearLayout pickerLayout;
    private TextView quitText, selectText;
    private RelativeLayout priceLayout;
    private EditText keyword;
    private Button searchButton;
    String[] pk1 = {"10000", "50000", "100000", "200000", "400000", "800000", "1000000", "200000", "4000000", "8000000", "10000000", "20000000"};
    String[] pk2 = {"50000", "100000", "200000", "400000", "800000", "1000000", "2000000", "4000000", "8000000", "10000000", "20000000", "30000000"};
    //màn hình kết quả tìm kiếm:
    private RelativeLayout resultSearch;
    private ImageView searchBack;
    private TextView title;
    private IRecyclerView recyclerView;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private GirdProductAdapter girdProductAdapter;
    private ArrayList<ProductItem> searchResult;
    private TextView noResult;


    private void init() {

        ///màn hình resultSearch:
        title = findViewById(R.id.title);
        noResult = findViewById(R.id.noResult);
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        resultSearch = findViewById(R.id.resultSearch);
        searchBack = findViewById(R.id.searchBack);
        searchBack.setOnClickListener(this);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerView);
        //
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        keyword = findViewById(R.id.keyword);
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

        brandText = findViewById(R.id.brandText);
        brandLayout = findViewById(R.id.brandLayout);
        brandLayout.setOnClickListener(this);
        numberPicker1 = findViewById(R.id.number_picker1);
        numberPicker2 = findViewById(R.id.number_picker2);
        pickerLayout = findViewById(R.id.pickerLayout);
        quitText = findViewById(R.id.quitText);
        quitText.setOnClickListener(this);
        selectText = findViewById(R.id.selectText);
        selectText.setOnClickListener(this);
        priceTextView = findViewById(R.id.priceTextView);
        priceLayout = findViewById(R.id.priceLayout);
        priceLayout.setOnClickListener(this);

        numberPicker1.setMinValue(0);
        numberPicker2.setMinValue(0);
        String[] pk1Format = StaticMethod.formatVNĐ(pk1);
        String[] pk2Format = StaticMethod.formatVNĐ(pk2);

        numberPicker1.setMaxValue(pk1Format.length - 1);
        numberPicker2.setMaxValue(pk2Format.length - 1);

        numberPicker1.setDisplayedValues(pk1Format);
        numberPicker2.setDisplayedValues(pk2Format);

        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int a = numberPicker2.getValue();
                if (newVal > a) {
                    numberPicker2.setValue(a);
                }
            }
        });
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int a = numberPicker1.getValue();
                if (newVal < a) {
                    numberPicker1.setValue(a);
                }
            }
        });
        searchResult = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        girdProductAdapter = new GirdProductAdapter(this);
        recyclerView.setIAdapter(girdProductAdapter);
        girdProductAdapter.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(int position, ProductItem productItem, View v) {
                Intent myIntent = new Intent(SearchActivity.this, DetailProductActivity.class);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                LoginActivity.requestManager.getUserListing(userId, token, String.valueOf(0), String.valueOf(20), userListingHandler);

            }
        });

    }

    private class SearchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.SEARCH:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadResultSearch(jsonArray);
                break;
            case StaticVarriable.ERROR_INTERNET:
                recyclerView.setRefreshing(false);
                break;
            default:
                break;
            }
        }
    }

    private void loadResultSearch(JSONArray jsonArray) {
        Log.e("2222", jsonArray.toString());
        searchResult.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ProductItem productItem = new ProductItem(jsonObject);
                searchResult.add(productItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.setRefreshing(false);
        girdProductAdapter.setList(searchResult);
//        resultSearch.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_start_view_0x));

        updateUI();
    }

    private void updateUI() {
        resultSearch.setVisibility(View.VISIBLE);
        noResult.setVisibility(searchResult.size() == 0 ? View.VISIBLE : View.GONE);
        String searchText = "";
        if (keyword.getText().toString().length() > 0) {
            searchText = searchText + "," + keyword.getText().toString();
        }

        if (exhibitItem.getId() != 0) {
            searchText = searchText + "," + exhibitItem.getName();
        }
        if (brand.getId() != 0) {
            searchText = searchText + "," + brand.getBrand_name();
        }
        if (size.getId() != 0) {
            searchText = searchText + "," + size.getSize_name();
        }
        if (min != 0) {
            searchText = searchText + "," + StaticMethod.formatPrice(String.valueOf(min)) + " - " + StaticMethod.formatPrice(String.valueOf(max));

        }
        if (!(statusText.getText().toString()).equals("Tất cả")) {
            searchText = searchText + "," + statusText.getText().toString();
        }

        searchText = StaticMethod.removeCharAt(searchText);
        title.setText(searchText);

    }

}
