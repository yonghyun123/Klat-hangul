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
import java.util.Locale;

/**
 * Created by yonghyun on 2017. 7. 25..
 */

public class WordListViewAdapter extends ArrayAdapter<WordPackageItem> implements CompoundButton.OnCheckedChangeListener{
    final private WordListActivity activity;

    private List<WordPackageItem> koreanWord = new ArrayList<>();
    private List<WordPackageItem> searchWord = new ArrayList<>();
    private List<WordPackageItem> selectedWord = new ArrayList<>();
    private DBWordHelper db = new DBWordHelper(this.getContext());

    private boolean[] checkedStatus;

    public WordListViewAdapter(WordListActivity context, int resource, List<WordPackageItem> objects, boolean []checkedStatus) {
        super(context.getActivity(), resource, objects);
        this.activity = context;
        this.koreanWord = objects;
        this.checkedStatus = checkedStatus;
        this.searchWord.addAll(koreanWord);
    }
    @Override
    public WordPackageItem getItem(int position){
        return koreanWord.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity.getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if(convertView == null){
            convertView = inflater.inflate(R.layout.word_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        WordPackageItem item = koreanWord.get(position);
        holder.name.setText(item.getKoreanWord());
        holder.btnEdit.setText(item.getEnglishWord());
        holder.btnCheckbox.setTag(position);

        holder.btnCheckbox.setOnClickListener(onDeleteListener(position,holder));
//        holder.btnCheckbox.setOnCheckedChangeListener(this);

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

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        koreanWord.clear();

        if(charText.length() == 0){
            koreanWord.addAll(searchWord);
        }
        else{
            for(WordPackageItem item : searchWord){
                if(item.getKoreanWord().toLowerCase(Locale.getDefault()).contains(charText)){
                    koreanWord.add(item);
                }
            }
        }
        activity.updateAdapter();
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