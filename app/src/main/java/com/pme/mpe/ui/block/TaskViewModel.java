package com.pme.mpe.ui.block;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.storage.repository.TasksPackageRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TasksPackageRepository tasksPackageRepository;
    private final CategoryWithCatBlocksAndTasksRelation categoryWithCatBlocksAndTasksRelation;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
        this.categoryWithCatBlocksAndTasksRelation = new CategoryWithCatBlocksAndTasksRelation();
    }


   /* public LiveData<List<Task>> getTasks() {
        return this.tasksPackageRepository.getTasksLiveData();
    }*/ //TODO: brauchen wir noch eine getTaskLiveData() funktion, oder läuft das über die Blocks? bisher brauchen wir die Funktion nicht

    public void deleteTask (Task task)
    {
        this.tasksPackageRepository.deleteTask(task);
    }

    public List<Task> getTasks(long blockID) { return this.tasksPackageRepository.getTasks(); }

}
