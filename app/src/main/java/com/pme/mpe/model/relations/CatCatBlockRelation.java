package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;

import java.util.List;

public class CatCatBlockRelation {
    @Embedded public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "catCatBlockId"
    )
    public List<CategoryBlock> categoryBlocks;
}
