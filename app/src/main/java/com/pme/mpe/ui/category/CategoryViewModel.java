package com.pme.mpe.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private final TasksPackageRepository tasksPackageRepository;
    private final CategoryWithCatBlocksAndTasksRelation categoryWithCatBlocksAndTasksRelation;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
        this.categoryWithCatBlocksAndTasksRelation = new CategoryWithCatBlocksAndTasksRelation();
    }

    public LiveData<List<Category>> getCategories() {
        return this.tasksPackageRepository.getCategoriesLiveData();
    }

    public void deleteCategory (Category category)
    {
        this.tasksPackageRepository.deleteCategory(category);
    }
}