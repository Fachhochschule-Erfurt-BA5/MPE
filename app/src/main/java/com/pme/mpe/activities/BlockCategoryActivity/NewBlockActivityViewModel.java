package com.pme.mpe.activities.BlockCategoryActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.storage.repository.exceptions.FixedTaskException;
import com.pme.mpe.storage.repository.exceptions.ObjectNotFoundException;

public class NewBlockActivityViewModel extends AndroidViewModel {
    private final TasksPackageRepository tasksPackageRepository;


    public NewBlockActivityViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
    }

    public void saveBlock (CategoryBlock categoryBlock)
    {
        this.tasksPackageRepository.insertCategoryBlock(categoryBlock);
    }

    public void updateBlock(long categoryBlockID, CategoryBlock newCategoryBlock) throws FixedTaskException, ObjectNotFoundException {
        this.tasksPackageRepository.updateCategoryBlock(categoryBlockID,newCategoryBlock);
    }
}
