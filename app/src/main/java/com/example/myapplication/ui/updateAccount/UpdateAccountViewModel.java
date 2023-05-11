package com.example.myapplication.ui.updateAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateAccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UpdateAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Account fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
