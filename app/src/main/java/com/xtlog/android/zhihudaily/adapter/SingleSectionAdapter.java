package com.xtlog.android.zhihudaily.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtlog.android.zhihudaily.activity.DetailActivity;
import com.xtlog.android.zhihudaily.activity.SingleSectionActivity;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.models.InSection;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.models.Sections;
import com.xtlog.android.zhihudaily.utils.RandomColor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/12/21.
 */

public class SingleSectionAdapter extends RecyclerView.Adapter<SingleSectionAdapter.SingleSectionViewHolder> {

    private List<InSection.StoryInSection> mStories;
    private static final String TAG = "SingleSectionAdapter";



    static class SingleSectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        InSection.StoryInSection mStory;
        ImageView image;
        TextView title;
        TextView date;

        public SingleSectionViewHolder (View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.item_single_section_image_view);
            title = (TextView)view.findViewById(R.id.item_single_section_title);
            date = (TextView)view.findViewById(R.id.item_single_section_date);
            view.setOnClickListener(this);

        }

        public void bindStory(InSection.StoryInSection story){
            mStory = story;
            Picasso.with(MyApplication.getContext()).load(mStory.getImages().get(0)).into(image);
            title.setText(mStory.getTitle());
            date.setText(mStory.getDisplay_date());
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailActivity.newIntent(SingleSectionActivity.sSingleSectionActivity, String.valueOf(mStory.getId()));
            SingleSectionActivity.sSingleSectionActivity.startActivity(intent);
        }
    }

    public SingleSectionAdapter(List<InSection.StoryInSection> storiesList){
        mStories = storiesList;
    }

    @Override
    public SingleSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_section_list, parent, false);
        SingleSectionViewHolder holder = new SingleSectionViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SingleSectionViewHolder holder, int position) {
        InSection.StoryInSection story = mStories.get(position);
        holder.bindStory(story);


    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }
}
