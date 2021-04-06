package com.example.indoortracking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> name;
    private MutableLiveData<String> location;

    public void setNameData(String nameData) {
        name.setValue(nameData);

/*
        If you are calling setNameData from a background thread use:
        name.postValue(nameData);
*/
    }

    public MutableLiveData<String> getNameData() {
        if (name == null) {
            name = new MutableLiveData<>();
        }

        return name;
    }

    public void setLocation(String nameData){
        location.setValue(nameData);
    }

    public MutableLiveData<String> getLocation() {
        if (location == null){
            location = new MutableLiveData<>();
        }

        return location;
    }
}
