package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

public class CategoryBlockHaveTasks {
    @Embedded public CategoryBlock categoryBlock;
    @Relation(
            parentColumn = "catBlockId",
            entityColumn = "taskCatBlockId"
    )
    public List<Task> tasks;
}
