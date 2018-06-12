package com.xtlog.android.zhihudaily.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtlog.android.zhihudaily.activity.MainActivity;
import com.xtlog.android.zhihudaily.activity.SingleSectionActivity;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.models.LatestNews;
import com.xtlog.android.zhihudaily.models.Sections;
import com.xtlog.android.zhihudaily.utils.RandomColor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/12/21.
 */

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionViewHolder> {

    private List<Sections.Section> mSections;
    private static final String TAG = "SectionsAdapter";



    static class SectionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView desc;
        TextView title;
        TextView enter;
        LinearLayout mLinearLayout;
        public SectionViewHolder (View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.item_sections_image);
            title = (TextView)view.findViewById(R.id.item_sections_title);
            desc = (TextView)view.findViewById(R.id.item_sections_desc);
            enter = (TextView)view.findViewById(R.id.item_sections_enter);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.item_sections_linear_layout);
        }

    }

    public SectionsAdapter(List<Sections.Section> sectionList){
        mSections = sectionList;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sections_list, parent, false);
        SectionViewHolder holder = new SectionViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final Sections.Section section = mSections.get(position);
        holder.mLinearLayout.setBackgroundColor(Color.parseColor(RandomColor.getMDColor()));
        holder.title.setText(section.getName());
        holder.desc.setText(section.getDescription());
        Picasso.with(MyApplication.getContext())
                .load(TextUtils.isEmpty(section.getThumbnail())? null : section.getThumbnail())
                .placeholder(R.drawable.section_image_holder)
                .into(holder.image);
        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = SingleSectionActivity.newIntent(MainActivity.sMainActivity, String.valueOf(section.getId()), section.getName());
                MainActivity.sMainActivity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }
}
