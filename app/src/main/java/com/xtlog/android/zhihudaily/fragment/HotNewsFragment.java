package com.xtlog.android.zhihudaily.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.adapter.HotNewsAdapter;
import com.xtlog.android.zhihudaily.adapter.LatestNewsAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.HotNews;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String JSONString;
    private static final int SEND_JSON = 1;
    private static final int REFRESH_COMPETE = 2;
    private List<LatestNews.TopStory> mTopStories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_hot_news_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_hot_news_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.ZhiHu_blue));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setAdapter(new HotNewsAdapter(new ArrayList<LatestNews.TopStory>()));

        download(SEND_JSON);

        return view;
    }

    public Handler handler = new Handler(MyApplication.getContext().getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_JSON:
                    parseJSONtoUI();
                    break;

                case REFRESH_COMPETE:
                    parseJSONtoUI();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;

            }
        }
    };

    private void parseJSONtoUI(){
        Gson gson = new Gson();
        LatestNews latestNews = gson.fromJson(JSONString, LatestNews.class);
        mTopStories = latestNews.getTopStories();
        HotNewsAdapter hotNewsAdapter = new HotNewsAdapter(mTopStories);
        mRecyclerView.setAdapter(hotNewsAdapter);
    }

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

    @Override
    public void onRefresh() {
        download(REFRESH_COMPETE);
    }
}
