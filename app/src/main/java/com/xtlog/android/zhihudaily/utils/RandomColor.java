package com.xtlog.android.zhihudaily.utils;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.base.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2016/12/23.
 */

public class RandomColor {
    public int index = 0;

    public static String getColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#" + r+g+b;
    }

    public static String getColor(){
        List<String> list = new ArrayList<>();
        list.add("#414141");
        list.add("#378d3b");
        list.add("#778f9b");
        list.add("#778f9b");
        list.add("#778f9b");
        list.add("#4c6977");
        list.add("#fd854d");
        int index = new Random().nextInt(7);
        return list.get(index);
    }

    public static String getMDColor(){
        List<String> list = new ArrayList<>();
        list.add("#4CAF50");//灰
        list.add("#9E9E9E");//绿
        list.add("#009688");//蓝
        list.add("#00bcd4");//碧
        int i = (MyApplication.colorIndex)%4;
        MyApplication.colorIndex++;
        return list.get(i);
    }
}
