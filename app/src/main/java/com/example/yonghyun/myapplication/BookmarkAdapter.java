package com.example.yonghyun.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghyun on 2017. 8. 5..
 */

public class BookmarkAdapter extends ArrayAdapter<WordPackageItem> {

    final private BookmarkActivity activity;

    private List<WordPackageItem> bookmarkWord = new ArrayList<>();
    private DBWordHelper db;

    public BookmarkAdapter(BookmarkActivity context, int resource, List<WordPackageItem> objects){
        super(context.getActivity(), resource, objects);
        this.activity = context;
        this.bookmarkWord = objects;
    }

    @Override
    public WordPackageItem getItem(int position){
        return bookmarkWord.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        BookmarkAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)activity.getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        db = new DBWordHelper(this.getContext());

        if(convertView == null){
            convertView = inflater.inflate(R.layout.bookmark_item, parent, false);
            holder = new BookmarkAdapter.ViewHolder(convertView);
            convertView.setTag(holder);

        } else{
            holder = (BookmarkAdapter.ViewHolder)convertView.getTag();
        }

        WordPackageItem item = bookmarkWord.get(position);
        holder.name.setText(item.getKoreanWord());
        holder.btnEdit.setText(item.getEnglishWord());
        holder.btnDelete.setOnClickListener(onDeleteListener(position,holder));

        return convertView;
    }

    private View.OnClickListener onDeleteListener(final int position, final BookmarkAdapter.ViewHolder holder){
        return new View.OnClickListener(){
            @Override

            public void onClick(View view) {
                db.deleteWord(getItem(position));
                bookmarkWord.remove(position);
                holder.swipeLayout.close();
                activity.updateAdapter();
            }
        };
    }

    private class ViewHolder{
        private TextView name;
        private TextView btnEdit;
        private View btnDelete;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v){
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnDelete = v.findViewById(R.id.delete);
            btnEdit = (TextView)v.findViewById(R.id.editQuery);
            name = (TextView)v.findViewById(R.id.name);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
    }
}