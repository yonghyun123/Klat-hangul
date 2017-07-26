package com.example.yonghyun.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private ListView wordListView;
    private ArrayAdapter<String> wordAdapter;
    private ArrayList<String> wordList;
    private TextView totalWordNumber;
    private SwipeLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        wordListView = (ListView) findViewById(R.id.koreanWord);

        wordList = new ArrayList<>();
        getDataFromFile();
        setListViewHeader();
        setListViewAdapter();
    }
    private void getDataFromFile(){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.easy_korean),"UTF-8"));
            String line = reader.readLine();
            while(line != null && !line.equals(" ")){
                line = reader.readLine();
                wordList.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void setListViewHeader(){
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.hearder_word_list, wordListView, false);
        totalWordNumber = (TextView)header.findViewById(R.id.total);
        swipeLayout = (SwipeLayout)header.findViewById(R.id.swipe_layout);
        setSwipeViewFeatures();
        wordListView.addHeaderView(header);
    }
    private void setSwipeViewFeatures(){
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left,findViewById(R.id.bottomWrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener(){

            @Override
            public void onStartOpen(SwipeLayout layout) {
                Log.i("kimtag","on Start Open");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Log.i("kimtag","the BottomView totally show");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Log.i("kimtag","the BottomView totally close");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                Log.i("kimtag","onClose");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                Log.i("kimtag","on Swiping");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }
    private void setListViewAdapter(){
        wordAdapter = new WordListViewAdapter(this, R.layout.word_list_item, wordList);
        wordListView.setAdapter(wordAdapter);

        totalWordNumber.setText("("+wordList.size()+")");
    }
    public void updateAdapter(){
        wordAdapter.notifyDataSetChanged();

        totalWordNumber.setText("("+wordList.size()+")");
    }
}
