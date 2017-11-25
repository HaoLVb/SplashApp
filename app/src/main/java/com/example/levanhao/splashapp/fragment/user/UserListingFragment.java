package com.example.levanhao.splashapp.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.activity.CategoryActivity;
import com.example.levanhao.splashapp.activity.DetailProductActivity;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.adapter.GirdProductAdapter;
import com.example.levanhao.splashapp.interfaces.OnItemClickListener;
import com.example.levanhao.splashapp.model.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class UserListingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private int userId;
    private String token;


    public UserListingFragment() {
        // Required empty public constructor
    }

    public static UserListingFragment newInstance(int userId, String token) {
        UserListingFragment fragment = new UserListingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        args.putString(ARG_PARAM2, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
            token = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_listing, container, false);
        init();
        userListingHandler = new UserListingHandler();
        LoginActivity.systemManager.getHandlerManager().setUserListingHandler(userListingHandler);
        LoginActivity.requestManager.getUserListing(userId, token, String.valueOf(0), String.valueOf(20), userListingHandler);
        return view;
    }

    private void init() {
        productItems = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        girdProductAdapter = new GirdProductAdapter(context);
        recyclerView.setIAdapter(girdProductAdapter);
        girdProductAdapter.setOnItemClickListener(new OnItemClickListener<ProductItem>() {
            @Override
            public void onItemClick(int position, ProductItem productItem, View v) {
                Intent myIntent = new Intent(context, DetailProductActivity.class);
                myIntent.putExtra(StaticVarriable.PRODUCT_ITEM, productItem.getId());
                startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
            }
        });
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoginActivity.requestManager.getUserListing(userId, token, String.valueOf(0), String.valueOf(20), userListingHandler);

            }
        });
    }

    private View view;
    private IRecyclerView recyclerView;
    private Context context;
    private GirdProductAdapter girdProductAdapter;
    private UserListingHandler userListingHandler;
    private ArrayList<ProductItem> productItems;

    private class UserListingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.GET_USER_LISTING:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadData(jsonArray);
                break;
            case StaticVarriable.ERROR_INTERNET:
                recyclerView.setRefreshing(false);
                break;
            default:
                break;
            }
        }
    }

    private void loadData(JSONArray jsonArray) {
        productItems.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ProductItem productItem = new ProductItem(jsonObject);
                productItems.add(productItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.setRefreshing(false);
        girdProductAdapter.setList(productItems);
    }


}
