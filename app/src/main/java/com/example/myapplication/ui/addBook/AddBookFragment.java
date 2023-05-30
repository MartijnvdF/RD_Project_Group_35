package com.example.myapplication.ui.addBook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AddBookBinding;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;


public class AddBookFragment extends Fragment {
    private AddBookBinding binding;
    MaterialButton addBookButton, test;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = AddBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = NavHostFragment.findNavController(this);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String year_user = sharedPreferences.getString("year_user", "");
        boolean notification = sharedPreferences.getBoolean("Notification", false);

        isbn = root.findViewById(R.id.isbn);
        title = root.findViewById(R.id.title);
        author = root.findViewById(R.id.author);
        version = root.findViewById(R.id.version);
        year = root.findViewById(R.id.year);
        course = root.findViewById(R.id.course);

        addBookButton = (MaterialButton) root.findViewById(R.id.add_book_button);
        test = (MaterialButton) root.findViewById(R.id.test);

        dataBaseActivity = new DataBaseActivity(getContext());

        addBookButton.setOnClickListener(view -> {
            String isbn_str = isbn.getText().toString();
            String title_str = title.getText().toString();
            String author_str = author.getText().toString();
            String version_str = version.getText().toString();
            String year_str = year.getText().toString();
            String course_str = course.getText().toString();

            if (safeAddBook(isbn_str, title_str, author_str, version_str, year_str, course_str, dataBaseActivity, year_user)){
                dataBaseActivity.insertBook(isbn_str, author_str, title_str, version_str, year_str, course_str, year_user);

                navController.navigateUp();
            } else {
                Toast.makeText(getContext(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });

        test.setOnClickListener(view -> {
            if(notification) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "My Notification");
                builder.setContentTitle("Notification");
                builder.setContentText("New book has been added");
                builder.setSmallIcon(R.drawable.books);
                builder.setAutoCancel(true);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(1, builder.build());
            }
            navController.navigateUp();
        });

        return root;
    }

    public Boolean safeAddBook(String isbn, String title, String author, String version, String year, String course, DataBaseActivity dba, String year_user){
        return !dba.checkISBN(isbn, year_user) // check if the isbn does not already exist in the database
                && (title != "" && author != "" && version != "" && year != "" && course != "") // check if all strings except isbn are non-empty
                && Arrays.stream(courses).anyMatch(course::equals); // check if the given course is valid
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}