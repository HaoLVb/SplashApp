package com.example.levanhao.splashapp.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.CommentAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.view.customview.HRecycleView;
import com.example.levanhao.splashapp.model.product.CommentItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        setContentView(R.layout.activity_comment);
        ImageView loadingIcon = (ImageView) findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        productId = getIntent().getIntExtra(StaticVarriable.ID, -1);
        init();
        commentHandler = new CommentHandler();
        LoginActivity.systemManager.getHandlerManager().setCommentHandler(commentHandler);
        LoginActivity.requestManager.getComment(productId, commentHandler);
    }

    private void init() {
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(this);
        vloading = findViewById(R.id.layoutLoading);
        vloading.setVisibility(View.VISIBLE);
        commentItems = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentItems, this);
        recyclerView.setAdapter(commentAdapter);
        btSendComment = findViewById(R.id.btSendComment);
        edComment = findViewById(R.id.edComment);
        btSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.requestManager.setComment(MainActivity.token, productId, edComment.getText().toString(), 0, commentHandler);
                vloading.setVisibility(View.VISIBLE);
            }
        });

    }

    private View vloading;
    private AnimationDrawable loadingViewAnim = null;
    private int productId;
    private CommentHandler commentHandler;
    private ArrayList<CommentItem> commentItems;
    private HRecycleView recyclerView;
    private CommentAdapter commentAdapter;
    private ImageView backIcon;
    private Button btSendComment;
    private EditText edComment;


    private class CommentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_COMMENT:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadComment(jsonArray);
                vloading.setVisibility(View.GONE);
                break;
            case StaticVarriable.SET_COMMENT:
                LoginActivity.requestManager.getComment(productId, commentHandler);
                edComment.getText().clear();
                StaticMethod.hideKeyboard(CommentActivity.this);
                break;
            case StaticVarriable.ERROR_INTERNET:
            default:
                break;
            }
        }
    }


    private void loadComment(JSONArray jsonArray) {
        commentItems.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                CommentItem commentItem = new CommentItem(jsonObject);
                commentItems.add(commentItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        commentAdapter.notifyDataSetChanged();
    }
}
