package com.pme.mpe.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final TasksPackageRepository tasksPackageRepository;

    public HomeViewModel (Application application)
    {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
    }

    public LiveData<List<CategoryBlock>> getCategoryBlocks() {
        return this.tasksPackageRepository.getCategoryBlocksLiveData();
    }

    public LiveData<List<Category>> getCategories() {
        return this.tasksPackageRepository.getCategoriesLiveData();
    }
}