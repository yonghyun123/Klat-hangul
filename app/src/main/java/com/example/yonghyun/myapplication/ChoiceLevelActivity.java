package com.example.yonghyun.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChoiceLevelActivity extends Fragment {
    public ChoiceLevelActivity(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View v = inflater.inflate(R.layout.activity_choice_level, container, false);
        final FragmentTransaction tr = getFragmentManager().beginTransaction();
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