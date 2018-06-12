package com.xtlog.android.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xtlog.android.zhihudaily.fragment.ColumnViewFragment;
import com.xtlog.android.zhihudaily.fragment.HotNewsFragment;
import com.xtlog.android.zhihudaily.fragment.LatestNewsFragment;
import com.xtlog.android.zhihudaily.fragment.UserFragment;

/**
 * Created by admin on 2016/12/21.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LatestNewsFragment();
            case 1:
                return new HotNewsFragment();
            case 2:
                return new ColumnViewFragment();
            case 3:
                return new UserFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "最新";
            case 1:
                return "热门";
            case 2:
                return "栏目";
            case 3:
                return "我的";
            default:
                return null;
        }
    }
}
