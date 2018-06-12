package com.xtlog.android.zhihudaily.fragment;


import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.activity.CommentActivity;
import com.xtlog.android.zhihudaily.adapter.CommentAdapter;
import com.xtlog.android.zhihudaily.adapter.HotNewsAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.models.ShortComment;
import com.xtlog.android.zhihudaily.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_NEWS_ID = "arg_news_id";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private TextView mEmptyText;
    private String newsId;
    private String JSONString;
    private ShortComment mShortComment;
    private static final int SEND_JSON = 1;
    private static final int REFRESH_COMPLETE = 2;
    private static final String TAG = "CommentFragment";

    public static CommentFragment newInstance(String newsId){
        Bundle args = new Bundle();
        args.putString(ARG_NEWS_ID, newsId);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_comment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setAdapter(new CommentAdapter(new ArrayList<ShortComment.Short>()));

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_comment_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.ZhiHu_blue));

        mProgressBar = (ProgressBar)view.findViewById(R.id.fragment_comments_progress_bar);
        mEmptyText =(TextView)view.findViewById(R.id.fragment_comment_empty_view);

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

                case REFRESH_COMPLETE:
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
        mShortComment = gson.fromJson(JSONString, ShortComment.class);

        CommentAdapter commentAdapter = new CommentAdapter(mShortComment.getComments());
        mRecyclerView.setAdapter(commentAdapter);
        mProgressBar.setVisibility(View.INVISIBLE);
        if(commentAdapter.getItemCount() == 0){
            mEmptyText.setVisibility(View.VISIBLE);
        }
    }

    public void download(final int request) {
        newsId = getArguments().getString(ARG_NEWS_ID);
        String url = "http://news-at.zhihu.com/api/4/story/"+ newsId +"/short-comments";

        HttpUtil.sendOkHttpRequest(url, new Callback() {
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

    //菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_comments, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_add_comment_item:
                Toast.makeText(MyApplication.getContext(), "你没有登录，无法使用此功能", Toast.LENGTH_SHORT).show();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public void onRefresh() {
        download(REFRESH_COMPLETE);
    }
}
