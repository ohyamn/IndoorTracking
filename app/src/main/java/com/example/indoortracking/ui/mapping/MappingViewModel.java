package com.example.indoortracking.ui.mapping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.indoortracking.MainActivity;

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