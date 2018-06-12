package com.xtlog.android.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ID = "extra_news_id";


    public static Intent newIntent(Context context, String newsId){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, newsId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailFragment detailFragment = DetailFragment.newInstance(getIntent().getStringExtra(EXTRA_NEWS_ID));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.activity_fragment_container, detailFragment)
                .commit();

    }
}
