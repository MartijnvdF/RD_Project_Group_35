package com.example.myapplication.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.LogIn;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    DataBaseActivity dataBaseActivity;
    TextView fullName, studentNumber, major, year, email;
    MaterialButton updateBtn, changePW, deleteAccount;
    ArrayList<String> userData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get username from login
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");

        updateBtn = (MaterialButton) root.findViewById(R.id.updateaccountbtn);
        changePW = (MaterialButton) root.findViewById(R.id.change_password);
        deleteAccount = (MaterialButton) root.findViewById(R.id.delete_account);

        dataBaseActivity = new DataBaseActivity(getContext());
        fullName = (TextView) root.findViewById(R.id.full_name);
        studentNumber = (TextView) root.findViewById(R.id.studentNumber);
        major = (TextView) root.findViewById(R.id.major);
        year = (TextView) root.findViewById(R.id.year);
        email = (TextView) root.findViewById(R.id.email);

        userData = dataBaseActivity.getUserData(userName);

        fullName.setText("Full name: " + userData.get(2));
        studentNumber.setText("Student number: " + userData.get(3));
        major.setText("Major: " + userData.get(4));
        year.setText("Year: " + userData.get(5));
        email.setText("Email: " + userData.get(6));

        NavController navController = NavHostFragment.findNavController(this);

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_change_password);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_update_account);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseActivity.deleteUser(userName);
                Intent intent = new Intent(getContext(), LogIn.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}