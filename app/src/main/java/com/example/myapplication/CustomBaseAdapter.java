package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ui.home.HomeFragment;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<String> bookList;

    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, List<String> bookList){
        this.context = ctx;
        this.bookList = bookList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) view.findViewById(R.id.textView);
        txtView.setText(bookList.get(i));
        return view;
    }
}
