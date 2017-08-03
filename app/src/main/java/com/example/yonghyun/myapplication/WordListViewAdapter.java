package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 7. 25..
 */

public class WordListViewAdapter extends ArrayAdapter<WordPackageItem> {

    final private WordListActivity activity;
    private List<WordPackageItem> koreanWord = new ArrayList<>();
    private List<WordPackageItem> selectedWord = new ArrayList<>();

    public WordListViewAdapter(WordListActivity context, int resource, List<WordPackageItem> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.koreanWord = objects;
    }
    @Override
    public WordPackageItem getItem(int position){
        return koreanWord.get(position);
    }
    List<WordPackageItem> getSelectedWord(){
        return this.selectedWord;
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

        WordPackageItem item = koreanWord.get(position);
        holder.name.setText(item.getKoreanWord());
        holder.btnEdit.setText(item.getEnglishWord());
//      holder.btnEdit.setOnClickListener(onEditListener(position,holder)); //edit Button temporary stopped
        holder.btnDelete.setOnClickListener(onDeleteListener(position,holder));

        return convertView;
    }

    /* editButtoon click listener temporary stopped
    private View.OnClickListener onEditListener(final int position, final ViewHolder holder){
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showEditDialog(position,holder);
                Log.i("tagKim","dialog");
            }
        };

    }
    private void showEditDialog(final int position, final ViewHolder holder){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("EDIT ELEMENT");


        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setTag(koreanWord.get(position));
        input.setLayoutParams(lp);
        alertDialogBuilder.setView(input);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                koreanWord.set(position, input.getText().toString().trim());
                                activity.updateAdapter();//여기 코드랑 다름
                                holder.swipeLayout.close();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

*/
    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(koreanWord.get(position).getSelectedWord() == false){
                    koreanWord.get(position).setSeletedWord(true);
                    selectedWord.add(koreanWord.get(position));
                    holder.swipeLayout.close();
                    activity.updateAdapter();
                }
                else{
                    koreanWord.get(position).setSeletedWord(false);
                    selectedWord.remove(koreanWord.get(position));
                    holder.swipeLayout.close();
                    activity.updateAdapter();
                }

                for(int i = 0; i < selectedWord.size(); i++){
                    Log.i("clicked--",selectedWord.get(i).getKoreanWord());
                }

            }
        };
    }

    private class ViewHolder{
        private TextView name;
        private TextView btnEdit;
        private CheckBox btnDelete;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v){
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnDelete = (CheckBox)v.findViewById(R.id.checkbox);
            btnEdit = (TextView)v.findViewById(R.id.editQuery);
            name = (TextView)v.findViewById(R.id.name);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        }

    }

}
