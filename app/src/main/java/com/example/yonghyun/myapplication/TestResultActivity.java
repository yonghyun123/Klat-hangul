package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TestResultActivity extends AppCompatActivity {
    private ListView resultListView;

    private List<TestPackageItem> wrongAnswerList;
    private ArrayList<Integer> wrongCheckNumber;
    private ArrayList<Integer> answerArray;

    private TestResultAdapter testResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        resultListView = (ListView)findViewById(R.id.testResultView);

        SpannableString s = new SpannableString("App Name");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9d00")), 0, "App Name".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        Button btn = (Button) findViewById(R.id.test_result_btn);
        wrongAnswerList = new ArrayList<>();
        wrongCheckNumber = new ArrayList<Integer>();
        answerArray = new ArrayList<Integer>();

        Intent intent = getIntent();
        wrongAnswerList = (List<TestPackageItem>) intent.getSerializableExtra("wrongAnswer");
        wrongCheckNumber = (ArrayList<Integer>)intent.getSerializableExtra("checkArray");
        answerArray = (ArrayList<Integer>)intent.getSerializableExtra("answerArray");

        setListViewAdapter();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestResultActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setListViewAdapter(){
        testResultAdapter = new TestResultAdapter(this,R.layout.test_result_item, wrongAnswerList, wrongCheckNumber, answerArray);
        resultListView.setAdapter(testResultAdapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}