package com.pme.mpe.ui.block;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.ArrayList;
import java.util.List;

public class BlockViewModel extends AndroidViewModel {
    private final TasksPackageRepository tasksPackageRepository;
    private final CategoryWithCatBlocksAndTasksRelation categoryWithCatBlocksAndTasksRelation;

    public BlockViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
        this.categoryWithCatBlocksAndTasksRelation = new CategoryWithCatBlocksAndTasksRelation();
    }

    public LiveData<List<CategoryBlock>> getBlocks() {
        return this.tasksPackageRepository.getCategoryBlocksLiveData();
    }


    public void deleteBlock (CategoryBlock categoryBlock)
    {
        this.tasksPackageRepository.deleteCategoryBlock(categoryBlock);
    }
}