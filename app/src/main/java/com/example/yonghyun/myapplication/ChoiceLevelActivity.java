package com.example.yonghyun.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_level);

        Button easyButton = (Button)findViewById(R.id.levelChoice1); //if easybutton clicked flag is 0
        Button nomalButton = (Button)findViewById(R.id.levelChoice2);// if nomalbutton clicked flag is 1

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flagEasy = new Intent(ChoiceLevelActivity.this, DayListActivity.class);
                flagEasy.putExtra("flag", 0);
                ChoiceLevelActivity.this.startActivity(flagEasy);
            }
        });

        nomalButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent flagNomal = new Intent(ChoiceLevelActivity.this, DayListActivity.class);
                flagNomal.putExtra("flag",1);
                ChoiceLevelActivity.this.startActivity(flagNomal);
            }
        });
    }
}
