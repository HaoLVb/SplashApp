package com.example.levanhao.splashapp.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.adapter.CommentAdapter;
import com.example.levanhao.splashapp.adapter.ViewPagerImageProductsAdapter;
import com.example.levanhao.splashapp.adapter.ViewPagerImageProductsViewAdapter;
import com.example.levanhao.splashapp.anim.ViewLoading;
import com.example.levanhao.splashapp.view.customview.ClickableViewpager;
import com.example.levanhao.splashapp.view.customview.ImageViewTouchViewPager;
import com.example.levanhao.splashapp.dialog.ViewDialogForNotification;
import com.example.levanhao.splashapp.model.ProductItem;
import com.example.levanhao.splashapp.model.product.CommentItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.backIcon:
            onBackPressed();
            break;
        case R.id.moreIcon:
            viewMore.startAnimation(AnimationUtils.loadAnimation(DetailProductActivity.this, R.anim.anim_start_view));
            viewMore.setVisibility(View.VISIBLE);
            break;
        case R.id.btnCancelMore:
            viewMore.setVisibility(View.GONE);
            break;
        case R.id.viewMore:
            viewMore.setVisibility(View.GONE);
            break;
        case R.id.btReturnHome:
            finish();
            break;
        case R.id.imNextImage:
            imHeader.setCurrentItem(imHeader.getCurrentItem() + 1, true);
            break;
        case R.id.imBackImage:
            imHeader.setCurrentItem(imHeader.getCurrentItem() - 1, true);
            break;
        case R.id.btnCloseView:
            imProdView.setVisibility(View.GONE);
            break;
        case R.id.commentButton:
            Intent intent = new Intent(DetailProductActivity.this, CommentActivity.class);
            intent.putExtra(StaticVarriable.ID, productItem.getId());
            startActivity(intent);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.categoryText:
            Intent categoryIntent = new Intent(DetailProductActivity.this, CategoryActivity.class);
            categoryIntent.putExtra("categoryId", productItem.getCategories().get(0));
            startActivity(categoryIntent);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.userLayout:
            Intent userIntent = new Intent(DetailProductActivity.this, UserInfoActivity.class);
            try {
                userIntent.putExtra(StaticVarriable.TOKEN, MainActivity.token);
                userIntent.putExtra(StaticVarriable.USER_ID, productItem.getSeller().getId());
            } catch (NullPointerException e) {
                Log.e("exception", e.toString());
            }
            startActivity(userIntent);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        case R.id.heartImage:
            vloading.setVisibility(View.VISIBLE);
            LoginActivity.requestManager.likeProduct(MainActivity.token, productId, deatailHandler);
            break;
        case R.id.btReport:
            Intent reportIntent = new Intent(DetailProductActivity.this, ReportActivity.class);
            reportIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem);
            startActivity(reportIntent);
            viewMore.setVisibility(View.GONE);
            overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
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
        setContentView(R.layout.activity_detail_product);

        loadViewForCode();
        init();
        deatailHandler = new DeatailHandler();
        LoginActivity.systemManager.getHandlerManager().setDetailHandler(deatailHandler);
        productId = getIntent().getIntExtra(StaticVarriable.PRODUCT_ITEM, -1);
        LoginActivity.requestManager.getProductInfo(productId, MainActivity.token, deatailHandler);
        LoginActivity.requestManager.getComment(productId, deatailHandler);
    }

    private int productId;
    private DeatailHandler deatailHandler;
    private ProductItem productItem;
    private ImageView backIcon;
    private ImageView moreIcon;
    private View viewMore;
    private Button btnCancel;
    private Button returnHome;
    private ClickableViewpager imHeader;
    private ImageView imNextImage;
    private ImageView imBackImage;
    private View imProdView;
    private ImageView btnCloseView;
    private ImageViewTouchViewPager imProductView;
    private TextView likeTextView;
    private TextView commentTextview;
    private TextView descriTextview;
    private TextView nameProduct;
    private CircleImageView avatar;
    private TextView sellerName;
    private TextView score;
    private TextView listing;
    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private ArrayList<CommentItem> commentItems;
    private Button commentButton;
    private AnimationDrawable loadingViewAnim = null;
    private View vloading;
    private TextView categoryText;
    private TextView status;
    private TextView price;
    private TextView shipFrom;
    private ImageView heartImage;
    private RelativeLayout userLayout;
    private TextView weightText;
    private TextView sizeText;
    private Button btReport;

    private void init() {
        vloading = findViewById(R.id.layoutLoading);
        ImageView loadingIcon = findViewById(R.id.imLoad);
        ViewLoading loading = new ViewLoading(loadingViewAnim, loadingIcon);
        loading.createAnim();
        vloading.setVisibility(View.VISIBLE);

        this.backIcon = findViewById(R.id.backIcon);
        this.backIcon.setOnClickListener(this);
        this.moreIcon = findViewById(R.id.moreIcon);
        this.moreIcon.setOnClickListener(this);
        viewMore = findViewById(R.id.viewMore);
        this.viewMore.setOnClickListener(this);
        btnCancel = findViewById(R.id.btnCancelMore);
        this.btnCancel.setOnClickListener(this);
        returnHome = findViewById(R.id.btReturnHome);
        returnHome.setOnClickListener(this);
        imHeader = findViewById(R.id.headerImageProduct);
        imBackImage = findViewById(R.id.imBackImage);
        imBackImage.setOnClickListener(this);
        imNextImage = findViewById(R.id.imNextImage);
        imNextImage.setOnClickListener(this);
        imProdView = findViewById(R.id.view_image);
        btnCloseView = findViewById(R.id.btnCloseView);
        btnCloseView.setOnClickListener(this);
        imProductView = findViewById(R.id.imProductView);
        likeTextView = findViewById(R.id.likeTextView);
        commentTextview = findViewById(R.id.commentTextview);
        descriTextview = findViewById(R.id.descriTextview);
        nameProduct = findViewById(R.id.nameProduct);
        avatar = findViewById(R.id.avatar);
        sellerName = findViewById(R.id.sellerName);
        score = findViewById(R.id.score);
        listing = findViewById(R.id.listing);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentItems = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentItems, this);
        commentRecyclerView.setAdapter(commentAdapter);
        commentButton = findViewById(R.id.commentButton);
        commentButton.setOnClickListener(this);
        categoryText = findViewById(R.id.categoryText);
        categoryText.setOnClickListener(this);
        status = findViewById(R.id.status);
        price = findViewById(R.id.price);
        shipFrom = findViewById(R.id.shipFrom);
        heartImage = findViewById(R.id.heartImage);
        heartImage.setOnClickListener(this);
        userLayout = findViewById(R.id.userLayout);
        userLayout.setOnClickListener(this);
        weightText = findViewById(R.id.weightText);
        sizeText = findViewById(R.id.sizeText);
        btReport = findViewById(R.id.btReport);
        btReport.setOnClickListener(this);
    }

    //
    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.information_product_layout, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        //set layout cho header
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(width, (int) (3.0F * (height / 5.5F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    private void updateViewProductDetail(final ProductItem detailProduct) {
        nameProduct.setText(detailProduct.getName());
        Log.e("374673", detailProduct.toString());
        Glide.with(this)
                .load(StaticVarriable.DOMAIN + "/" + detailProduct.getSeller().getAvatar())
                .placeholder(R.drawable.icon_no_avatar)
                .error(R.drawable.icon_no_avatar)
                .into(avatar);
        Log.e("sss2", StaticVarriable.DOMAIN + "/" + detailProduct.getSeller().getAvatar());
        sellerName.setText(detailProduct.getSeller().getName());
        score.setText("Điểm: " + detailProduct.getSeller().getScore());
        listing.setText("Sản phẩm: " + detailProduct.getSeller().getListing());
        categoryText.setText(detailProduct.getCategories().get(0).getName());
        status.setText(detailProduct.getCondition());
        price.setText("Giá:  " + StaticMethod.formatPrice(String.valueOf(detailProduct.getPrice())));
        shipFrom.setText(detailProduct.getShips_from());
        weightText.setText(detailProduct.getWeight());
        ArrayList<String> list = detailProduct.getDimension();
        sizeText.setText(list.get(0) + "x" + list.get(1) + "x" + list.get(2));

        if (detailProduct.isIs_liked()) {
            heartImage.setImageResource(R.drawable.icon_like_on);
        } else {
            heartImage.setImageResource(R.drawable.icon_like_off);
        }

        ViewPagerImageProductsAdapter viewPagerImageProductsAdapter = new ViewPagerImageProductsAdapter(this, detailProduct.getImages(), detailProduct.getVideos());
        imHeader.setAdapter(viewPagerImageProductsAdapter);
        ViewPagerImageProductsViewAdapter viewPagerImageProductsViewAdapter = new ViewPagerImageProductsViewAdapter(DetailProductActivity.this, detailProduct.getImages(),
                detailProduct.getVideos());
        imProductView.setAdapter(viewPagerImageProductsViewAdapter);

        if (detailProduct.getImages().size() > 1) {
            imNextImage.setVisibility(View.VISIBLE);
        } else {
            imBackImage.setVisibility(View.GONE);
        }

        imHeader.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("position ảnh: ", String.valueOf(position));
                if (position == 0) {
                    imNextImage.setVisibility(View.VISIBLE);
                    imBackImage.setVisibility(View.GONE);
                } else if ((position > 0) && (position < ((detailProduct.getImages().size() - 1)))) {
                    imNextImage.setVisibility(View.VISIBLE);
                    imBackImage.setVisibility(View.VISIBLE);
                } else if (position == (detailProduct.getImages().size() - 1)) {
                    imNextImage.setVisibility(View.GONE);
                    imBackImage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imHeader.setOnItemClickListener(position -> {
            imProdView.startAnimation(AnimationUtils.loadAnimation(DetailProductActivity.this, R.anim.anim_start_view));
            imProdView.setVisibility(View.VISIBLE);
        });
        likeTextView.setText(detailProduct.getLike() + " thích");
        commentTextview.setText(detailProduct.getNumber_comment() + " bình luận");
        descriTextview.setText(detailProduct.getDescribed());
    }


    private class DeatailHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vloading.setVisibility(View.GONE);
            switch (msg.what) {
            case StaticVarriable.GET_COMMENT:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadComment(jsonArray);
                break;
            case StaticVarriable.GET_PRODUCT_INFO:
                JSONObject jsonObject = (JSONObject) msg.obj;
                productItem = new ProductItem(jsonObject);
                updateViewProductDetail(productItem);
                break;
            case StaticVarriable.LIKE_PRODUCT:
                if (productItem.isIs_liked()) {
                    productItem.setIs_liked(false);
                    heartImage.setImageResource(R.drawable.icon_like_off);
                } else {
                    productItem.setIs_liked(true);
                    heartImage.setImageResource(R.drawable.icon_like_on);
                }
                try {
                    int likeNumber = ((JSONObject) msg.obj).getInt("like");
                    likeTextView.setText(likeNumber + " thích");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case StaticVarriable.ERROR_INTERNET:
                ViewDialogForNotification dialog = new ViewDialogForNotification();
                dialog.showDialog(DetailProductActivity.this, "Thông báo", "Kiểm tra kết nối internet", R.drawable.tick_box_icon);
                break;
            default:
                break;
            }
        }
    }

    private void loadComment(JSONArray jsonArray) {
        for (int i = 0; i < 3; i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                CommentItem commentItem = new CommentItem(jsonObject);
                commentItems.add(commentItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (commentItems.size() > 0) {
            commentButton.setText("Xem và viết bình luận");
        } else {
            commentButton.setText("Trở thành người bình luận đầu tiên");
        }
        commentAdapter.notifyDataSetChanged();
    }
}
