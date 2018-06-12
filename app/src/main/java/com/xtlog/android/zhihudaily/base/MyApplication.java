package com.xtlog.android.zhihudaily.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2016/12/21.
 */

public class MyApplication extends Application {
    private static Context context;
    public static int colorIndex;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        colorIndex = 0;
    }

    public static Context getContext(){
        return context;
    }


}
