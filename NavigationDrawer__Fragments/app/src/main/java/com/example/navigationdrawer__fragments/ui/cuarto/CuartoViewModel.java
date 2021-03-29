package com.example.navigationdrawer__fragments.ui.cuarto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CuartoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CuartoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cuarto Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}