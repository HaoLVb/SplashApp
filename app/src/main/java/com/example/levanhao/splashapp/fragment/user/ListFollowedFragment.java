package com.example.levanhao.splashapp.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levanhao.splashapp.R;
import com.example.levanhao.splashapp.StaticVarriable;
import com.example.levanhao.splashapp.activity.LoginActivity;
import com.example.levanhao.splashapp.activity.MainActivity;
import com.example.levanhao.splashapp.activity.UserInfoActivity;
import com.example.levanhao.splashapp.adapter.FollowAdapter;
import com.example.levanhao.splashapp.view.customview.RecyclerTouchListener;
import com.example.levanhao.splashapp.model.UserItemFollowModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListFollowedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private int userId;

    public ListFollowedFragment() {
        // Required empty public constructor
    }

    public static ListFollowedFragment newInstance(int param1) {
        ListFollowedFragment fragment = new ListFollowedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_followed, container, false);
        followedHandler = new FollowedHandler();
        LoginActivity.systemManager.getHandlerManager().setFollowedHandler(followedHandler);
        init();
        LoginActivity.requestManager.getFollowed(userId, MainActivity.token, String.valueOf(0), String.valueOf(20), followedHandler);
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        models = new ArrayList<>();
        followedAdapter = new FollowAdapter(context, models);
        recyclerView.setAdapter(followedAdapter);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent userIntent = new Intent(context, UserInfoActivity.class);
                userIntent.putExtra(StaticVarriable.TOKEN, MainActivity.token);
                userIntent.putExtra(StaticVarriable.USER_ID, models.get(position).getId());
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.hold);
                startActivity(userIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private View view;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<UserItemFollowModel> models;
    private FollowAdapter followedAdapter;
    private FollowedHandler followedHandler;

    private class FollowedHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case StaticVarriable.GET_LIST_FOLLOWED:
                JSONArray jsonArray = (JSONArray) msg.obj;
                loadData(jsonArray);

                break;
            case StaticVarriable.ERROR_INTERNET:
                break;

            default:
                break;
            }
        }
    }

    private void loadData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                UserItemFollowModel userItemFollowModel = new UserItemFollowModel(jsonObject);
                models.add(userItemFollowModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        followedAdapter.notifyDataSetChanged();
    }
}
