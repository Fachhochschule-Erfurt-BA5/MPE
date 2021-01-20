package com.pme.mpe.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CategoryViewModel extends AndroidViewModel {

    private final TasksPackageRepository tasksPackageRepository;

    private MutableLiveData<List<String>> gCategoryList;
    private MutableLiveData<List<String>> gBlockList;

    public CategoryViewModel (@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
        gCategoryList = new MutableLiveData<>();
        gBlockList = new MutableLiveData<>();
        List<String> listCategory = new ArrayList<>();
        List<String> nListBlock = new ArrayList<>();

        listCategory.add("Study");
        listCategory.add("Fitness");
        listCategory.add("Cooking");
        listCategory.add("Work");
        listCategory.add("Travel");
        listCategory.add("+");
        nListBlock.add("25 Blocks");
        nListBlock.add("5 Blocks");
        nListBlock.add("10 Blocks");
        nListBlock.add("35 Blocks");
        nListBlock.add("15 Blocks");
        gCategoryList.setValue(listCategory);
        gBlockList.setValue(nListBlock);
    }

    public LiveData<List<String>> getCategory() {
        return gCategoryList;
    }
    public LiveData<List<String>> getBlock() {
        return gBlockList;
    }

    public LiveData<List<Category>> getCategories() {
        return this.tasksPackageRepository.getCategoriesLiveData();
    }
}