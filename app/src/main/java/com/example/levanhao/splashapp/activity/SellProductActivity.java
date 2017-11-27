package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.levanhao.splashapp.LoginHelper;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.ImageAdapter;
import com.example.levanhao.splashapp.dialog.ViewDialogForNotification;
import com.example.levanhao.splashapp.model.ExhibitItem;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class SellProductActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.addImage:
            Intent cameraIntent = new Intent(SellProductActivity.this, CameraActivity.class);
            cameraIntent.putExtra(StaticVarriable.CAMERA_ACTIVITY, true);
            startActivityForResult(cameraIntent, StaticVarriable.CAMERA_REQUEST);
            break;
        case R.id.exhibitLayout:
            Intent exhibitIntent = new Intent(SellProductActivity.this, ListCategoryActiviy.class);
            exhibitIntent.putExtra(StaticVarriable.EXHIBIT_BOOLEAN, false);
            startActivityForResult(exhibitIntent, StaticVarriable.EXHIBIT_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.statusLayout:
            Intent statusIntent = new Intent(SellProductActivity.this, StatusActivity.class);
            startActivityForResult(statusIntent, StaticVarriable.STATUS_REQUEST);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.backIcon:
            onBackPressed();
            break;
        case R.id.postButton:
            postProducts();
            break;
        }
    }

    private void postProducts() {
        ArrayList<Integer> ships_from_id = new ArrayList<>();
        ships_from_id.add(1);
        ships_from_id.add(2);
        ships_from_id.add(3);
        LoginActivity.requestManager.addProduct(MainActivity.token, productName.getText().toString(), Integer.parseInt(priceEditText.getText().toString()), exhibitItem.getId(), productDe.getText().toString(), "Đại hoc Bách Khoa Hà Nội", ships_from_id, condition, sellProductHandler);
    }

    private ExhibitItem exhibitItem;
    private String condition;
    private EditText productDe;
    private EditText productName;
    private EditText totalPrice;
    private EditText priceEditText;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.trans_right_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case StaticVarriable.CAMERA_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                File file = (File) getIntent().getExtras().get(StaticVarriable.IMAGE_RETURN);
                files.add(file);
                imageAdapter.notifyDataSetChanged();
            }
            break;
        case StaticVarriable.EXHIBIT_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                exhibitItem = (ExhibitItem) data.getSerializableExtra(StaticVarriable.EXHIBIT_STRING);
                exhibitText.setText(exhibitItem.getName());
                exhibitText.setTextColor(ContextCompat.getColor(this, R.color.red));
            }
            break;
        case StaticVarriable.STATUS_REQUEST:
            if (resultCode == Activity.RESULT_OK) {
                condition = data.getStringExtra(StaticVarriable.STATUS_STRING);
                statusText.setText(condition);
                statusText.setTextColor(ContextCompat.getColor(this, R.color.red));
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        File file = (File) getIntent().getExtras().get("image");
        files = new ArrayList<>();
        files.add(file);
        sellProductHandler = new SellProductHandler();
        LoginActivity.systemManager.getHandlerManager().setSellProductHandler(sellProductHandler);
        init();
    }

    private RecyclerView imageRecyclerView;
    private ImageAdapter imageAdapter;
    private ImageView addImage;
    private TextView exhibitText;
    private TextView statusText;
    private LinearLayout exhibitLayout;
    private LinearLayout statusLayout;
    private ImageView backIcon;
    private ArrayList<File> files;
    private String price;
    private Button postButton;
    private SellProductHandler sellProductHandler;


    private void init() {
        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(this);
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(this, files);
        imageRecyclerView.setAdapter(imageAdapter);

        this.addImage = findViewById(R.id.addImage);
        this.addImage.setOnClickListener(this);

        exhibitText = findViewById(R.id.exhibitText);
        exhibitLayout = findViewById(R.id.exhibitLayout);
        exhibitLayout.setOnClickListener(this);

        statusText = findViewById(R.id.statusText);
        statusLayout = findViewById(R.id.statusLayout);
        statusLayout.setOnClickListener(this);

        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        productName = findViewById(R.id.productName);
        productDe = findViewById(R.id.productDe);
        priceEditText = findViewById(R.id.priceEditText);
        totalPrice = findViewById(R.id.totalPrice);
        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                priceEditText.setTextColor(Color.parseColor("#d8202a"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalPrice.setText(s);
                totalPrice.setTextColor(Color.parseColor("#d8202a"));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private class SellProductHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.ADD_PRODUCT:
                JSONObject jsonObject = (JSONObject) msg.obj;
                Log.e("addproduct", jsonObject.toString());
                break;
            case StaticVarriable.ERROR_INTERNET:
                ViewDialogForNotification dialog = new ViewDialogForNotification();
                dialog.showDialog(SellProductActivity.this, "Thông báo", "Kiểm tra kết nối internet", R.drawable.tick_box_icon);
                break;
            case StaticVarriable.NOT_VALIDATE:
                LoginHelper loginHelper = new LoginHelper(SellProductActivity.this);
                loginHelper.deleteLogin();
                loginHelper.deleteUser();
                Intent validateiIntent = new Intent(SellProductActivity.this, LoginActivity.class);
                startActivity(validateiIntent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
                finish();
                break;
            default:
                break;
            }
        }
    }

}
