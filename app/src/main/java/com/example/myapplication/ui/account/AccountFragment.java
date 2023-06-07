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
    MaterialButton updateBtn, changePassword, deleteAccount;
    ArrayList<String> userData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get username from login
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");

        updateBtn = root.findViewById(R.id.materialbutton_account_update);
        changePassword = root.findViewById(R.id.materialbutton_account_change_password);
        deleteAccount = root.findViewById(R.id.materialbutton_account_delete_account);

        dataBaseActivity = new DataBaseActivity(getContext());
        userData = dataBaseActivity.getUserData(username);

        fullName = root.findViewById(R.id.textview_account_full_name);
        studentNumber = root.findViewById(R.id.textview_account_student_number);
        major = root.findViewById(R.id.textview_account_major);
        year = root.findViewById(R.id.textview_account_year);
        email = root.findViewById(R.id.textview_account_email);

        fullName.setText("Full name: " + userData.get(2));
        studentNumber.setText("Student number: " + userData.get(3));
        major.setText("Major: " + userData.get(4));
        year.setText("Year: " + userData.get(5));
        email.setText("Email: " + userData.get(6));

        NavController navController = NavHostFragment.findNavController(this);

        changePassword.setOnClickListener(view -> navController.navigate(R.id.nav_change_password));

        updateBtn.setOnClickListener(view -> navController.navigate(R.id.nav_update_account));

        deleteAccount.setOnClickListener(view -> {
            dataBaseActivity.deleteUser(username);
            Intent intent = new Intent(getContext(), LogIn.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}