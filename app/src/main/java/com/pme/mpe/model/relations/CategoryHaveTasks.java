package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

public class CategoryHaveTasks {
    @Embedded public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "taskCategoryId"
    )
    List<Task> tasks;
}
