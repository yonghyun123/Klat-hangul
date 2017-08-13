package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChoiceLevelActivity extends Fragment{
    private View v;
    public ChoiceLevelActivity(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.activity_choice_level, container, false);
        final FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim);
        final Fragment DayListActivityFragment = new DayListActivity();
        Button easyButton = (Button)v.findViewById(R.id.levelChoice1); //if easybutton clicked level is 0
        Button nomalButton = (Button)v.findViewById(R.id.levelChoice2);// if nomalbutton clicked level is 1

        //case 1 : easy mode
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State_Field.setLevel(0);
                tr.replace(R.id.contentPanel, DayListActivityFragment);
                tr.addToBackStack(null);
                tr.commit();
            }
        });

        //case 2 : nomal mode
        nomalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                State_Field.setLevel(1);
                tr.replace(R.id.contentPanel, DayListActivityFragment);
                tr.addToBackStack(null);
                tr.commit();
            }
        });
        return v;
    }
}