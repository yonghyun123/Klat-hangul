package com.example.yonghyun.myapplication;

import java.io.Serializable;

/**
 * Created by yonghyun on 2017. 7. 27..
 */

public class WordPackageItem implements Serializable{
    private static final long serialVersionUID = 1L;
    private String koreanWord;
    private String partOfWord;
    private String englishWord;
    private String translateWord;

    public String getKoreanWord(){
        return koreanWord;
    }
    public String getEnglishWord(){
        return englishWord;
    }
    public String getPartOfWord(){
        return partOfWord;
    }
    public String getTranslateWord(){
        return translateWord;
    }

    public void setKoreanWord(String koreanWord){
        this.koreanWord = koreanWord;
    }
    public void setPartOfWord(String partOfWord){
        this.partOfWord = partOfWord;
    }
    public void setEnglishWord(String englishWord){
        this.englishWord = englishWord;
    }
    public void setTranslateWord(String translateWord){
        this.translateWord = translateWord;
    }

}
