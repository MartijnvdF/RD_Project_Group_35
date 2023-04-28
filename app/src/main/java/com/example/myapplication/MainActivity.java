package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MaterialButton loginBtn, cancelBtn, signUpBtn;
    EditText username,password, confirmPassword;
    TextView attemptsLeft, attemptsLeftCounter;
    String dummyUsername = "admin";
    String dummyPassword = "admin";
    DataBaseActivity dataBaseActivity;

    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        attemptsLeftCounter = findViewById(R.id.attempts_left_counter);
        attemptsLeft = findViewById(R.id.attempts_left);

        loginBtn = findViewById(R.id.loginbtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        attemptsLeft.setVisibility(View.GONE);
        attemptsLeftCounter.setVisibility(View.GONE);

        dataBaseActivity = new DataBaseActivity(this);




        //admin and admin

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pw = password.getText().toString();

                if(user.equals("")||pw.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = dataBaseActivity.checkusernamepassword(user, pw);
                    if (checkuserpass) {
                        Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                        intent.putExtra("Username", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        attemptsLeft.setVisibility(View.VISIBLE);
                        attemptsLeftCounter.setVisibility(View.VISIBLE);
                        counter--;
                        attemptsLeftCounter.setText(Integer.toString(counter));

                        if(counter == 0){
                            loginBtn.setEnabled(false);
                        }
                    }
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }
}