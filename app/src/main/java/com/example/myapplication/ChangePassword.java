package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentAccountBinding;
import com.google.android.material.button.MaterialButton;

public class ChangePassword extends AppCompatActivity {
    MaterialButton changePassword, cancel;
    EditText password, cPassword;
    DataBaseActivity dataBaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");

        changePassword = (MaterialButton) findViewById(R.id.change_password_button);
        cancel = (MaterialButton) findViewById(R.id.change_cancel_button1);

        password = (EditText) findViewById(R.id.change_password1);
        cPassword = (EditText) findViewById(R.id.change_confirm_password1);

        dataBaseActivity = new DataBaseActivity(this);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uPassword = password.getText().toString();
                String uCPassword = cPassword.getText().toString();

                if(uPassword.equals("")||uCPassword.equals("")) {
                    Toast.makeText(ChangePassword.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }else{
                    if(uPassword.equals(uCPassword)){
                        dataBaseActivity.updateData(userName, "password", uPassword);
                    }else{
                        Toast.makeText(ChangePassword.this, "Confirm password doesn't match password", Toast.LENGTH_SHORT).show();
                    }
                }

                /*
                NavController navController = Navigation.findNavController(ChangePassword.this, R.id.nav_host_fragment_content_main);
                navController.navigateUp();
                navController.navigate(R.id.nav_account);*/

                Intent intent = new Intent(getBaseContext(), FragmentAccountBinding.class);
                startActivity(intent);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FragmentAccountBinding.class);
                startActivity(intent);
                finish();
            }
        });

    }
}