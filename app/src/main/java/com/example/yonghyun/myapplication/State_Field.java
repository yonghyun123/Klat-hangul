package com.example.yonghyun.myapplication;

/**
 * Created by niceguy on 2017-07-27.
 */

public class State_Field {
    static private boolean state;       //qize or memorization
    static private int level;
    static private int date;

    static public void setState(boolean s){
        state = s;
    }
    static public boolean getState(){ return state; }

    static public void setLevel(int l) {
        level = l;
    }
    static public int getLevel() {return level;    }

    static public void setDate(int d){ date = d;}
    static public int getDate(){ return date; }

}