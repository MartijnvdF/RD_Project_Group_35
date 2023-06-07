package com.example.myapplication.ui.addBook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AddBookBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class AddBookFragment extends Fragment {
    private AddBookBinding binding;
    MaterialButton addBookButton, test;
    DataBaseActivity dataBaseActivity;
    EditText isbn, title, author, version, year;
    Spinner courseMenu;

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
        courseMenu = root.findViewById(R.id.course);
        dataBaseActivity = new DataBaseActivity(getContext());

        // Get all courses in an array
        ArrayList<String> courses = dataBaseActivity.getCourses("first");
        courses.add(0, "Select course");
        String[] courses1 = new String[courses.size()];
        courses1 = courses.toArray(courses1);

        // Link the courses to the menu
        ArrayAdapter ad = new ArrayAdapter(getContext(), R.layout.list_item_spinner_course, courses1);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseMenu.setAdapter(ad);

        // Set up the buttons
        addBookButton = (MaterialButton) root.findViewById(R.id.add_book_button);
        test = (MaterialButton) root.findViewById(R.id.test);

        addBookButton.setOnClickListener(view -> {
            String isbn_str = isbn.getText().toString();
            String title_str = title.getText().toString();
            String author_str = author.getText().toString();
            String version_str = version.getText().toString();
            String year_str = year.getText().toString();
            String course_str = courseMenu.getSelectedItem().toString();
            if(course_str.equals("Select course")){
                course_str = "";
            }

            if (safeAddBook(isbn_str, title_str, author_str, version_str, year_str, course_str, dataBaseActivity, year_user)){
                dataBaseActivity.insertBook(isbn_str, author_str, title_str, version_str, year_str, course_str, year_user);

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
            } else if(dataBaseActivity.checkISBN(isbn_str, title_str)){
                Toast.makeText(getContext(), "Book already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
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
        return !dba.checkISBN(isbn, title) // check if the combination of isbn and title does not already exist in the database
                && (title != "" && author != "" && version != "" && year != "" && course != ""); // check if all strings except isbn are non-empty
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}