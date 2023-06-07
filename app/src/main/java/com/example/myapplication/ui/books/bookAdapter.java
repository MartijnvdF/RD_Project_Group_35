package com.example.myapplication.ui.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import java.util.ArrayList;

public class bookAdapter extends ArrayAdapter<Book> {
    public bookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_book, parent, false);
        }

        //Get all the corresponding text view fields
        TextView bookISBN = convertView.findViewById(R.id.textview_book_page_isbn);
        TextView bookAuthor = convertView.findViewById(R.id.textview_book_page_author);
        TextView bookTitle = convertView.findViewById(R.id.textview_book_page_title);
        TextView bookVersion = convertView.findViewById(R.id.textview_book_page_version);
        TextView bookYear = convertView.findViewById(R.id.textview_book_page_year);

        //Set the data for the text view fields
        bookISBN.setText("ISBN: " + book.get_isbn());
        bookAuthor.setText("Author: " + book.get_author());
        bookTitle.setText(book.get_title());
        bookVersion.setText("Version: " + book.get_version());
        bookYear.setText("Year: " + book.get_year());

        return convertView;
    }
}
