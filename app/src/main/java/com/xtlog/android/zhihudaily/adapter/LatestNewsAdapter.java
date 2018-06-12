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
import com.xtlog.android.zhihudaily.models.LatestNews;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/12/21.
 */

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.NewsViewHolder> {

    private List<LatestNews.Story> mStories;



    static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LatestNews.Story mStory;
        ImageView storyImage;
        TextView storyTitle;
        public NewsViewHolder (View view){
            super(view);
            storyImage = (ImageView) view.findViewById(R.id.item_latest_news_image_view);
            storyTitle = (TextView)view.findViewById(R.id.item_latest_news_title);
            view.setOnClickListener(this);
        }

        public void bindStory(LatestNews.Story story){
            mStory = story;
            storyTitle.setText(story.getTitle());
            Picasso.with(MyApplication.getContext()).load(story.getImages().get(0)).into(storyImage);
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailActivity.newIntent(MainActivity.sMainActivity, String.valueOf(mStory.getId()));
            MainActivity.sMainActivity.startActivity(intent);
        }
    }

    public LatestNewsAdapter(List<LatestNews.Story> storiesList){
        mStories = storiesList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_news_list, parent, false);
        NewsViewHolder holder = new NewsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        LatestNews.Story story = mStories.get(position);
        holder.bindStory(story);

    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }
}
