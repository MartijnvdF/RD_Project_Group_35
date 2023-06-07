package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignUp extends AppCompatActivity {

    EditText username, password, confirmPassword, fullName, email, major;
    MaterialButton signUpBtn, cancelBtn;
    DataBaseActivity dataBaseActivity;
    Spinner year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.edittext_sign_up_username);
        password = findViewById(R.id.edittext_sign_up_password);
        confirmPassword = findViewById(R.id.edittext_sign_up_confirm_password);
        fullName = findViewById(R.id.edittext_sign_up_full_name);
        email = findViewById(R.id.edittext_sign_up_email);
        major = findViewById(R.id.edittext_sign_up_major);
        year = findViewById(R.id.edittext_sign_up_year);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.list_item_spinner_year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter);

        signUpBtn = findViewById(R.id.materialbutton_sign_up_signup);
        cancelBtn =  findViewById(R.id.materialbutton_sign_up_cancel);

        dataBaseActivity = new DataBaseActivity(this);

        signUpBtn.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pw = password.getText().toString();
            String cpw = confirmPassword.getText().toString();
            String fn = fullName.getText().toString();
            String em = email.getText().toString();
            String mj = major.getText().toString();
            String yr = year.getSelectedItem().toString();
            if(yr.equals("Select year")){
                yr = "";
            }

            if(user.equals("")||pw.equals("")||cpw.equals("")||fn.equals("")||em.equals("")||mj.equals("")|| yr.equals("")) {
                Toast.makeText(SignUp.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
            }else{
                if(pw.equals(cpw)){
                    Boolean checkUser = dataBaseActivity.checkUsername(user);
                    if(!checkUser){
                        Boolean insert = dataBaseActivity.insertUser(user, pw, fn, em, mj, yr);
                        if(insert){
                            Toast.makeText(SignUp.this, "SIGN UP SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, LogIn.class));
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

        cancelBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUp.this, LogIn.class));
            finish();
        });

    }
}