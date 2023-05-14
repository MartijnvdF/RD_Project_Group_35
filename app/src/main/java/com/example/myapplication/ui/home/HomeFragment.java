package com.example.myapplication.ui.home;

import android.content.Context;
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

import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    GridView gridView;
    ListView listView;
    List<String> booklist1 = new ArrayList<>();
    DataBaseActivity dataBaseActivity;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        dataBaseActivity = new DataBaseActivity(getContext());

        //get username from login page
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");

        //display fullname
        String username = "Welcome " + dataBaseActivity.getUserData(userName).get(2);

        //display username
        TextView textView = root.findViewById(R.id.custom_Msg);
        textView.setText(username);

        //getCourses();
        booklist1 = dataBaseActivity.getBooksData();

        if(dataBaseActivity.isBooksEmpty())
            dataBaseActivity.fillBooksDatabase(getResources().openRawResource(R.raw.books));



        listView = (ListView) root.findViewById(R.id.customListView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.activity_custom_list_view, R.id.textView, booklist1));

        //TO DO: redirect to another page using position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: go to new page to fill in new book using database.insertBook
                //Intent intent = new Intent();
                //startActivity(intent);

                Log.i("CUSTOM_GRID_VIEW", "Item is clicked at position " + i);
            }
        });

        return root;
    }

    private void getCourses(){

        InputStream inputStream = getResources().openRawResource(R.raw.books);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        );
        String line = "";
        try {
            line = reader.readLine();
            while((line = reader.readLine())!=null){
                //split by ','
                String[] info = line.split(";");

                //read data
                booklist1.add(info[5]);
            }
        } catch (IOException e) {
            Log.wtf("HomeFragment", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}