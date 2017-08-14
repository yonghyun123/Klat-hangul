package com.example.yonghyun.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerAdapterScreen extends Fragment {

    private ViewPager mViewPager;


    public PagerAdapterScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = (View)inflater.inflate(R.layout.fragment_pager_adapter_screen, container, false);
        AppCompatActivity activity = (AppCompatActivity)v.getContext();
        mViewPager = (ViewPager) v.findViewById(R.id.mainViewPager);
        mViewPager.setAdapter(new pagerAdapter(activity.getSupportFragmentManager()));
        return v;
    }


    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position){
                case 0: return new WebViewFragment1();
                case 1: return new WebViewFragment2();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
