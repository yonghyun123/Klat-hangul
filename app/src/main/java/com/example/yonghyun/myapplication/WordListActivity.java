package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WordListActivity extends Fragment {

    private ListView wordListView;
    private EditText searchWord;
    private WordListViewAdapter koreanWordAdapter;
    private WordPackageItem packageItems;
    private InputMethodManager imm;

    private ArrayList<WordPackageItem> packageItemsList;
    private List<WordPackageItem> dbItemList;

    private ArrayList<String> koreanWordList; //korean word

    private TextView totalWordNumber;
    private SwipeLayout swipeLayout;
    private DBWordHelper db;


    public WordListActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_word_list, container, false);
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        wordListView = (ListView) v.findViewById(R.id.koreanWord);
        searchWord = (EditText)v.findViewById(R.id.searchText);

        koreanWordList = new ArrayList<>();
        packageItemsList = new ArrayList<>();
        dbItemList = new ArrayList<>();



        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("status", Context.MODE_PRIVATE);

        getDataFromFile();
        Log.i("in wordListActivity","what is it = "+State_Field.getDate());


        boolean []checkedStatus = new boolean[packageItemsList.size()];
        for(int i = 0; i < checkedStatus.length; i++){

            checkedStatus[i] = sharedPreferences.getBoolean(Integer.toString(i), false);
        }

        setListViewAdapter(checkedStatus);


        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast toast = Toast.makeText(v.getContext(),"몇번째?"+position,Toast.LENGTH_SHORT);
                toast.show();

                Intent fullWord = new Intent(getActivity(), ShowFullWord.class);
                fullWord.putExtra("ItemList",packageItemsList);
                fullWord.putExtra("position",position);
                WordListActivity.this.startActivity(fullWord);
            }
        });
        wordListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                hideKeyboard();
                return false;
            }
        });
        return v;
    }

    private void getDataFromFile(){

        BufferedReader reader = null;
        String[] splitStr = null;
        db = new DBWordHelper(getActivity());
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
                packageItems.setSeletedWord(db.selectedWord(packageItems));
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

    private void setListViewAdapter(boolean[] checkedStatus){
        koreanWordAdapter = new WordListViewAdapter(this, R.layout.word_list_item, packageItemsList,checkedStatus);
        wordListView.setAdapter(koreanWordAdapter);

        searchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString().toLowerCase(Locale.getDefault());
                koreanWordAdapter.filter(filterText);
            }
        });

    }
    public void updateAdapter(){
        koreanWordAdapter.notifyDataSetChanged();
    }
    public void hideKeyboard() {imm.hideSoftInputFromWindow(searchWord.getWindowToken(),0);}
}