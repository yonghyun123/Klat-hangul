package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

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
        TextView englishWordView = (TextView)findViewById(R.id.english);
        TextView translateWordView = (TextView)findViewById(R.id.translate);

        SpannableString s = new SpannableString("App Name");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9d00")), 0, "App Name".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        packageItem = new WordPackageItem();

        Intent intent = getIntent();

        packageItemList = (ArrayList<WordPackageItem>) intent.getSerializableExtra("ItemList");
        position = intent.getIntExtra("position",0);
        packageItem = packageItemList.get(position);

        koreanWordView.setText(packageItem.getKoreanWord()+"\n["+packageItem.getPartOfWord()+"]");
        englishWordView.setText(packageItem.getEnglishWord());
        translateWordView.setText(packageItem.getTranslateWord());

    }
}