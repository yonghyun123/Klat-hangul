package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private ListView wordListView;
    private ArrayAdapter<WordPackageItem> koreanWordAdapter;
    private WordPackageItem packageItems;
    private ArrayList<WordPackageItem> packageItemsList;

    private ArrayList<String> koreanWordList; //korean word

    private TextView totalWordNumber;
    private SwipeLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        wordListView = (ListView) findViewById(R.id.koreanWord);

        koreanWordList = new ArrayList<>();

        packageItemsList = new ArrayList<>();

        getDataFromFile();
//        setListViewHeader();
        setListViewAdapter();

        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(),"몇번째?"+position,Toast.LENGTH_SHORT);
                toast.show();
                Intent fullWord = new Intent(WordListActivity.this, ShowFullWord.class);
                fullWord.putExtra("ItemList",packageItemsList);
                fullWord.putExtra("position",position);
                WordListActivity.this.startActivity(fullWord);
            }
        });
    }
    private void getDataFromFile(){

        BufferedReader reader = null;
        String[] splitStr = null;
        try{
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.easy_day1),"UTF-8"));
            String line = null;

            while((line = reader.readLine())!=null && !line.equals(" ")){
                packageItems = new WordPackageItem();
                splitStr = null;
                splitStr = line.split("\t");

                for(int i=0; i < splitStr.length; i++){
                    splitStr[i] = splitStr[i].trim();
                }
                packageItems.setKoreanWord(splitStr[0]);
                packageItems.setPartOfWord(splitStr[1]);
                packageItems.setEnglishWord(splitStr[2]);
                packageItems.setTranslateWord(splitStr[3]);
                packageItemsList.add(packageItems);

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
        koreanWordAdapter = new WordListViewAdapter(this, R.layout.word_list_item, packageItemsList);

        wordListView.setAdapter(koreanWordAdapter);

//        totalWordNumber.setText("("+packageItemsList.size()+")");
    }
    public void updateAdapter(){
        koreanWordAdapter.notifyDataSetChanged();

//        totalWordNumber.setText("("+packageItemsList.size()+")");
    }
}
