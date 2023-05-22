package com.example.myapplication.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class book_page extends AppCompatActivity {

    ArrayList<Book> bookArray = new ArrayList<Book>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String selectedCourse = getIntent().getStringExtra("course"); //Use this to find books in database

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        ListView list_view = findViewById(R.id.list_book_page);
        bookAdapter adapter = new bookAdapter(this, bookArray);

        list_view.setAdapter(adapter);

        //TODO: Add all books to array when database works
        Book book_test = new Book("ISBN-978-0-134-67094-2", "Y. Daniel Liang","Introduction to Java Programming and Data Structures,Comprehensive Version", "twelfth edition","2021","Object Oriented Programming", "link_placeholder");
        bookArray.add(book_test);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}