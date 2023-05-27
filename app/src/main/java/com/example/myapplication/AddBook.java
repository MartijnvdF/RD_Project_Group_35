package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Arrays;

public class AddBook extends AppCompatActivity {

    MaterialButton addBookButton, cancelAddBookButton;

    DataBaseActivity dataBaseActivity;

    String courses[] = new String[] {
            "ms", "mathematical structures",
            "da", "data analysis",
            "security",
            "ip", "imperative programming",
            "imdb", "information modelling and databases", "information modeling and databases",
            "combinatorics",
            "mc", "matrix calculations",
            "la", "languages and automata",
            "processors",
            "re", "requirements engineering",
            "r&d project",
            "calculus and probability theory",
            "ai", "artificial intelligence",
            "oop", "object oriented programming",
            "logic and applications" };

    EditText isbn, title, author, version, year, course;

    public Boolean safeAddBook(String isbn, String title, String author, String version, String year, String course, DataBaseActivity dba){
        return !dba.checkISBN(isbn) // check if the isbn does not already exist in the database
                && (title != "" && author != "" && version != "" && year != "" && course != "") // check if all strings except isbn are non-empty
                && Arrays.stream(courses).anyMatch(course::equals); // check if the given course is valid
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        isbn = findViewById(R.id.isbn);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        version = findViewById(R.id.version);
        year = findViewById(R.id.year);
        course = findViewById(R.id.course);

        addBookButton = (MaterialButton) findViewById(R.id.add_book_button);
        cancelAddBookButton = (MaterialButton) findViewById(R.id.cancel_add_book);

        dataBaseActivity = new DataBaseActivity(this);

        addBookButton.setOnClickListener(view -> {
            String isbn_str = isbn.getText().toString();
            String title_str = title.getText().toString();
            String author_str = author.getText().toString();
            String version_str = version.getText().toString();
            String year_str = year.getText().toString();
            String course_str = course.getText().toString();

            if (safeAddBook(isbn_str, title_str, author_str, version_str, year_str, course_str, dataBaseActivity)){
                dataBaseActivity.insertBook(isbn_str, author_str, title_str, version_str, year_str, course_str);
            } else {
                Toast.makeText(AddBook.this, "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });

        cancelAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBook.this, MainActivity.class));
                finish();
            }
        });
    }
}
