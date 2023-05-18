package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.databinding.FragmentAccountBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class UpdateAccount extends AppCompatActivity {

    MaterialButton uupdatebtn, cancelupdatebtn;
    EditText fullname, studentNumber, major, year, email;
    DataBaseActivity dataBaseActivity;
    ArrayList<String> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_account);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");
        dataBaseActivity = new DataBaseActivity(this);
        user = dataBaseActivity.getUserData(userName);

        uupdatebtn = (MaterialButton) findViewById(R.id.uupdateaccountbtn);
        cancelupdatebtn = (MaterialButton) findViewById(R.id.cancelUpdate);

        fullname = findViewById(R.id.ufull_name);
        fullname.setHint(user.get(2));

        studentNumber = findViewById(R.id.ustudentNumber);
        if(!user.get(3).equals(""))
            studentNumber.setText(user.get(3));

        major = findViewById(R.id.uMajor);
        major.setHint(user.get(4));

        year = findViewById(R.id.uYear);
        year.setHint(user.get(5));

        email = findViewById(R.id.uEmail);
        email.setHint(user.get(6));

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

                Intent intent = new Intent(getApplicationContext(), FragmentAccountBinding.class);
                startActivity(intent);
                finish();
            }
        });



        cancelupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FragmentAccountBinding.class);
                startActivity(intent);
                finish();
            }
        });
    }
}