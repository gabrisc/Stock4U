package com.example.stock4u.main.ui.clients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ClientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is teste fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}