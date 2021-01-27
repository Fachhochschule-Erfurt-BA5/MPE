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


    public CategoryViewModel (@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);

    }

    public LiveData<List<Category>> getCategories() {
        return this.tasksPackageRepository.getCategoriesLiveData();
    }

    public void deleteCategory (Category category)
    {
        this.tasksPackageRepository.deleteCategory(category);
    }
}