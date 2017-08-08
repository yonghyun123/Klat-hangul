package com.example.yonghyun.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by yonghyun on 2017. 7. 14..
 */

public class ListViewItem {
    private Drawable iconDrawable;
    private String dayStr;

    public void setIcon(Drawable icon){
        this.iconDrawable = icon;
    }

    public void setDay(String day){
        this.dayStr = day;
    }

    public Drawable getIcon(){ return this.iconDrawable; }
    public String getDay(){ return this.dayStr; }
}