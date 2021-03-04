package com.example.indoortracking.ui.floorplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FloorPlanViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public void MappingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("hello");
    }

    public LiveData<String> getText() {
        return mText;
    }
}