package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends Fragment {

    private RecyclerView mRecyclerView;

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


        mRecyclerView = (RecyclerView) v.findViewById(R.id.mainRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        horizontalViewAdapter = new MainHorizontalViewAdapter(bookmarkItemList, this.getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.scrollToPosition(0);

        mRecyclerView.setAdapter(horizontalViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, new PagerAdapterScreen());
        transaction.addToBackStack(null).commit();
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
}