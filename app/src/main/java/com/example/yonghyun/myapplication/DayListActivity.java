package com.example.yonghyun.myapplication;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


public class DayListActivity extends AppCompatActivity {
    int i = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);

        ListView listView = (ListView)findViewById(R.id.dayList);
        ListViewAdapter adapter = new ListViewAdapter();

        listView.setAdapter(adapter);

        for(i = 0; i < 30; i++){
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_menu_camera), "day "+ (i+1));
        }

    }
}
