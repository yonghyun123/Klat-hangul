package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends Fragment {

    private ListView bookmarkListView;
    private TextView totalTextView;
    private List<WordPackageItem> bookmarkItemList;
    private ArrayAdapter<WordPackageItem> bookmarkAdapter;
    private DBWordHelper db;
    private TextView totalWordNumber;
    private SwipeLayout swipeLayout;

    public BookmarkActivity(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_bookmark, container, false);

        bookmarkListView = (ListView)v.findViewById(R.id.bookmarkList);
        totalTextView = (TextView)v.findViewById(R.id.totalNumber);

        bookmarkItemList = new ArrayList<>();
        db = new DBWordHelper(this.getActivity());

        bookmarkItemList = db.getAllWords();
//        setListViewHeader(v);
        setListViewAdapter();

        bookmarkListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast toast = Toast.makeText(v.getContext(),"몇번째?"+position,Toast.LENGTH_SHORT);
                toast.show();
                Intent fullWord = new Intent(getActivity(), ShowFullWord.class);
                fullWord.putExtra("ItemList", (Serializable) bookmarkItemList);
                fullWord.putExtra("position",position);
                BookmarkActivity.this.startActivity(fullWord);
            }
        });
        return v;
    }

    private void setListViewAdapter(){
        bookmarkAdapter = new BookmarkAdapter(this, R.layout.bookmark_item, bookmarkItemList);
        bookmarkListView.setAdapter(bookmarkAdapter);

        totalTextView.setText("("+bookmarkItemList.size()+")");
    }
    public void updateAdapter(){
        bookmarkAdapter.notifyDataSetChanged();

        totalTextView.setText("("+bookmarkItemList.size()+")");
    }
}