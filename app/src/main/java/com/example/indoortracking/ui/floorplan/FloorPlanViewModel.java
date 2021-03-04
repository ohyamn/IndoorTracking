package com.example.indoortracking.ui.floorplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FloorPlanViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public FloorPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Floor Plan Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}