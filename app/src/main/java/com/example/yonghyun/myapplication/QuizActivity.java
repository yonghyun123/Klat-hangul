package com.example.yonghyun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private ArrayList<WordPackageItem> packageItemsList;
    private WordPackageItem packageItems;
    private ArrayList<String> wrongItemsList;
    InputMethodManager imm;

    int index = 0;
    int positive = 0;
    int progress = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SpannableString s = new SpannableString("Main");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9d00")), 0, "Main".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        final TextView txt = (TextView) findViewById(R.id.quizText);
        final TextView status = (TextView) findViewById(R.id.statusText);
        final Button btn = (Button) findViewById(R.id.submitButton);
        final Button exitBtn = (Button) findViewById(R.id.exitButton);
        final EditText edit = (EditText) findViewById(R.id.editText);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.quizscreen);

        long seed = System.nanoTime();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        packageItemsList = new ArrayList<>();
        wrongItemsList = new ArrayList<>();

        getDataFromFile();

        Collections.shuffle(packageItemsList, new Random(seed));
        txt.setText(packageItemsList.get(index).getKoreanWord().toString());
        status.setText(progress + "/20");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct;
                progress++;
                String answer = edit.getText().toString();
                edit.setText(null);
                correct = is_correct(answer);
                if (correct == true) {
                    Toast.makeText(view.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    positive++;
                } else {
                    Toast.makeText(view.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    wrongItemsList.add(packageItemsList.get(index).getKoreanWord().toString());
                    wrongItemsList.add(packageItemsList.get(index).getEnglishWord().toString());
                }
                if (progress <= 20) {
                    progressBar.setProgress(progress);
                    status.setText(progress + "/20");
                    index++;
                    txt.setText(packageItemsList.get(index).getKoreanWord().toString());
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("positive", positive);
                    intent.putStringArrayListExtra("list", wrongItemsList);
                    startActivity(intent);
                }
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                switch (v.getId()) {
                    case R.id.quizText:
                        break;
                    case R.id.statusText:
                        break;
                    case R.id.progressBar:
                        break;
                    case R.id.quizscreen:
                        break;
                }
            }
        };
        txt.setOnClickListener(myClickListener);
        status.setOnClickListener(myClickListener);
        progressBar.setOnClickListener(myClickListener);
        layout.setOnClickListener(myClickListener);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void getDataFromFile(){
        BufferedReader reader = null;
        String[] splitstr = null;
        try{
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.easy_day1),"UTF-8"));
            String line = null;

            while((line = reader.readLine())!=null && !line.equals(" ")){
                packageItems = new WordPackageItem();
                splitstr = null;
                splitstr = line.split("\t");

                for(int i=0;i<splitstr.length; i++){
                    splitstr[i] = splitstr[i].trim();
                }
                packageItems.setKoreanWord(splitstr[0]);
                packageItems.setEnglishWord(splitstr[2]);
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

    public boolean is_correct(String ans){
        String english = packageItemsList.get(index).getEnglishWord().toString();
        String[] spliteng = null;
        ans = ans.toLowerCase();
        ans = ans.trim();

        spliteng = english.split(";");
        for(int i=0;i<spliteng.length; i++){
            spliteng[i] = spliteng[i].trim();
            spliteng[i] = spliteng[i].toLowerCase();
            if(ans.equals(spliteng[i]) == true){
                return true;
            }
        }
        return false;
    }
}