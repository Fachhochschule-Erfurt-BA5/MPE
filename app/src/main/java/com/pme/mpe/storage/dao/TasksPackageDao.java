package com.pme.mpe.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.pme.mpe.model.relations.CategoryBlockHaveTasks;
import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.util.List;

/**
 * The interface Tasks package dao.
 * Manage all the access for the whole package
 */
@Dao
public interface TasksPackageDao {

    //Basic Functions
    @Insert
    long insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Insert
    long insertCategoryBlock (CategoryBlock categoryBlock);

    @Update
    void updateCategoryBlock (CategoryBlock categoryBlock);

    @Delete
    void deleteCategoryBlock (CategoryBlock categoryBlock);

    @Insert
    long insertTask (Task task);

    @Update
    void updateTask (Task task);

    @Delete
    void deleteTask (Task task);

    //Live Data Queries
    @Transaction
    @Query("SELECT * FROM Category")
    LiveData<List<CategoryWithCatBlocksAndTasksRelation>> getCategoriesWithCategoryBlocks();

    @Transaction
    @Query("SELECT * FROM CategoryBlock")
    LiveData<List<CategoryBlockHaveTasks>> getCategoryBlocksWhitTasks();
}
