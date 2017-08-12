package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 8. 9..
 */

public class TestResultAdapter extends ArrayAdapter<TestPackageItem> {
    final private TestResultActivity activity;


    List<TestPackageItem> wrongAnswerList = new ArrayList<>();
    ArrayList<Integer> wrongIntegerList = new ArrayList<>();
    ArrayList<Integer> answerIntegerList = new ArrayList<>();


    public TestResultAdapter(TestResultActivity context, int resource, List<TestPackageItem> objects, ArrayList<Integer> array1, ArrayList<Integer> array2) {
        super(context, resource, objects);
        activity = context;
        this.wrongAnswerList=objects;
        wrongIntegerList = array1;
        answerIntegerList = array2;
    }
    @Override
    public TestPackageItem getItem(int position){
        return wrongAnswerList.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent){

        TestPackageItem item = wrongAnswerList.get(position);
        TestResultAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.test_result_item, parent, false);
            holder = new TestResultAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
            setAnswered(holder,item);

        } else{
            holder = (TestResultAdapter.ViewHolder)convertView.getTag();

        }
        int checkIdx = wrongIntegerList.get(position);

        holder.resultQuestionView.setText(item.getQuestion());
        holder.resultExampleView.setText(item.getExample());
        holder.answerView1.setText(item.getAnswer1());
        holder.answerView2.setText(item.getAnswer2());
        holder.answerView3.setText(item.getAnswer3());
        holder.answerView4.setText(item.getAnswer4());

//        Log.i("whatIs","what number"+answerIdx);

        setChecked(holder,checkIdx);

        return convertView;
    }

    private void setChecked(ViewHolder holder,int checkIdx){
        switch (checkIdx){
            case 1:
                holder.answerView1.setChecked(true);
                break;
            case 2:
                holder.answerView2.setChecked(true);
                break;
            case 3:
                holder.answerView3.setChecked(true);
                break;
            case 4:
                holder.answerView4.setChecked(true);
                break;
        }
    }

    private void setAnswered(ViewHolder holder, TestPackageItem answerItem){
        switch (answerItem.getCorrectAnswer()){
            case 1:
                holder.answerView1.setTextColor(Color.parseColor("#ff9d00"));
                break;
            case 2:
                holder.answerView2.setTextColor(Color.parseColor("#ff9d00"));
                break;
            case 3:
                holder.answerView3.setTextColor(Color.parseColor("#ff9d00"));
                break;
            case 4:
                holder.answerView4.setTextColor(Color.parseColor("#ff9d00"));
                break;
        }
    }

    private class ViewHolder{
        private LinearLayout linearLayout;
        private TextView resultQuestionView;
        private TextView resultExampleView;
        private RadioGroup radioGroup;
        private RadioButton answerView1;
        private RadioButton answerView2;
        private RadioButton answerView3;
        private RadioButton answerView4;

        public ViewHolder(View v){
            linearLayout = (LinearLayout) v.findViewById(R.id.testResultLinearLayout);
            resultQuestionView = v.findViewById(R.id.resultQuestion);
            resultExampleView = v.findViewById(R.id.resultExample);
            radioGroup = v.findViewById(R.id.resultAnswerGroup);
            answerView1 = v.findViewById(R.id.resultAnswerRadio1);
            answerView2 = v.findViewById(R.id.resultAnswerRadio2);
            answerView3 = v.findViewById(R.id.resultAnswerRadio3);
            answerView4 = v.findViewById(R.id.resultAnswerRadio4);
        }

    }
}
