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

import java.time.LocalDate;
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

    //Id helpers
    @Query("SELECT categoryId from Category ORDER BY categoryId DESC LIMIT 1")
    long getLastCategoryId();

    //Live Data Queries
    @Transaction
    @Query("SELECT * FROM Category")
    LiveData<List<CategoryWithCatBlocksAndTasksRelation>> getCategoriesWithCategoryBlocks();

    @Transaction
    @Query("SELECT * FROM CategoryBlock")
    LiveData<List<CategoryBlockHaveTasks>> getCategoryBlocksWithTasks();

    //Get Category with given CatID
    @Query("SELECT * FROM Category WHERE categoryId = :catId")
    Category getCategoryWithID(long catId);

    //Get Category block with given Category Block ID
    @Query("SELECT * FROM CategoryBlock WHERE id = :categoryBlockID")
    CategoryBlock getCategoryBlockWithID(long categoryBlockID);

    //Get Task with given TaskID
    @Query("SELECT * FROM task WHERE id = :taskID")
    Task getTaskWithID(long taskID);

    //Get all the Category blocks from a given Category with category id
    @Query("SELECT * FROM CategoryBlock WHERE CB_CategoryId = :categoryID")
    List<CategoryBlock> getCategoryBlocksWithCategoryID(long categoryID);

    //Get all the Tasks from a given Category with category id
    @Query("SELECT * FROM Task WHERE T_categoryID = :categoryID")
    List<Task> getTasksWithCategoryID(long categoryID);

    //Get all the Fixed Tasks from a given Category Block with category block id
    @Query("SELECT * FROM Task WHERE T_categoryBlockID = :categoryBlockID")
    List<Task> getFixedTasksFromCB(long categoryBlockID);

    //Get Category with a given CatName
    @Query("SELECT * FROM Category WHERE categoryName = :catName")
    Category getCategoryWithName(String catName);

    @Query("SELECT * FROM CategoryBlock WHERE CB_CategoryId = :categoryID AND title = :blockName")
    CategoryBlock getCategoryBlockWithCategoryIDAndName(long categoryID, String blockName);

    //Get all the Category blocks
    @Query("SELECT * FROM CategoryBlock ")
    List<CategoryBlock> getCategoryBlocks();

    // Get CategoryBlocks by Date
    @Query("SELECT * FROM CategoryBlock WHERE date = :date")
    List<CategoryBlock> getCatBlocksByDay(LocalDate date);

    //Get All Tasks
    @Query("SELECT * FROM task")
    List<Task> getTasks();


}
