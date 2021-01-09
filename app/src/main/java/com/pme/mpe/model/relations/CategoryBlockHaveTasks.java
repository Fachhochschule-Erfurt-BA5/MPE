package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

public class CategoryBlockHaveTasks {
    @Embedded
    public CategoryBlock categoryBlock;

    @Relation(
            parentColumn = "id",
            entityColumn = "T_categoryBlockID"
    )
    public List<Task> tasks;

    public CategoryBlock merge()
    {
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).isTaskFixed())
            {
                this.categoryBlock.addTaskToFixedTasks(tasks.get(i));
            }
            else {
                this.categoryBlock.addTaskToSoftFixedTasks(tasks.get(i));
            }
        }

        return this.categoryBlock;
    }
}
