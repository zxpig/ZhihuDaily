package com.xtlog.android.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.fragment.CommentFragment;

public class CommentActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ID = "extra_news_id";


    public static Intent newIntent(Context context, String newsId){
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, newsId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        CommentFragment commentFragment = CommentFragment.newInstance(getIntent().getStringExtra(EXTRA_NEWS_ID));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.activity_comments_fragment_container, commentFragment)
                .commit();

    }
}
