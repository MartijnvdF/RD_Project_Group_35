package com.example.myapplication.ui.updateAccount;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.example.myapplication.DataBaseActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.example.myapplication.databinding.UpdateAccountBinding;
import com.example.myapplication.ui.account.AccountFragment;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;


public class UpdateAccountFragment extends Fragment {

    private UpdateAccountBinding binding;

    MaterialButton uupdatebtn;
    EditText fullname, studentNumber, major, year, email;
    DataBaseActivity dataBaseActivity;
    ArrayList<String> user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = UpdateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");

        dataBaseActivity = new DataBaseActivity(getContext());
        user = dataBaseActivity.getUserData(userName);

        uupdatebtn = (MaterialButton) root.findViewById(R.id.uupdateaccountbtn);

        fullname = root.findViewById(R.id.ufull_name);
        fullname.setHint(user.get(2));

        studentNumber = root.findViewById(R.id.ustudentNumber);
        if(!user.get(3).equals(""))
            studentNumber.setHint(user.get(3));

        major = root.findViewById(R.id.uMajor);
        major.setHint(user.get(4));

        year = root.findViewById(R.id.uYear);
        year.setHint(user.get(5));

        email = root.findViewById(R.id.uEmail);
        email.setHint(user.get(6));

        NavController navController = NavHostFragment.findNavController(this);


        uupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = fullname.getText().toString();
                String uStudentNumber = studentNumber.getText().toString();
                String uMajor = major.getText().toString();
                String uYear = year.getText().toString();
                String uEmail = email.getText().toString();

                if(!uname.equals("")){
                    dataBaseActivity.updateData(userName, "fullname", uname);
                }
                if(!uStudentNumber.equals("")){
                    dataBaseActivity.updateData(userName, "studentnumber", uStudentNumber);
                }
                if(!uMajor.equals("")){
                    dataBaseActivity.updateData(userName, "major", uMajor);
                }
                if(!uYear.equals("")){
                    dataBaseActivity.updateData(userName, "year", uYear);
                }
                if(!uEmail.equals("")){
                    dataBaseActivity.updateData(userName, "email", uEmail);
                }

                navController.navigateUp();
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
