package com.example.yonghyun.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        String fileName = "easy_korean.csv";
        List<String[]> content = new ArrayList<String[]>();
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(fileName));
            content = reader.readAll();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                
            }
        }


    }
    private void setListViewHeader(){}
    private void setListViewAdapter(){}
}
