package com.example.myapplication.ui.account;

import android.content.ContentValues;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ChangePassword;
import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.UpdateAccount;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.example.myapplication.ui.updateAccount.UpdateAccountFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    DataBaseActivity dataBaseActivity;
    TextView fullname, studenNumber, major, year, email;
    MaterialButton updatebtn, changePW, deleteAccount;
    ArrayList<String> userData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get username from login
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");


        updatebtn = (MaterialButton) root.findViewById(R.id.updateaccountbtn);
        changePW = (MaterialButton) root.findViewById(R.id.change_password);
        deleteAccount = (MaterialButton) root.findViewById(R.id.delete_account);

        dataBaseActivity = new DataBaseActivity(getContext());
        fullname = (TextView) root.findViewById(R.id.full_name);
        studenNumber = (TextView) root.findViewById(R.id.studentNumber);
        major = (TextView) root.findViewById(R.id.major);
        year = (TextView) root.findViewById(R.id.year);
        email = (TextView) root.findViewById(R.id.email);

        userData = dataBaseActivity.getData(userName);

        fullname.setText("Full name: " + userData.get(2));
        studenNumber.setText("Student number: " + userData.get(3));
        major.setText("Major: " + userData.get(4));
        year.setText("Year: " + userData.get(5));
        email.setText("Email: " + userData.get(6));

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateAccount.class);
                startActivity(intent);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseActivity.deleteUser(userName);
                Intent intent = new Intent(getContext(), MainActivity.class);
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