package com.pme.mpe.activities.CategoryActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.storage.repository.TasksPackageRepository;

public class NewCategoryActivityViewModel extends AndroidViewModel {

    private final TasksPackageRepository tasksPackageRepository;

    public NewCategoryActivityViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
    }

    public void saveCategory (Category category)
    {
        this.tasksPackageRepository.insertCategory(category);
    }
}
