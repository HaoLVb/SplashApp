package com.example.levanhao.splashapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.levanhao.splashapp.App;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.ImageAdapter;
import com.example.levanhao.splashapp.model.ExhibitItem;

import java.util.ArrayList;

public class SellProductActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.addImage:
//            Intent cameraIntent = new Intent(SellProductActivity.this, CameraActivity.class);
//            cameraIntent.putExtra(StaticVarriable.CAMERA_ACTIVITY, true);
//            startActivityForResult(cameraIntent, StaticVarriable.CAMERA_REQUEST);
            break;
        case R.id.exhibitLayout:
            Intent exhibitIntent = new Intent(SellProductActivity.this, ExhibitActiviy.class);
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
        }
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
//                byte[] bytes = getIntent().getByteArrayExtra(StaticVarriable.IMAGE_RETURN);
//                Bitmap bitmapReturn = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                bitmaps.add(bitmapReturn);
//                imageAdapter.notifyDataSetChanged();
//                for (int i = 0; i < bitmaps.size(); i++) {
//                    Log.e("gdas3", bitmaps.get(i).toString());
//                }
//                Log.e("123réult", bytes.toString());
                byte[] bytes = App.getInstance().getCapturedPhotoData();
                Bitmap bitmapReturn = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmaps.add(bitmapReturn);
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
        data = getIntent().getByteArrayExtra(StaticVarriable.IMAGE);
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        init();


    }

    private Bitmap bitmap;
    private RecyclerView imageRecyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<Bitmap> bitmaps;
    private ImageView addImage;
    private TextView exhibitText;
    private TextView statusText;
    private LinearLayout exhibitLayout;
    private LinearLayout statusLayout;
    private byte[] data;
    private ImageView backIcon;


    private void init() {
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(this, bitmaps);
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

    private String price;
}
