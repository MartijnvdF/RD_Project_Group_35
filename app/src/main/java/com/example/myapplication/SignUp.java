package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignUp extends AppCompatActivity {

    EditText username, password, confirmPassword;
    MaterialButton signUpBtn, cancelBtn;
    DataBaseActivity dataBaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        confirmPassword = findViewById(R.id.confirm_password1);

        signUpBtn = findViewById(R.id.signUpBtn1);
        cancelBtn =  findViewById(R.id.cancel_button1);

        dataBaseActivity = new DataBaseActivity(this);

        signUpBtn.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pw = password.getText().toString();
            String cpw = confirmPassword.getText().toString();

            if(user.equals("")||pw.equals("")||cpw.equals("")) {
                Toast.makeText(SignUp.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
            }else{
                if(pw.equals(cpw)){
                    Boolean checkUser = dataBaseActivity.checkUsername(user);
                    if(!checkUser){
                        Boolean insert = dataBaseActivity.insertUser(user, pw);
                        if(insert){
                            Toast.makeText(SignUp.this, "SIGN UP SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();;
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
                finish();
            }
        });

    }
}