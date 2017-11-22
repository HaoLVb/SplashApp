package com.example.levanhao.splashapp.activity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.model.ProductItem;

public class ReportActivity extends AppCompatActivity {
    private ProductItem productItem;
    private ImageView imProd;
    private TextView tvNameProd;
    private Spinner spSubject;
    private EditText edDetails;
    private Button btnSendReport;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        productItem = (ProductItem) getIntent().getSerializableExtra(StaticVarriable.PRODUCT_ITEM);
        init();
        updateInfo();

    }

    private void init() {
        imProd = findViewById(R.id.imProd);
        tvNameProd = findViewById(R.id.tvNameProd);
        spSubject = findViewById(R.id.spSubject);
        edDetails = findViewById(R.id.edDetails);
        btnSendReport = findViewById(R.id.btnSendReport);
        //view loading
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
    }

    private void updateInfo() {
        if (productItem != null) {
            if (productItem.getImages() != null) {
                Glide.with(this)
                        .load(StaticVarriable.DOMAIN + "/" + productItem.getImages().get(0).getUrl())
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(imProd);
                Log.e("shd3", productItem.getImages().get(0).getUrl());
            }
            tvNameProd.setText(productItem.getName());
        }
    }

}
