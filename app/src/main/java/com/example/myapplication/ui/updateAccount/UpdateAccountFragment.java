package com.example.myapplication.ui.updateAccount;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentUpdateAccountBinding;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

public class UpdateAccountFragment extends Fragment {

    private FragmentUpdateAccountBinding binding;
    MaterialButton updateBtn;
    EditText fullName, studentNumber, major, email;
    Spinner year;
    DataBaseActivity dataBaseActivity;
    ArrayList<String> user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");

        dataBaseActivity = new DataBaseActivity(getContext());
        user = dataBaseActivity.getUserData(username);

        updateBtn = root.findViewById(R.id.materialbutton_update_account);

        fullName = root.findViewById(R.id.edittext_update_account_full_name);
        fullName.setHint(user.get(2));

        studentNumber = root.findViewById(R.id.edittext_update_account_student_number);
        if(!user.get(3).equals(""))
            studentNumber.setHint(user.get(3));

        major = root.findViewById(R.id.edittext_update_account_major);
        major.setHint(user.get(4));

        year = root.findViewById(R.id.spinner_update_account_year);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.year, R.layout.list_item_spinner_year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter);

        setYearSelection(year, user);

        email = root.findViewById(R.id.edittext_update_account_email);
        email.setHint(user.get(6));

        NavController navController = NavHostFragment.findNavController(this);

        updateBtn.setOnClickListener(view -> {

            String uName = fullName.getText().toString();
            String uStudentNumber = studentNumber.getText().toString();
            String uMajor = major.getText().toString();
            String uYear = year.getSelectedItem().toString();
            if(uYear.equals("Select year")){
                uYear = "";
            }
            String uEmail = email.getText().toString();

            if(!uName.equals("")){
                dataBaseActivity.updateUserData(username, "full_name", uName);
            }
            if(!uStudentNumber.equals("")){
                dataBaseActivity.updateUserData(username, "student_number", uStudentNumber);
            }
            if(!uMajor.equals("")){
                dataBaseActivity.updateUserData(username, "major", uMajor);
            }
            if(!uYear.equals("")){
                dataBaseActivity.updateUserData(username, "year", uYear);
            }
            if(!uEmail.equals("")){
                dataBaseActivity.updateUserData(username, "email", uEmail);
            }

            navController.navigateUp();
        });

        return root;
    }

    public void setYearSelection(Spinner spinner, ArrayList<String> list){
        int pos = 0;
        switch (list.get(5)){
            case "first":
                pos=1;
                break;
            case "second":
                pos=2;
                break;
            case "third":
                pos=3;
                break;
        }
        spinner.setSelection(pos);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
