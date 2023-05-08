package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.CustomBaseAdapter;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    GridView gridView;
    String[] bookList = {"book1", "book2", "book3", "book4", "book5", "book6", "book7", "book8", "book9"};
    List<String> booklist1 = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        //get username from login page
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = "Welcome " + sharedPreferences.getString("Username", "");

        //display username
        TextView textView = root.findViewById(R.id.custom_Msg);
        textView.setText(username);

        readBooks();

        //show gridview using booklist
        gridView = (GridView) root.findViewById(R.id.customListView);

        //using arrayAdapter for easy use
        gridView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.activity_custom_list_view, R.id.textView, booklist1));

        //using customAdapter for more complex views
        //gridView.setAdapter(new CustomBaseAdapter(getActivity(), booklist1));

        //TO DO: redirect to another page using position
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("CUSTOM_GRID_VIEW", "Item is clicked at position " + i);
            }
        });

        return root;
    }

    private void readBooks(){

        InputStream inputStream = getResources().openRawResource(R.raw.dummy);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        );
        String line = "";
        try {
            while((line = reader.readLine())!=null){
                //split by ','
                String[] info = line.split(",");

                //read data
                booklist1.add(info[4]);
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