package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ArrayList<String> wronglist;

    private ArrayList<WordPackageItem> wrongItemList;
    private WordPackageItem wrongItem;
    private ArrayAdapter<WordPackageItem> wrongWordAdapter;
    private ListView wrongListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView score = (TextView) findViewById(R.id.scoreText);
        Button btn = (Button) findViewById(R.id.ok_btn);
        wrongListView = (ListView) findViewById(R.id.wrongItem);

        score.setText(QuizActivity.positive + " / " + 20);
        Intent intent = getIntent();
        wronglist = intent.getStringArrayListExtra("list");

        wrongItemList = new ArrayList<>();
        setWrongWords();
        setListViewAdapter();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void setWrongWords(){
        int cnt = 0;
        wrongItem = new WordPackageItem();
        for(int i = 0; i<wronglist.size();i++){
            cnt++;
            if(i%2 == 0){
                wrongItem.setKoreanWord(wronglist.get(i));
            }
            else{
                wrongItem.setEnglishWord(wronglist.get(i));
            }
            if(cnt != 0 && cnt%2 == 0){
                wrongItemList.add(wrongItem);
                wrongItem = new WordPackageItem();
            }
        }
    }

    private void setListViewAdapter(){
        wrongWordAdapter = new WrongListViewAdapter(this, R.layout.word_list_item, wrongItemList);
        wrongListView.setAdapter(wrongWordAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}