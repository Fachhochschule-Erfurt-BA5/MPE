package com.pme.mpe.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Categories will be shown here, once i know how it's done");
    }

    public LiveData<String> getText() {
        return mText;
    }
}