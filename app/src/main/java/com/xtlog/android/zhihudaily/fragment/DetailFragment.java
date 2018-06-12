package com.xtlog.android.zhihudaily.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.activity.CommentActivity;
import com.xtlog.android.zhihudaily.adapter.HotNewsAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.Extra;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.models.NewsContent;
import com.xtlog.android.zhihudaily.utils.CSS;
import com.xtlog.android.zhihudaily.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailFragment extends Fragment {

    private static final String ARG_NEWS_ID = "news_id";

    private String newsId;
    private String JSONString;
    private String JSONStringExtra;
    private String BigImageCode;
    private String HTMLCode;


    private NewsContent mNewsContent;
    private Extra mExtra;
    private static final int SEND_JSON = 1;
    private static final int SEND_EXTRA = 2;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private TextView commentsText;
    private TextView goodText;

    private static final String TAG = "DetailFragment";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static DetailFragment newInstance(String newsId){
        Bundle args = new Bundle();
        args.putString(ARG_NEWS_ID, newsId);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mWebView = (WebView)view.findViewById(R.id.fragment_detail_web_view);
        mProgressBar = (ProgressBar)view.findViewById(R.id.fragment_detail_progress_bar);


        download();
        downloadExtra();

        return view;
    }

    public Handler handler = new Handler(MyApplication.getContext().getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_JSON:
                    Gson gson = new Gson();
                    mNewsContent = gson.fromJson(JSONString, NewsContent.class);

                    BigImageCode = "<img src=" + "\"" + mNewsContent.getImage() + "\"" + "  />";


                    //在HTML代码的img-place-holder里嵌入<img>标签
                    StringBuilder sb = new StringBuilder();
                    sb.append(mNewsContent.getBody()).insert(91,BigImageCode);
                    HTMLCode = sb.toString();
                    //在HTML代码里插入CSS
                    String HTMLWithCssCode = "<style>" + CSS.CSSTEXT + "</style>" + HTMLCode;
                    //WebView加载HTML
                    mWebView.loadDataWithBaseURL(null, HTMLWithCssCode, "text/html","UTF-8", null);

                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;

                case SEND_EXTRA:
                    Gson gson1 = new Gson();
                    mExtra = gson1.fromJson(JSONStringExtra, Extra.class);
                    commentsText.setText(String.valueOf(mExtra.getComments()));
                    goodText.setText(String.valueOf(mExtra.getPopularity()));
                    break;

                default:
                    break;

            }
        }
    };

    private void download() {
        newsId = getArguments().getString(ARG_NEWS_ID);
        String api_url = "http://news-at.zhihu.com/api/4/news/" + newsId;

        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONString = response.body().string();
                Message msg = new Message();
                msg.what = SEND_JSON;
                handler.sendMessage(msg);

            }
        });
    }

    private void downloadExtra(){
        newsId = getArguments().getString(ARG_NEWS_ID);
        String api_url = "http://news-at.zhihu.com/api/4/story-extra/" + newsId;

        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONStringExtra = response.body().string();
                Message msg = new Message();
                msg.what = SEND_EXTRA;
                handler.sendMessage(msg);

            }
        });

    }

    //菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail, menu);
        final MenuItem commentsItem = menu.findItem(R.id.menu_item_comments_item);
        final MenuItem goodItem = menu.findItem(R.id.menu_item_good_item);

        commentsText = (TextView) menu.findItem(R.id.menu_item_comments_item).getActionView().findViewById(R.id.menu_item_comments_text);
        goodText = (TextView) menu.findItem(R.id.menu_item_good_item).getActionView().findViewById(R.id.menu_item_good_text);


        commentsItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(commentsItem);
            }
        });
        goodItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(goodItem);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_share_item:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, mNewsContent.getShare_url());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, mNewsContent.getTitle());
                startActivity(shareIntent);

                return true;

            case R.id.menu_item_comments_item:
                Intent commentsIntent = CommentActivity.newIntent(MyApplication.getContext(), newsId);
                startActivity(commentsIntent);

                return true;
            case R.id.menu_item_good_item:
                Toast.makeText(MyApplication.getContext(), "你没有登录，无法使用此功能", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }





}
