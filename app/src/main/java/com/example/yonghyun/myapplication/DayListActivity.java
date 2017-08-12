package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DayListActivity extends Fragment {
    int i = 0;

    public DayListActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_day_list, container, false);
        final FragmentTransaction tr = getFragmentManager().beginTransaction();
        final Fragment WordListActivityFragment = new WordListActivity();

        ListView listView = (ListView) v.findViewById(R.id.dayList);
        ListViewAdapter adapter = new ListViewAdapter();


        listView.setAdapter(adapter);

        for (i = 0; i < 30; i++) {
            adapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_menu_camera), "day " + (i + 1));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String day = item.getDay();
                Drawable icon = item.getIcon();
                Toast toast = Toast.makeText(v.getContext(),"ddd",Toast.LENGTH_SHORT);
                toast.show();

                if(State_Field.getState()==true){
                    tr.replace(R.id.contentPanel, WordListActivityFragment);
                }
                else{
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    startActivity(intent);
                }
                tr.addToBackStack(null); tr.commit();
            }
        });
        return v;
    }
}