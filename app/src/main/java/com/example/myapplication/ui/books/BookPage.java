package com.example.myapplication.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;

import java.util.ArrayList;


public class BookPage extends Fragment {
    ArrayList<Book> bookArray = new ArrayList<Book>();
    bookAdapter adapter;
    ListView listView;
    DataBaseActivity dataBaseActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_page, container, false);

        dataBaseActivity = new DataBaseActivity(getContext());

        listView = (ListView) view.findViewById(R.id.list_book_page);
        //Book book_test = new Book("ISBN-978-0-134-67094-2", "Y. Daniel Liang","Introduction to Java Programming and Data Structures,Comprehensive Version", "twelfth edition","2021","Object Oriented Programming", "link_placeholder");
        //bookArray.add(book_test);
        bookArray = dataBaseActivity.getBookInfo(getArguments().getString("course"));
        adapter = new bookAdapter(getContext(), bookArray);

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}