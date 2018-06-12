package com.xtlog.android.zhihudaily.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.adapter.CommentAdapter;
import com.xtlog.android.zhihudaily.adapter.SingleSectionAdapter;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.InSection;
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
public class SingleSectionFragment extends Fragment {

    private static final String ARG_SECTION_ID = "arg_section_id";
    private String JSONString;
    private String sectionId;
    private static final int SEND_JSON = 1;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;


    public static SingleSectionFragment newInstance(String sectionId){
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_ID, sectionId);
        SingleSectionFragment singleSectionFragment = new SingleSectionFragment();
        singleSectionFragment.setArguments(args);
        return singleSectionFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_section, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_single_section_recycler_view);
        mProgressBar = (ProgressBar)view.findViewById(R.id.fragment_single_section_progress_bar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setAdapter(new SingleSectionAdapter(new ArrayList<InSection.StoryInSection>()));

        download();
        return view;

    }

    public Handler handler = new Handler(MyApplication.getContext().getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_JSON:
                    Gson gson = new Gson();
                    InSection inSection = gson.fromJson(JSONString, InSection.class);
                    SingleSectionAdapter singleSectionAdapter = new SingleSectionAdapter(inSection.getStories());
                    mRecyclerView.setAdapter(singleSectionAdapter);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;

            }
        }
    };

    public void download() {
        sectionId = getArguments().getString(ARG_SECTION_ID);
        String url = "http://news-at.zhihu.com/api/4/section/"+ sectionId;

        HttpUtil.sendOkHttpRequest(url, new Callback() {
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
