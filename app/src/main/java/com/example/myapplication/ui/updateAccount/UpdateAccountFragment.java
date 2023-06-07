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
import com.example.myapplication.databinding.UpdateAccountBinding;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

public class UpdateAccountFragment extends Fragment {

    private UpdateAccountBinding binding;
    MaterialButton updateBtn;
    EditText fullName, studentNumber, major, email;
    Spinner year;
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

        updateBtn = (MaterialButton) root.findViewById(R.id.uupdateaccountbtn);

        fullName = root.findViewById(R.id.ufull_name);
        fullName.setHint(user.get(2));

        studentNumber = root.findViewById(R.id.ustudentNumber);
        if(!user.get(3).equals(""))
            studentNumber.setHint(user.get(3));

        major = root.findViewById(R.id.uMajor);
        major.setHint(user.get(4));

        year = root.findViewById(R.id.uYear);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.year, R.layout.list_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter);

        int pos = 0;
        switch (user.get(5)){
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
        year.setSelection(pos);

        email = root.findViewById(R.id.uEmail);
        email.setHint(user.get(6));

        NavController navController = NavHostFragment.findNavController(this);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = fullName.getText().toString();
                String uStudentNumber = studentNumber.getText().toString();
                String uMajor = major.getText().toString();
                String uYear = year.getSelectedItem().toString();
                if(uYear.equals("Select year")){
                    uYear = "";
                }
                String uEmail = email.getText().toString();

                if(!uname.equals("")){
                    dataBaseActivity.updateUserData(userName, "fullName", uname);
                }
                if(!uStudentNumber.equals("")){
                    dataBaseActivity.updateUserData(userName, "studentNumber", uStudentNumber);
                }
                if(!uMajor.equals("")){
                    dataBaseActivity.updateUserData(userName, "major", uMajor);
                }
                if(!uYear.equals("")){
                    dataBaseActivity.updateUserData(userName, "year", uYear);
                }
                if(!uEmail.equals("")){
                    dataBaseActivity.updateUserData(userName, "email", uEmail);
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
