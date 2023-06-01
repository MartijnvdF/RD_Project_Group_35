package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignUp extends AppCompatActivity {

    EditText username, password, confirmPassword, fullname, email, major, year;
    MaterialButton signUpBtn, cancelBtn, test;
    DataBaseActivity dataBaseActivity;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        confirmPassword = findViewById(R.id.confirm_password1);
        fullname = findViewById(R.id.name);
        email = findViewById(R.id.email);
        major = findViewById(R.id.major);
        year = findViewById(R.id.year);
        spinner = findViewById(R.id.year1);
        test = findViewById(R.id.testttt);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.list_item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        signUpBtn = findViewById(R.id.signupbtn);
        cancelBtn =  findViewById(R.id.cancelsignup);

        dataBaseActivity = new DataBaseActivity(this);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String y ="";
                if(!spinner.getSelectedItem().equals("Select year")) {
                    y = spinner.getSelectedItem().toString();
                }
                Toast.makeText(SignUp.this, "year: ", Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pw = password.getText().toString();
            String cpw = confirmPassword.getText().toString();
            String fn = fullname.getText().toString();
            String em = email.getText().toString();
            String mj = major.getText().toString();
            final String[] yr = new String[1];

            spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!adapterView.getItemAtPosition(i).equals("Select year")){
                        yr[0] = adapterView.getItemAtPosition(i).toString();
                    }
                }
            });



            if(user.equals("")||pw.equals("")||cpw.equals("")||fn.equals("")||em.equals("")||mj.equals("")|| yr[0].equals("")) {
                Toast.makeText(SignUp.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
            }else{
                if(pw.equals(cpw)){
                    Boolean checkUser = dataBaseActivity.checkUsername(user);
                    if(!checkUser){
                        Boolean insert = dataBaseActivity.insertUser(user, pw, fn, em, mj, yr[0]);
                        if(insert){
                            Toast.makeText(SignUp.this, "SIGN UP SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(SignUp.this,"SIGN UP FAILED",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUp.this, "Confirm password doesn't match password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
                finish();
            }
        });

    }
}