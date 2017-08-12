package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 7. 25..
 */

public class WrongListViewAdapter extends ArrayAdapter<WordPackageItem> implements CompoundButton.OnCheckedChangeListener{
    final private ResultActivity activity;
    private List<WordPackageItem> WrongWord = new ArrayList<>();
    private List<WordPackageItem> selectedWord = new ArrayList<>();
    private DBWordHelper db = new DBWordHelper(this.getContext());

    private boolean[] checkedStatus;

    public WrongListViewAdapter(ResultActivity context, int resource, List<WordPackageItem> objects, boolean []checkedStatus) {
        super(context, resource, objects);
        this.activity = context;
        this.WrongWord = objects;
        this.checkedStatus = checkedStatus;
        this.selectedWord.addAll(WrongWord);
    }
    @Override
    public WordPackageItem getItem(int position){
        return WrongWord.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.word_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        WordPackageItem item = WrongWord.get(position);
        holder.name.setText(item.getKoreanWord());
        holder.btnEdit.setText(item.getEnglishWord());
        holder.btnCheckbox.setTag(position);

        holder.btnCheckbox.setOnClickListener(onDeleteListener(position,holder));
        holder.btnCheckbox.setOnCheckedChangeListener(this);

        holder.btnCheckbox.setChecked(item.getSelectedWord());
        return convertView;
    }

    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder){
        return new View.OnClickListener(){
            @Override

            public void onClick(View view) {
                if(db.selectedWord(getItem(position)) == false){
                    getItem(position).setSeletedWord(true);
                    db.insertWord(getItem(position));   //db item insert
                    holder.swipeLayout.close();
                    activity.updateAdapter();
                }
                else{
                    getItem(position).setSeletedWord(false);
                    db.deleteWord(getItem(position));   //db item delete
                    holder.swipeLayout.close();
                    activity.updateAdapter();
                }

                selectedWord = db.getAllWords();

                for(WordPackageItem p : selectedWord){
                    String log = "korean : "+p.getKoreanWord()+ "english : "+p.getEnglishWord();
                    Log.i("TagKim",log);
                }
            }
        };
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Integer index = (Integer)buttonView.getTag();
        checkedStatus[index] = isChecked;
        String key = index.toString();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,isChecked);
        editor.apply();
    }

    private class ViewHolder{
        private TextView name;
        private TextView btnEdit;
        private CheckBox btnCheckbox;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v){
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnCheckbox = (CheckBox)v.findViewById(R.id.selectWordCheckBox);
            btnEdit = (TextView)v.findViewById(R.id.editQuery);
            name = (TextView)v.findViewById(R.id.name);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
    }
}