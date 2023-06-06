package com.example.myapplication.ui.changePassword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ChangePasswordBinding;
import com.google.android.material.button.MaterialButton;

public class ChangePasswordFragment extends Fragment {
    private ChangePasswordBinding binding;
    MaterialButton changePassword;
    EditText password, cPassword;
    DataBaseActivity dataBaseActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ChangePasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("Username", "");

        changePassword = (MaterialButton) root.findViewById(R.id.change_password_button);

        password = (EditText) root.findViewById(R.id.change_password1);
        cPassword = (EditText) root.findViewById(R.id.change_confirm_password1);

        dataBaseActivity = new DataBaseActivity(getContext());

        NavController navController = NavHostFragment.findNavController(this);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uPassword = password.getText().toString();
                String uCPassword = cPassword.getText().toString();

                if(uPassword.equals("")||uCPassword.equals("")) {
                    Toast.makeText(getContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                }else{
                    if(uPassword.equals(uCPassword)){
                        dataBaseActivity.updateData(userName, "password", uPassword);
                        navController.navigateUp();
                    }else{
                        Toast.makeText(getContext(), "Confirm password doesn't match password", Toast.LENGTH_SHORT).show();
                    }
                }
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