package com.pme.mpe.ui.block;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BlockViewModel extends ViewModel {
    private MutableLiveData<List<String>> gBlockCategoryList;
    private MutableLiveData<List<String>> gTaskList;

    public BlockViewModel() {
        gBlockCategoryList = new MutableLiveData<>();
        gTaskList = new MutableLiveData<>();
        List<String> listBlock = new ArrayList<>();
        List<String> nListTask = new ArrayList<>();

        listBlock.add("Block 1");
        listBlock.add("Block 2");
        listBlock.add("Block 3");
        listBlock.add("+");
        nListTask.add("25 Tasks");
        nListTask.add("5 Tasks");
        nListTask.add("10 Tasks");
        gBlockCategoryList.setValue(listBlock);
        gTaskList.setValue(nListTask);
    }

    public LiveData<List<String>> getBlockCategory() {
        return gBlockCategoryList;
    }
    public LiveData<List<String>> getNTask() {
        return gTaskList;
    }
}