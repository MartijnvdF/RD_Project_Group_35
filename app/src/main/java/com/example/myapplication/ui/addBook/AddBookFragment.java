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
import com.example.myapplication.databinding.FragmentAddBookBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;


public class AddBookFragment extends Fragment {
    private FragmentAddBookBinding binding;
    MaterialButton addBookButton, test;
    DataBaseActivity dataBaseActivity;
    EditText isbn, title, author, version, year;
    Spinner courseMenu;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = NavHostFragment.findNavController(this);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String yearUser = sharedPreferences.getString("YEAR_USER", "");
        boolean notification = sharedPreferences.getBoolean("NOTIFICATION", false);

        isbn = root.findViewById(R.id.edittext_add_book_isbn);
        title = root.findViewById(R.id.edittext_add_book_title);
        author = root.findViewById(R.id.edittext_add_book_author);
        version = root.findViewById(R.id.edittext_add_book_version);
        year = root.findViewById(R.id.edittext_add_book_year);
        courseMenu = root.findViewById(R.id.spinner_add_book_course);
        dataBaseActivity = new DataBaseActivity(getContext());

        // Get all courses in an array
        ArrayList<String> tempCourses = dataBaseActivity.getCourses("first");
        tempCourses.add(0, "Select course");
        String[] courses = new String[tempCourses.size()];
        courses = tempCourses.toArray(courses);

        // Link the courses to the menu
        ArrayAdapter ad = new ArrayAdapter(getContext(), R.layout.list_item_spinner_course, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseMenu.setAdapter(ad);

        // Set up the buttons
        addBookButton = root.findViewById(R.id.materialbutton_add_book);
        test = root.findViewById(R.id.test);

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

            if (safeAddBook(isbn_str, title_str, author_str, version_str, year_str, course_str, dataBaseActivity, yearUser)){
                dataBaseActivity.insertBook(isbn_str, author_str, title_str, version_str, year_str, course_str, yearUser);

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
            } else if(dataBaseActivity.checkISBN(isbn_str, title_str, yearUser)){
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

    public Boolean safeAddBook(String isbn, String title, String author, String version, String year, String course, DataBaseActivity dataBaseActivity, String yearUser){
        return !dataBaseActivity.checkISBN(isbn, title, yearUser) // check if the combination of isbn and title does not already exist in the database
                && (!Objects.equals(title, "") && !Objects.equals(author, "") && !Objects.equals(version, "") && !Objects.equals(year, "") && !Objects.equals(course, "")); // check if all strings except isbn are non-empty
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}