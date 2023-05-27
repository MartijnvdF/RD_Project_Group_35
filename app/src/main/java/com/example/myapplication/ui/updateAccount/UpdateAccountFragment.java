package com.example.myapplication.ui.updateAccount;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DataBaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.google.android.material.button.MaterialButton;


public class UpdateAccountFragment extends Fragment {

    private UpdateAccountFragment binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.update_account, container, false);





        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
