package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class DayListActivity extends AppCompatActivity {
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);

        ListView listView = (ListView) findViewById(R.id.dayList);
        ListViewAdapter adapter = new ListViewAdapter();

        listView.setAdapter(adapter);

        for (i = 0; i < 30; i++) {
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_menu_camera), "day " + (i + 1));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String day = item.getDay();
                Drawable icon = item.getIcon();
                Toast toast = Toast.makeText(getApplicationContext(),"ddd",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(DayListActivity.this, WordListActivity.class);
                DayListActivity.this.startActivity(intent);

            }
        });
    }
}
