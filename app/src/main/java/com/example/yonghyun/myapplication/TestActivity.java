package com.example.yonghyun.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private List<TestPackageItem> testPackageItemList;
    private List<TestPackageItem> wrongPackageItemList;
    private TestPackageItem testPackageItem;
    private TextView questionView;
    private TextView exampleView;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton answerView1;
    private RadioButton answerView2;
    private RadioButton answerView3;
    private RadioButton answerView4;
    private Button answerButton;
    private long seed;
    private int numberOfQuestion;
    private int index;
    private int positive;
    private int wrongIndex;
    private int checkIndex;
    private ArrayList<Integer> checkArray;
    private ArrayList<Integer> answerArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        numberOfQuestion = 0;
        index = 0;
        wrongIndex = 0;
        positive = 0;

        SpannableString s = new SpannableString("Main");
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#ff9d00")), 0, "Main".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        progressBar = (ProgressBar)findViewById(R.id.testProgress);
        questionView = (TextView)findViewById(R.id.questionView);
        exampleView = (TextView)findViewById(R.id.exampleView);
        radioGroup = (RadioGroup)findViewById(R.id.answerGroup);
        answerView1 = (RadioButton)findViewById(R.id.answerRadio1);
        answerView2 = (RadioButton)findViewById(R.id.answerRadio2);
        answerView3 = (RadioButton)findViewById(R.id.answerRadio3);
        answerView4 = (RadioButton)findViewById(R.id.answerRadio4);
        answerButton = (Button)findViewById(R.id.answerButton);

        seed = System.nanoTime();
        testPackageItemList = new ArrayList<>();
        wrongPackageItemList = new ArrayList<>();
        checkArray = new ArrayList<Integer>();
        answerArray = new ArrayList<Integer>();

        getDataFromFile();

        Collections.shuffle(testPackageItemList, new Random(seed));
        progressBar.setProgress(numberOfQuestion);
        questionView.setText(testPackageItemList.get(index).getQuestion());
        exampleView.setText(testPackageItemList.get(index).getExample().toString());
        answerView1.setText(testPackageItemList.get(index).getAnswer1().toString());
        answerView2.setText(testPackageItemList.get(index).getAnswer2().toString());
        answerView3.setText(testPackageItemList.get(index).getAnswer3().toString());
        answerView4.setText(testPackageItemList.get(index).getAnswer4().toString());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes final int checkedId) {
                View radioButton =  radioGroup.findViewById(checkedId);
                checkIndex = radioGroup.indexOfChild(radioButton) + 1;

                answerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numberOfQuestion++;
                        if(checkIndex == testPackageItemList.get(index).getCorrectAnswer()){
                            positive++;
                        }else{
                            wrongPackageItemList.add(testPackageItemList.get(index));
                            checkArray.add(checkIndex);
                            answerArray.add(testPackageItemList.get(index).getCorrectAnswer());
                            Log.i("hahah","what Number?"+testPackageItemList.get(index).getCorrectAnswer());
                        }
                        if(numberOfQuestion < 10){
                            index++;
                            progressBar.setProgress(numberOfQuestion);
                            questionView.setText(testPackageItemList.get(index).getQuestion());
                            exampleView.setText(testPackageItemList.get(index).getExample().toString());
                            answerView1.setText(testPackageItemList.get(index).getAnswer1().toString());
                            answerView2.setText(testPackageItemList.get(index).getAnswer2().toString());
                            answerView3.setText(testPackageItemList.get(index).getAnswer3().toString());
                            answerView4.setText(testPackageItemList.get(index).getAnswer4().toString());
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);

                            builder.setTitle("( " + positive + " / " + "10 )");
                            builder.setMessage("Do you want to see a review?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
                                    intent.putExtra("wrongAnswer", (Serializable) wrongPackageItemList);
                                    intent.putExtra("checkArray", checkArray);
                                    intent.putExtra("answerArray", answerArray);
                                    TestActivity.this.startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(TestActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();

                        }
                    }
                });

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TestActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void getDataFromFile(){

        BufferedReader reader = null;
        String[] splitStr = null;
        try{
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.one_sentence),"UTF-8"));
            String line = reader.readLine();

            while((line = reader.readLine())!=null && !line.equals(" ")){
                testPackageItem = new TestPackageItem();
                splitStr = null;
                splitStr = line.split("\t");

                for(int i=0; i < splitStr.length; i++){
                    splitStr[i] = splitStr[i].trim();
                }
                testPackageItem.setQuestion(splitStr[0]);
                testPackageItem.setExample(splitStr[1]);
                testPackageItem.setAnswer1(splitStr[2]);
                testPackageItem.setAnswer2(splitStr[3]);
                testPackageItem.setAnswer3(splitStr[4]);
                testPackageItem.setAnswer4(splitStr[5]);
                testPackageItem.setCorrectAnswer(Integer.parseInt(splitStr[6]));
                testPackageItemList.add(testPackageItem);

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
}