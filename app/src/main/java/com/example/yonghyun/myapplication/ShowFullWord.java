package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowFullWord extends AppCompatActivity{
    private ArrayList<WordPackageItem> packageItemList = new ArrayList<>();
    private WordPackageItem packageItem;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_word);
        TextView koreanWordView = (TextView)findViewById(R.id.korea);
        TextView partOfWordView = (TextView)findViewById(R.id.partOfWord);
        TextView englishWordView = (TextView)findViewById(R.id.english);
        TextView translateWordView = (TextView)findViewById(R.id.translate);



        packageItem = new WordPackageItem();

        Intent intent = getIntent();

        packageItemList = (ArrayList<WordPackageItem>) intent.getSerializableExtra("ItemList");
        position = intent.getIntExtra("position",0);
        packageItem = packageItemList.get(position);

        koreanWordView.setText(packageItem.getKoreanWord());
        partOfWordView.setText(packageItem.getPartOfWord());
        englishWordView.setText(packageItem.getEnglishWord());
        translateWordView.setText(packageItem.getTranslateWord());

    }
}
