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
import com.example.myapplication.databinding.FragmentChangePasswordBinding;
import com.google.android.material.button.MaterialButton;

public class ChangePasswordFragment extends Fragment {
    private FragmentChangePasswordBinding binding;
    MaterialButton changePassword;
    EditText password, confirmPassword;
    DataBaseActivity dataBaseActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");

        changePassword = (MaterialButton) root.findViewById(R.id.materialbutton_change_password);

        password = (EditText) root.findViewById(R.id.edittext_change_password);
        confirmPassword = (EditText) root.findViewById(R.id.edittext_change_password_confirm_password);

        dataBaseActivity = new DataBaseActivity(getContext());

        NavController navController = NavHostFragment.findNavController(this);

        changePassword.setOnClickListener(view -> {
            String updatedPassword = password.getText().toString();
            String updatedConfirmPassword = confirmPassword.getText().toString();

            if(updatedPassword.equals("")||updatedConfirmPassword.equals("")) {
                Toast.makeText(getContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
            }else{
                if(updatedPassword.equals(updatedConfirmPassword)){
                    dataBaseActivity.updateUserData(username, "password", updatedPassword);
                    navController.navigateUp();
                }else{
                    Toast.makeText(getContext(), "Confirm password doesn't match password", Toast.LENGTH_SHORT).show();
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