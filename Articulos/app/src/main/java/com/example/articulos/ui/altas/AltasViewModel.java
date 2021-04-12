package com.example.articulos.ui.altas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AltasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AltasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is altas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}