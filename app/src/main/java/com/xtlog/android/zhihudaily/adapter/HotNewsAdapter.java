package com.xtlog.android.zhihudaily.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtlog.android.zhihudaily.activity.DetailActivity;
import com.xtlog.android.zhihudaily.activity.MainActivity;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.models.HotNews;
import com.xtlog.android.zhihudaily.models.LatestNews;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/12/21.
 */

public class HotNewsAdapter extends RecyclerView.Adapter<HotNewsAdapter.NewsViewHolder> {

    private List<LatestNews.TopStory> mTopStories;


    static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LatestNews.TopStory mTopStory;
        ImageView image;
        TextView title;
        public NewsViewHolder (View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.item_hot_news_image_view);
            title = (TextView)view.findViewById(R.id.item_hot_news_title);
            view.setOnClickListener(this);
        }

        public void bindTopStory(LatestNews.TopStory topStory){
            mTopStory = topStory;
            title.setText(topStory.getTitle());
            Picasso.with(MyApplication.getContext()).load(topStory.getImage()).into(image);
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailActivity.newIntent(MainActivity.sMainActivity, String.valueOf(mTopStory.getId()));
            MainActivity.sMainActivity.startActivity(intent);
        }
    }

    public HotNewsAdapter(List<LatestNews.TopStory> topStoriesList){
        mTopStories = topStoriesList;
}

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_news_list, parent, false);
        NewsViewHolder holder = new NewsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        LatestNews.TopStory topStory = mTopStories.get(position);
        holder.bindTopStory(topStory);

    }

    @Override
    public int getItemCount() {
        return mTopStories.size();
    }
}
