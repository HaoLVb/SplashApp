package com.example.levanhao.splashapp.fragment.trangchu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticMethod;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.activity.CommentActivity;
import com.example.levanhao.splashapp.activity.DetailProductActivity;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.adapter.GirdProductAdapter;
import com.example.levanhao.splashapp.adapter.ListProductAdapter;
import com.example.levanhao.splashapp.anim.viewflipper.AnimationFactory;
import com.example.levanhao.splashapp.interfaces.OnClickViewListener;
import com.example.levanhao.splashapp.view.customview.ClassicRefreshHeaderView;
import com.example.levanhao.splashapp.view.customview.DensityUtils;
import com.example.levanhao.splashapp.view.customview.HidingScrollListener;
import com.example.levanhao.splashapp.view.customview.LoadMoreFooterView;
import com.example.levanhao.splashapp.interfaces.OnItemClickListener;
import com.example.levanhao.splashapp.model.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryProductFragment extends Fragment implements OnClickViewListener {

    private LoadMoreFooterView loadMoreListFooterView;
    private LoadMoreFooterView loadMoreGirdFooterView;
    RequestQueue requestQueue;
    private static final String ARG_PARAM = "param";
    private int categoryId;

    public CategoryProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_PARAM);
        }
        this.context = getActivity();
        requestQueue = Volley.newRequestQueue(context);
        categoryProductHandler = new CategoryProductHandler();
        LoginActivity.systemManager.getHandlerManager().setCategoryProductHandler(categoryProductHandler);
    }

    public static CategoryProductFragment newInstance(int param) {
        CategoryProductFragment fragment = new CategoryProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_product, container, false);
        this.girdRecyclerView = view.findViewById(R.id.girdRecyclerView);
        this.listRecyclerView = view.findViewById(R.id.listRecyclerView);

        this.girdRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        this.listRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        loadMoreListFooterView = (LoadMoreFooterView) listRecyclerView.getLoadMoreFooterView();
        loadMoreGirdFooterView = (LoadMoreFooterView) girdRecyclerView.getLoadMoreFooterView();

        viewFlipper = view.findViewById(R.id.viewFlipper);

        this.girdProductAdapter = new GirdProductAdapter(context);
        this.listProductAdapter = new ListProductAdapter(context, this);

//		this.productRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        viewFlipper.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // This is all you need to do to 3D flip
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
            }
        });
//        this.girdRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.girdRecyclerView.setIAdapter(girdProductAdapter);
        this.listRecyclerView.setIAdapter(listProductAdapter);

        ClassicRefreshHeaderView classicRefreshHeaderView = new ClassicRefreshHeaderView(context);
        classicRefreshHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(context, 80)));


        ClassicRefreshHeaderView classicRefreshHeaderView1 = new ClassicRefreshHeaderView(context);
        classicRefreshHeaderView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(context, 80)));
        girdRecyclerView.setRefreshHeaderView(classicRefreshHeaderView);
        listRecyclerView.setRefreshHeaderView(classicRefreshHeaderView1);

        listProductAdapter.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(int position, ProductItem productItem, View v) {
                Intent myIntent = new Intent(context, DetailProductActivity.class);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        listRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreListFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                refreshList();
            }
        });
        listRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (loadMoreListFooterView.canLoadMore() && listProductAdapter.getItemCount() > 0) {
                    loadMoreListFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                    loadMoreList();
                }
            }
        });

        girdProductAdapter.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(int position, ProductItem productItem, View v) {
                Intent myIntent = new Intent(context, DetailProductActivity.class);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        girdRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (loadMoreGirdFooterView.canLoadMore() && girdProductAdapter.getItemCount() > 0) {
                    loadMoreGirdFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                    loadMoreGird();

                }
            }
        });
        girdRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreGirdFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                refreshGird();
            }
        });

        this.listRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.SCROLL_TOP);
            }

            @Override
            public void onShow() {
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.SCROLL_BOTTOM);
            }
        });
        this.girdRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.SCROLL_TOP);
            }

            @Override
            public void onShow() {
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.SCROLL_BOTTOM);
            }
        });
        refreshList();
        refreshGird();

        return view;
    }

    private View view;
    private IRecyclerView girdRecyclerView;
    private IRecyclerView listRecyclerView;
    private GirdProductAdapter girdProductAdapter;
    private ListProductAdapter listProductAdapter;
    private Context context;
    private CategoryProductHandler categoryProductHandler;
    private ViewAnimator viewFlipper;
    private ProductItem productItem;

    private void refreshGird() {
        String url = StaticVarriable.DOMAIN + "/get_list_products";
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductItem productItem = new ProductItem(jsonObject2);
                                arrayList.add(productItem);
                                Log.e("ssasww", productItem.toString());
                            }
                            girdRecyclerView.setRefreshing(false);
                            girdProductAdapter.setList(arrayList);
                            LoginActivity.systemManager.getHandlerManager().sendMessage(
                                    LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                                    StaticVarriable.HIDE_LOADING);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ssasww", "fail");
                        girdRecyclerView.setRefreshing(false);
                        LoginActivity.systemManager.getHandlerManager().sendMessage(
                                LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                                StaticVarriable.HIDE_LOADING);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (MainActivity.token != null) {
                    params.put("token", MainActivity.token);
                }
                params.put("index", String.valueOf(StaticVarriable.INDEX));
                params.put("count", String.valueOf(StaticVarriable.COUNT));
                if (categoryId != 0) {
                    params.put("category_id", String.valueOf(categoryId));
                }
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void loadMoreGird() {
        String url = StaticVarriable.DOMAIN + "/get_list_products";
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductItem productItem = new ProductItem(jsonObject2);
                                arrayList.add(productItem);
                                Log.e("ssasww", productItem.toString());
                            }
                            if (StaticMethod.isEmpty(arrayList)) {
                                loadMoreGirdFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                            } else {
                                loadMoreGirdFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                                girdProductAdapter.addList(arrayList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ssasww", "fail");


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (MainActivity.token != null) {
                    params.put("token", MainActivity.token);
                }
                params.put("index", String.valueOf(girdProductAdapter.getItemCount()));
                params.put("count", String.valueOf(StaticVarriable.COUNT));
                if (categoryId != 0) {
                    params.put("category_id", String.valueOf(categoryId));
                }
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void refreshList() {
        String url = StaticVarriable.DOMAIN + "/get_list_products";
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("products");
                            Log.e("yyyy", jsonObject.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductItem productItem = new ProductItem(jsonObject2);
                                arrayList.add(productItem);
                                Log.e("ssasww", productItem.toString());
                            }
                            listRecyclerView.setRefreshing(false);
                            listProductAdapter.setList(arrayList);
                            LoginActivity.systemManager.getHandlerManager().sendMessage(
                                    LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                                    StaticVarriable.HIDE_LOADING);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ssasww", "fail");
                        listRecyclerView.setRefreshing(false);
                        LoginActivity.systemManager.getHandlerManager().sendMessage(
                                LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                                StaticVarriable.HIDE_LOADING);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (MainActivity.token != null) {
                    params.put("token", MainActivity.token);
                }
                params.put("index", String.valueOf(StaticVarriable.INDEX));
                params.put("count", String.valueOf(StaticVarriable.COUNT));
                if (categoryId != 0) {
                    params.put("category_id", String.valueOf(categoryId));
                }

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void loadMoreList() {
        String url = StaticVarriable.DOMAIN + "/get_list_products";
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject1.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductItem productItem = new ProductItem(jsonObject2);
                                arrayList.add(productItem);
                                Log.e("ssasww", productItem.toString());
                            }
                            if (StaticMethod.isEmpty(arrayList)) {
                                loadMoreListFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                            } else {
                                loadMoreListFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                                listProductAdapter.addList(arrayList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ssasww", "fail");


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (MainActivity.token != null) {
                    params.put("token", MainActivity.token);
                }
                params.put("index", String.valueOf(listProductAdapter.getItemCount()));
                params.put("count", String.valueOf(StaticVarriable.COUNT));
                if (categoryId != 0) {
                    params.put("category_id", String.valueOf(categoryId));
                }
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    @Override
    public void onClickView(int type, ProductItem productItem) {
        this.productItem = productItem;
        switch (type) {
        case 1:
            LoginActivity.requestManager.likeProduct(MainActivity.token, productItem.getId(), categoryProductHandler);
            LoginActivity.systemManager.getHandlerManager().sendMessage(
                    LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                    StaticVarriable.SHOW_LOADING);
            break;
        case 2:
//            Toast.makeText(context, "comment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra(StaticVarriable.ID, productItem.getId());
            startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            break;
        }
    }

    private class CategoryProductHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.TIMELINE:
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                Log.e("223s", "1");
                break;
            case StaticVarriable.GIRD:
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.RIGHT_LEFT);
                Log.e("223s", "2");
                break;
            case StaticVarriable.NOT_VALIDATE:
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.HIDE_LOADING);
                break;
            case StaticVarriable.ERROR_INTERNET:
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.ERROR_INTERNET);
                break;
            case StaticVarriable.LIKE_PRODUCT:
                LoginActivity.systemManager.getHandlerManager().sendMessage(
                        LoginActivity.systemManager.getHandlerManager().getMainHandler(),
                        StaticVarriable.HIDE_LOADING);
                if (productItem.isIs_liked()) {
                    productItem.setIs_liked(false);
                    productItem.setLike(productItem.getLike()-1);
                } else {
                    productItem.setIs_liked(true);
                    productItem.setLike(productItem.getLike()+1);
                }
                listProductAdapter.notifyDataSetChanged();

                break;
            default:
                break;
            }
        }
    }

}
