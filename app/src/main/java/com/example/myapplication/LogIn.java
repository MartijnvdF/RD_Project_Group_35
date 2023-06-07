package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LogIn extends AppCompatActivity {
    MaterialButton loginBtn, signUpBtn;
    EditText username,password;
    TextView attemptsLeft, attemptsLeftCounter;
    DataBaseActivity dataBaseActivity;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edittext_login_username);
        password = findViewById(R.id.edittext_login_password);
        attemptsLeftCounter = findViewById(R.id.textview_login_attempts_left_counter);
        attemptsLeft = findViewById(R.id.textview_login_attempts_left);

        loginBtn = findViewById(R.id.materialbutton_login_sign_in);
        signUpBtn = findViewById(R.id.materialbutton_login_sign_up);

        attemptsLeft.setVisibility(View.GONE);
        attemptsLeftCounter.setVisibility(View.GONE);

        dataBaseActivity = new DataBaseActivity(this);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        boolean darkMode = sharedPreferences.getBoolean("dark", false);
        if(darkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        loginBtn.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pw = password.getText().toString();

            if(user.equals("")||pw.equals("")) {
                Toast.makeText(LogIn.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
            } else {
                boolean checkUserPassword = dataBaseActivity.checkUsernamePassword(user, pw);
                if (checkUserPassword) {
                    Toast.makeText(LogIn.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    String yearUser = dataBaseActivity.getYearUser(user);

                    //put username into sharedpref for other files to use
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("yearUser", yearUser);
                    editor.putString("username", user);
                    editor.apply();

                    //fill database with data from file
                    if(dataBaseActivity.isBooksEmpty(yearUser))
                        dataBaseActivity.fillBooksDatabase(getResources().openRawResource(R.raw.books), yearUser);

                    //redirect to main app
                    Intent intent = new Intent(LogIn.this, MainActivity3.class);
                    intent.putExtra("Username", user);
                    startActivity(intent);
                    finish();
                } else {

                    //increase counter
                    Toast.makeText(LogIn.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    attemptsLeft.setVisibility(View.VISIBLE);
                    attemptsLeftCounter.setVisibility(View.VISIBLE);
                    counter--;
                    attemptsLeftCounter.setText(Integer.toString(counter));

                    //if too many attempts failed, then login isn't available anymore
                    if(counter == 0){
                        loginBtn.setEnabled(false);
                    }
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUp.class));
                finish();
            }
        });
    }
}