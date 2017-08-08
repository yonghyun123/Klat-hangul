package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class QuizActivity extends Fragment {
    private ArrayList<WordPackageItem> packageItemsList;
    private WordPackageItem packageItems;
    private ArrayList<String> wrongItemsList;
    InputMethodManager imm;
    int index = 0;
    static int positive = 0;
    int progress = 1;

    public QuizActivity() {
        // Required empty public constructor
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_quiz, container, false);
        final TextView txt = (TextView)v.findViewById(R.id.quizText);
        final TextView status = (TextView)v.findViewById(R.id.statusText);
        final Button btn = (Button)v.findViewById(R.id.submitButton);
        final EditText edit = (EditText)v.findViewById(R.id.editText);
        final ProgressBar progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        final RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.quizscreen);
        long seed = System.nanoTime();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        packageItemsList = new ArrayList<>();
        wrongItemsList = new ArrayList<>();

        getDataFromFile();
        Collections.shuffle(packageItemsList, new Random(seed));
        txt.setText(packageItemsList.get(index).getKoreanWord().toString());
        status.setText(progress+"/20");
        View.OnClickListener myClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                switch (v.getId())
                {
                    case R.id.quizText :
                        break;
                    case R.id.statusText:
                        break;
                    case R.id.submitButton:
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
        btn.setOnClickListener(myClickListener);
        progressBar.setOnClickListener(myClickListener);
        layout.setOnClickListener(myClickListener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct;
                progress++;
                String answer = edit.getText().toString();
                edit.setText(null);
                correct = is_correct(answer);
                if(correct == true){
                    Toast.makeText(view.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    positive++;
                }
                else{
                    Toast.makeText(view.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    wrongItemsList.add(packageItemsList.get(index).getKoreanWord().toString());
                    wrongItemsList.add(packageItemsList.get(index).getEnglishWord().toString());
                }
                if(progress <= 20){
                    progressBar.setProgress(progress);
                    status.setText(progress+"/20");
                    index++;
                    txt.setText(packageItemsList.get(index).getKoreanWord().toString());
                }
                else{
                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putStringArrayListExtra("list",wrongItemsList);
                    startActivity(intent);
                }
            }
        });
        return v;
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