package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ListView listView;
    List<String> bookList = new ArrayList<>();
    DataBaseActivity dataBaseActivity;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        dataBaseActivity = new DataBaseActivity(getContext());

        //get username from login page
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");
        String year_user = sharedPreferences.getString("year_user", "");

        //display fullName
        String username = "Welcome " + dataBaseActivity.getUserData(userName).get(2);

        //display username
        TextView textView = root.findViewById(R.id.custom_Msg);
        textView.setText(username);

        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);

        bookList = dataBaseActivity.getCourses(year_user);

        NavController navController = NavHostFragment.findNavController(this);

        listView = (ListView) root.findViewById(R.id.customListView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.activity_custom_list_view, R.id.textView, bookList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("course", bookList.get(i));
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