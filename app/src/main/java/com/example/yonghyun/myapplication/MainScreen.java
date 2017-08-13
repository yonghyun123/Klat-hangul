package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends Fragment {

    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private List<WordPackageItem> bookmarkItemList;
    private MainHorizontalViewAdapter horizontalViewAdapter;
    private DBWordHelper db;

    public MainScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_main_screen, container, false);
        AppCompatActivity activity = (AppCompatActivity)v.getContext();

        mViewPager = (ViewPager) v.findViewById(R.id.mainViewPager);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.mainRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        horizontalViewAdapter = new MainHorizontalViewAdapter(bookmarkItemList, this.getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.scrollToPosition(0);

        mRecyclerView.setAdapter(horizontalViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mViewPager.setAdapter(new pagerAdapter(activity.getSupportFragmentManager()));

        return v;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {
        bookmarkItemList = new ArrayList<WordPackageItem>();
        db = new DBWordHelper(this.getActivity());

        bookmarkItemList = db.getAllWords();
    }

    private class pagerAdapter extends FragmentStatePagerAdapter{

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position){
                case 0: return new WebViewFragment1();
                case 1: return new WebViewFragment2();
                default: return new WebViewFragment1();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}