package com.example.stock4u.main.ui.economicOperations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EconomicOperationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EconomicOperationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}