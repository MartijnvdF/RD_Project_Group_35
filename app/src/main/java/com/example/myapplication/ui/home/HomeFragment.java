package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.books.BookPage;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ListView listView;
    List<String> booklist1 = new ArrayList<>();
    DataBaseActivity dataBaseActivity;
    FloatingActionButton floatingActionButton;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        dataBaseActivity = new DataBaseActivity(getContext());

        //get username from login page
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");
        String year_user = sharedPreferences.getString("year_user", "");

        //display fullname
        String username = "Welcome " + dataBaseActivity.getUserData(userName).get(2);

        //display username
        TextView textView = root.findViewById(R.id.custom_Msg);
        textView.setText(username);

        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);

        booklist1 = dataBaseActivity.getBooksData(year_user);

        NavController navController = NavHostFragment.findNavController(this);

        listView = (ListView) root.findViewById(R.id.customListView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.activity_custom_list_view, R.id.textView, booklist1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("course", booklist1.get(i));
                navController.navigate(R.id.nav_book, bundle);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_add_book);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}