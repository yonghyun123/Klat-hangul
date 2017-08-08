package com.example.yonghyun.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 8. 3..
 */

public class DBWordHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    //db name
    private static final String DATABASE_NAME = "wordsManager";
    //words table name
    private static final String TABLE_NAME = "words";
    //Words table column names
    private static final String KEY_KOREAN_WORD = "koreanWord";
    private static final String KEY_PART_OF_WORD = "partOfWord";
    private static final String KEY_ENGLISH_WORD = "englishWord";
    private static final String KEY_TRANSLATE_WORD = "translateWord";

    public DBWordHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WORD_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_KOREAN_WORD + " TEXT PRIMARY KEY," +
                KEY_PART_OF_WORD + " TEXT," +
                KEY_ENGLISH_WORD + " TEXT," +
                KEY_TRANSLATE_WORD + " TEXT" + ")";
        //create table
        sqLiteDatabase.execSQL(CREATE_WORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertWord(WordPackageItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_KOREAN_WORD, item.getKoreanWord());
        values.put(KEY_PART_OF_WORD, item.getPartOfWord());
        values.put(KEY_ENGLISH_WORD, item.getEnglishWord());
        values.put(KEY_TRANSLATE_WORD, item.getTranslateWord());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<WordPackageItem> getAllWords(){
        List<WordPackageItem> packageItemList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                WordPackageItem packageItem = new WordPackageItem();
                packageItem.setKoreanWord(cursor.getString(0));
                packageItem.setPartOfWord(cursor.getString(1));
                packageItem.setEnglishWord(cursor.getString(2));
                packageItem.setTranslateWord(cursor.getString(3));

                packageItemList.add(packageItem);

            }while(cursor.moveToNext());
        }
        return packageItemList;
    }

    public void deleteWord(WordPackageItem packageItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_KOREAN_WORD + " =? ",
                new String[] {String.valueOf(packageItem.getKoreanWord())});

        db.close();
    }

    public boolean selectedWord(WordPackageItem packageItem){
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                KEY_KOREAN_WORD + " = '" + packageItem.getKoreanWord() + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                return true;
            }while(cursor.moveToNext());
        }else{
            return false;
        }

    }

    public int getWordCount(){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
