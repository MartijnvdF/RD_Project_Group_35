package com.example.myapplication.ui.addBook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddBookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddBookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
