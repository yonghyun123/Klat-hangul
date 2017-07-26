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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yonghyun on 2017. 7. 25..
 */

public class WordListViewAdapter extends ArrayAdapter<String> {

    final private WordListActivity activity;
    private List<String> koreanWord;

    public WordListViewAdapter(WordListActivity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.koreanWord = objects;
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
        holder.name.setText(getItem(position));
        holder.btnEdit.setOnClickListener(onEditListener(position,holder));
        holder.btnDelete.setOnClickListener(onDeleteListener(position,holder));

        return convertView;
    }

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

    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                koreanWord.remove(position);
                holder.swipeLayout.close();
                activity.updateAdapter();

            }
        };
    }

    private class ViewHolder{
        private TextView name;
        private View btnDelete;
        private View btnEdit;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v){
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnDelete = v.findViewById(R.id.delete);
            btnEdit = v.findViewById(R.id.editQuery);
            name = (TextView)v.findViewById(R.id.name);


            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);


        }

    }

}