package com.xtlog.android.zhihudaily.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.activity.MainActivity;
import com.xtlog.android.zhihudaily.adapter.LatestNewsAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LatestNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "LatestNewsFragment";
    private String JSONString;
    private LatestNews mLatestNews;
    private List<LatestNews.Story> storiesList;

    private RecyclerView mLatestRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int SEND_JSON = 1;
    private static final int REFRESH_COMPLETE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_news, container, false);
        mLatestRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_latest_news_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_latest_news_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.ZhiHu_blue));
        mLatestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLatestRecyclerView.setAdapter(new LatestNewsAdapter(new ArrayList<LatestNews.Story>()));

        download(SEND_JSON);

        return view;
    }

    public Handler handler = new Handler(MyApplication.getContext().getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_JSON:
                    parseJSONtoUI();
                    SharedPreferences.Editor editor = MainActivity.sMainActivity.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    editor.putString("json", JSONString);
                    editor.apply();
                    break;

                case REFRESH_COMPLETE:
                    parseJSONtoUI();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;

                default:
                    break;

            }
        }
    };

    public void download(final int request) {
        HttpUtil.sendOkHttpRequest("http://news-at.zhihu.com/api/4/news/latest", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONString = response.body().string();
                Message msg = new Message();
                msg.what = request;
                handler.sendMessage(msg);

            }
        });
    }

    private void parseJSONtoUI(){
        Gson gson = new Gson();
        mLatestNews = gson.fromJson(JSONString, LatestNews.class);
        storiesList = mLatestNews.getStories();
        LatestNewsAdapter latestNewsAdapter = new LatestNewsAdapter(storiesList);
        mLatestRecyclerView.setAdapter(latestNewsAdapter);
    }


    @Override
    public void onRefresh() {
        download(REFRESH_COMPLETE);
    }


}
