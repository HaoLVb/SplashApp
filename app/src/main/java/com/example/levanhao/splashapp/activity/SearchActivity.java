package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.shawnlin.numberpicker.NumberPicker;

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
            String s1 = pk1[numberPicker1.getValue()];
            String s2 = pk2[numberPicker2.getValue()];
            String price = s1.substring(0, s1.lastIndexOf(",")) + " K - " + s2.substring(0, s2.lastIndexOf(",")) + " K";
            priceTextView.setText(price);
            pickerLayout.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_finish_view));
            pickerLayout.setVisibility(View.GONE);
            break;
        case R.id.priceLayout:
            pickerLayout.startAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.anim_start_view));
            pickerLayout.setVisibility(View.VISIBLE);
            break;
        case R.id.searchButton:

            break;
        default:
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
                if (result.equals("Tất cả")) {
                    statusText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    statusText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;
        case StaticVarriable.EXHIBIT_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.EXHIBIT_STRING);
                exhibitText.setText(result);
                if (result.equals("Tất cả")) {
                    exhibitText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    exhibitText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;
        case StaticVarriable.SIZE_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.SIZE_STRING);
                sizeText.setText(result);
                if (result.equals("Tất cả")) {
                    sizeText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    sizeText.setTextColor(ContextCompat.getColor(this, R.color.black));
                }

            }
            break;

        case StaticVarriable.BRAND_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(StaticVarriable.BRAND_STRING);
                brandText.setText(result);
                if (result.equals("Tất cả")) {
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
    String[] pk1 = {"10,000 VNĐ", "50,000 VNĐ", "100,000 VNĐ", "200,000 VNĐ", "400,000 VNĐ", "800,000 VNĐ", "1,000,000 VNĐ", "2,000,000 VNĐ", "4,000,000 VNĐ", "8,000,000 VNĐ", "10,000,000 VNĐ", "20,000,000 VNĐ"};
    String[] pk2 = {"50,000 VNĐ", "100,000 VNĐ", "200,000 VNĐ", "400,000 VNĐ", "800,000 VNĐ", "1,000,000 VNĐ", "2,000,000 VNĐ", "4,000,000 VNĐ", "8,000,000 VNĐ", "10,000,000 VNĐ", "20,000,000 VNĐ", "30,000,000 VNĐ"};
    //màn hình kết quả tìm kiếm:
    private LinearLayout resultSearch;
    private ImageView searchBack;
    private TextView title;
    private RecyclerView recyclerView;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;


    private void init() {

        ///màn hình resultSearch:
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        resultSearch = findViewById(R.id.resultSearch);
        searchBack = findViewById(R.id.searchBack);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerView);
        //
        searchButton = findViewById(R.id.searchButton);
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

        numberPicker1.setMaxValue(pk1.length - 1);
        numberPicker2.setMaxValue(pk2.length - 1);

        numberPicker1.setDisplayedValues(pk1);
        numberPicker2.setDisplayedValues(pk2);

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

    }
}
