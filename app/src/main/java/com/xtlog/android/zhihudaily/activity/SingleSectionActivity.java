package com.xtlog.android.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.fragment.CommentFragment;
import com.xtlog.android.zhihudaily.fragment.SingleSectionFragment;

public class SingleSectionActivity extends AppCompatActivity {

    private static final String EXTRA_SECTION_ID = "extra_section_id";
    private static final String EXTRA_SECTION_NAME = "extra_section_name";
    private ActionBar mActionBar;
    private String label = "栏目 · ";

    public static SingleSectionActivity sSingleSectionActivity;

    public static Intent newIntent(Context context, String sectionId, String sectionName){
        Intent intent = new Intent(context, SingleSectionActivity.class);
        intent.putExtra(EXTRA_SECTION_ID, sectionId);
        intent.putExtra(EXTRA_SECTION_NAME, sectionName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sSingleSectionActivity = this;
        mActionBar = getSupportActionBar();
        label += getIntent().getStringExtra(EXTRA_SECTION_NAME);
        mActionBar.setTitle(label);
        setContentView(R.layout.activity_single_section);

        SingleSectionFragment singleSectionFragment = SingleSectionFragment.newInstance(getIntent().getStringExtra(EXTRA_SECTION_ID));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.activity_single_section_fragment_container, singleSectionFragment)
                .commit();
    }

}
