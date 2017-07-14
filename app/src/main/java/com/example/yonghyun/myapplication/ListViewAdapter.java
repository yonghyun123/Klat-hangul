package com.example.yonghyun.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yonghyun on 2017. 7. 14..
 */

public class ListViewAdapter extends BaseAdapter{

    private ArrayList<ListViewItem> listViewDayList = new ArrayList<>();

    public ListViewAdapter(){} // initializer

    @Override
    public int getCount() {
        return listViewDayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewDayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int postion = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_day, parent, false);
        }

        ImageView iconImageView = (ImageView)convertView.findViewById(R.id.dayImage);
        TextView dayTextView = (TextView)convertView.findViewById(R.id.dayText);

        ListViewItem listViewDay = listViewDayList.get(position);

        iconImageView.setImageDrawable(listViewDay.getIcon());
        dayTextView.setText(listViewDay.getDay());


        return convertView;
    }

    //addItem String day and icon insert to the ListViewItem

    public void addItem(Drawable icon, String day){
        ListViewItem listItem = new ListViewItem();

        listItem.setIcon(icon);
        listItem.setDay(day);

        listViewDayList.add(listItem);
    }
}
