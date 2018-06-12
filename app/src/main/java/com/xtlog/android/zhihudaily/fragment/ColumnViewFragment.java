package com.xtlog.android.zhihudaily.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.adapter.LatestNewsAdapter;
import com.xtlog.android.zhihudaily.adapter.SectionsAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.models.Sections;
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
public class ColumnViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Sections mSections;
    private List<Sections.Section> mSectionList;
    private String JSONString;
    private static final int SEND_JSON = 1;
    private static final String TAG = "ColumnViewFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_column_view, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_section_recycler_view);
//        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gm = new GridLayoutManager(MyApplication.getContext(),2);
        mRecyclerView.setLayoutManager(gm);
        mRecyclerView.setAdapter(new SectionsAdapter(new ArrayList<Sections.Section>()));

        download();
        return view;
    }

    public Handler handler = new Handler(MyApplication.getContext().getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_JSON:
                    Gson gson = new Gson();
                    mSections = gson.fromJson(JSONString, Sections.class);
                    mSectionList = mSections.getData();
                    SectionsAdapter sectionsAdapter = new SectionsAdapter(mSectionList);
                    mRecyclerView.setAdapter(sectionsAdapter);
                    break;
                default:
                    break;

            }
        }
    };

    public void download() {
        HttpUtil.sendOkHttpRequest("http://news-at.zhihu.com/api/3/sections", new Callback() {
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

}
