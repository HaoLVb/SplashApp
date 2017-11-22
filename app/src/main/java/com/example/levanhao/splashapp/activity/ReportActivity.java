package com.example.levanhao.splashapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.dialog.ViewDialogForNotification;
import com.example.levanhao.splashapp.model.LoginInfo;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.UserInformationModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    private ProductItem productItem;
    private ImageView imProd;
    private TextView tvNameProd;
    private Spinner spSubject;
    private EditText edDetails;
    private Button btnSendReport;
    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private ImageView backIcon;
    private ReportHandler reportHandler;
    private ViewDialogForNotification notificationDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.backIcon:
            onBackPressed();
            break;
        case R.id.btnSendReport:
            if ((spSubject.getSelectedItem().toString()).equals("Ấn vào đây để chọn")) {
                notificationDialog = new ViewDialogForNotification();
                notificationDialog.showDialog(ReportActivity.this, "Thông báo", "Bạn chưa chọn loại báo cáo", R.drawable.cancel);
            } else if (TextUtils.isEmpty(edDetails.getText().toString())) {
                notificationDialog.showDialog(ReportActivity.this, "Thông báo", "Bạn chưa nhập Thông tin chi tiết báo cáo", R.drawable.cancel);
            } else {
                sendReport();
            }
            break;
        }
    }

    private void sendReport() {
        vloading.setVisibility(View.VISIBLE);
        LoginActivity.requestManager.sendReport(MainActivity.token, productItem.getId(), spSubject.getSelectedItem().toString(), edDetails.getText().toString(), reportHandler);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.trans_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        productItem = (ProductItem) getIntent().getSerializableExtra(StaticVarriable.PRODUCT_ITEM);
        reportHandler = new ReportHandler();
        LoginActivity.systemManager.getHandlerManager().setReportHandler(reportHandler);
        init();
        updateInfo();

    }

    private void init() {
        imProd = findViewById(R.id.imProd);
        tvNameProd = findViewById(R.id.tvNameProd);
        spSubject = findViewById(R.id.spSubject);
        edDetails = findViewById(R.id.edDetails);
        btnSendReport = findViewById(R.id.btnSendReport);
        btnSendReport.setOnClickListener(this);
        //view loading
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        String[] report_item = new String[]{
                "Không đúng danh mục",
                "Người bán lừa đảo",
                "Vi phạm pháp luật",
                "Khác"
        };
        final ArrayList<String> listItem = new ArrayList<>(Arrays.asList(report_item));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportActivity.this, android.R.layout.simple_list_item_1, listItem) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                    ((TextView) v.findViewById(android.R.id.text1)).setHintTextColor(Color.GRAY);
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Ấn vào đây để chọn");
        spSubject.setAdapter(adapter);
        spSubject.setSelection(adapter.getCount());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void updateInfo() {
        if (productItem != null) {
            if (productItem.getImages() != null && productItem.getImages().size() > 0) {
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

    private class ReportHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.SEND_REPORT:
                ViewDialogForNotification dialog = new ViewDialogForNotification();
                dialog.showDialog(ReportActivity.this, "Thông báo", "Báo cáo vi phạm đã được gửi thành công", R.drawable.tick_icon);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, R.anim.trans_right_out);
                    }
                }, 2000);
                break;
            default:
                break;
            }
        }
    }
}
