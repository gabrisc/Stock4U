package com.example.stock4u.main.ui.ec_mei;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EC_MEI_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EC_MEI_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}