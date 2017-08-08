package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 7. 25..
 */

public class WrongListViewAdapter extends ArrayAdapter<WordPackageItem> {
    final private ResultActivity activity;
    private List<WordPackageItem> WrongWord = new ArrayList<>();

    public WrongListViewAdapter(ResultActivity context, int resource, List<WordPackageItem> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.WrongWord = objects;
    }
    @Override
    public WordPackageItem getItem(int position){
        return WrongWord.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.word_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        WordPackageItem item = WrongWord.get(position);
        holder.name.setText(item.getKoreanWord());
        holder.btnEdit.setText(item.getEnglishWord());

        return convertView;
    }

    private class ViewHolder{
        private TextView name;
        private TextView btnEdit;
        private CheckBox btnDelete;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v){
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnDelete = (CheckBox)v.findViewById(R.id.checkbox);
            btnEdit = (TextView)v.findViewById(R.id.editQuery);
            name = (TextView)v.findViewById(R.id.name);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
    }
}