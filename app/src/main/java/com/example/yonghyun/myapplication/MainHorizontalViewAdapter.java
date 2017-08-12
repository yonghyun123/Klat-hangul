package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 8. 11..
 */

public class MainHorizontalViewAdapter extends RecyclerView.Adapter<MainHorizontalViewAdapter.ViewHolder> {
    private List<WordPackageItem> bookmarkWordList =new ArrayList<>();
    private MyListenr myListenr = new MyListenr();


    public MainHorizontalViewAdapter(List<WordPackageItem> bookmarkWordList, Activity activity){
        this.bookmarkWordList = bookmarkWordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_bookmark_item, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mainKorean.setText(bookmarkWordList.get(position).getKoreanWord());
        holder.mainEnglish.setText(bookmarkWordList.get(position).getEnglishWord());
        holder.mainEnglish.setOnClickListener(myListenr);
        holder.mainKorean.setOnClickListener(myListenr);
    }

    @Override
    public int getItemCount() {
        return bookmarkWordList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mainKorean;
        public TextView mainEnglish;

        public ViewHolder(View v) {
            super(v);
            mainKorean = (TextView)v.findViewById(R.id.mainBookmarkKorean);
            mainEnglish = (TextView)v.findViewById(R.id.mainBookmarkEnglish);

        }
    }

    public class MyListenr implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity)v.getContext();
            for(int i = 0;i <activity.getFragmentManager().getBackStackEntryCount() ;i++){
                activity.getFragmentManager().popBackStack();
            }
            FragmentTransaction tr = activity.getFragmentManager().beginTransaction();
            tr.replace(R.id.contentPanel, new BookmarkActivity());
            tr.addToBackStack(null);
            tr.commit();
        }
    }
}
