package com.example.yonghyun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ArrayList<WordPackageItem> wronglist;

    private ArrayList<WordPackageItem> wrongItemList;
    private ArrayAdapter<WordPackageItem> wrongWordAdapter;
    private ListView wrongListView;

    private DBWordHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        wrongItemList = new ArrayList<>();
        SpannableString s = new SpannableString("App Name");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9d00")), 0, "App Name".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        TextView score = (TextView) findViewById(R.id.scoreText);
        Button btn = (Button) findViewById(R.id.ok_btn);
        wrongListView = (ListView) findViewById(R.id.wrongItem);
        wronglist = new ArrayList<>();
        int pos = 0;

        Intent intent = getIntent();
        pos = intent.getExtras().getInt("positive");
        score.setText(pos+ " / " + 20);
        wronglist = (ArrayList<WordPackageItem>)intent.getSerializableExtra("list");

        setWrongWords();


        SharedPreferences sharedPreferences = this.getSharedPreferences("status", Context.MODE_PRIVATE);
        boolean []checkedStatus = new boolean[wrongItemList.size()];
        for(int i = 0; i < checkedStatus.length; i++){

            checkedStatus[i] = sharedPreferences.getBoolean(Integer.toString(i), false);
        }
        setListViewAdapter(checkedStatus);

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
        db = new DBWordHelper(this);
        wrongItemList.addAll(wronglist);
    }

    private void setListViewAdapter(boolean[] checkedStatus){
        wrongWordAdapter = new WrongListViewAdapter(this, R.layout.word_list_item, wrongItemList, checkedStatus);
        wrongListView.setAdapter(wrongWordAdapter);
    }

    public void updateAdapter(){
        wrongWordAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}