package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    GridView gridView;
    String[] bookList = {"book1", "book2", "book3", "book4", "book5", "book6", "book7", "book8", "book9"};



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

        //show gridview using booklist
        gridView = (GridView) root.findViewById(R.id.customListView);
        gridView.setAdapter(new CustomBaseAdapter(getActivity(), bookList));

        //TO DO: redirect to another page using position
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("CUSTOM_GRID_VIEW", "Item is clicked at position " + i);
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