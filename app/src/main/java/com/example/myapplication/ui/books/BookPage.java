package com.example.myapplication.ui.books;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;

import java.util.ArrayList;


public class BookPage extends Fragment {
    ArrayList<Book> bookArray = new ArrayList<>();
    bookAdapter adapter;
    ListView listView;
    DataBaseActivity dataBaseActivity;
    String year_user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_page, container, false);

        dataBaseActivity = new DataBaseActivity(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        year_user = sharedPreferences.getString("YEAR_USER", "");

        listView = view.findViewById(R.id.listview_book_page);
        bookArray = dataBaseActivity.getBookInfo(getArguments().getString("course"), year_user);
        adapter = new bookAdapter(getContext(), bookArray);

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}