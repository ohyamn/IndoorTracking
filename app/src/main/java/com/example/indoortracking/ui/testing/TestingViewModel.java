package com.example.indoortracking.ui.testing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestingViewModel() {
        mText = new MutableLiveData<>();
    }

    public void setmText(String string) {
        mText.setValue(string);
    }

    public LiveData<String> getText() {
        return mText;
    }
}