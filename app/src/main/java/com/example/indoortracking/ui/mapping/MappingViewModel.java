package com.example.indoortracking.ui.mapping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MappingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MappingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("hello");
    }

    public LiveData<String> getText() {
        return mText;
    }
}