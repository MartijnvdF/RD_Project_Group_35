package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    MaterialButton loginBtn, cancelBtn;
    EditText username,password;
    TextView attemptsLeft, attemptsLeftCounter;
    String dummyUsername = "admin";
    String dummyPassword = "admin";

    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        attemptsLeftCounter = findViewById(R.id.attempts_left_counter);
        attemptsLeft = findViewById(R.id.attempts_left);
        loginBtn = findViewById(R.id.loginbtn);
        cancelBtn = findViewById(R.id.cancel_button);
        attemptsLeft.setVisibility(View.GONE);
        attemptsLeftCounter.setVisibility(View.GONE);


        //admin and admin

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals(dummyUsername) && password.getText().toString().equals(dummyPassword)) {
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(i);
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
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}