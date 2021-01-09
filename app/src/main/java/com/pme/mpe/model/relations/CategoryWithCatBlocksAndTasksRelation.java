package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

public class CategoryWithCatBlocksAndTasksRelation {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "CB_CategoryId"
    )
    public List<CategoryBlock> categoryBlocks;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "T_categoryID"
    )
    public List<Task> tasks;

    public Category merge()
    {
        this.category.setCategoryBlockList(this.categoryBlocks);
        this.category.setTaskList(this.tasks);

        return this.category;
    }
}
