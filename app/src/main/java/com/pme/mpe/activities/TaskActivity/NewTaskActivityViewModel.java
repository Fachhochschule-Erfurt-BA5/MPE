package com.pme.mpe.activities.TaskActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.storage.repository.exceptions.ObjectNotFoundException;

import java.time.LocalDate;

public class NewTaskActivityViewModel extends AndroidViewModel {

    private final TasksPackageRepository tasksPackageRepository;

    public NewTaskActivityViewModel(@NonNull Application application) {
        super(application);
        this.tasksPackageRepository = TasksPackageRepository.getRepository(application);
    }

    public void saveTasks (Task task)
    {
        this.tasksPackageRepository.insertTask(task);
    }

    public void updateTask (long taskID, String newName, String newDescription, int newDuration, LocalDate deadline) throws ObjectNotFoundException, TaskFixException, TimeException {
        this.tasksPackageRepository.updateTask(taskID, newName, newDescription, newDuration, deadline);
    }

    public Category nameToIDCategory(String CategoryName) {
        return this.tasksPackageRepository.getCategoryWithName(CategoryName);
    }
}
