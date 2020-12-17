package com.pme.mpe.storage.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.pme.mpe.model.relations.CategoryBlockHaveTasks;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;

import java.util.List;

@Dao
public interface CategoryBlockDao {

    @Insert
    void insert(CategoryBlock... categoryBlocks);

    @Update
    void update(CategoryBlock... categoryBlocks);

    @Delete
    void delete(CategoryBlock... categoryBlocks);

    @Query("DELETE FROM CategoryBlock")
    void deleteAll();

    @Query("SELECT count(*) FROM CategoryBlock")
    int count();

    @Query("SELECT * FROM CategoryBlock")
    List<CategoryBlock> getCategoryBlocks();

    @Query("SELECT * FROM CategoryBlock ORDER BY startTimeHour ASC")
    List<CategoryBlock> getCategoryBlocksSortByStartTimeHour();

    @Query("SELECT * FROM CategoryBlock ORDER BY id DESC LIMIT 1")
    CategoryBlock getLastCategoryBlockAdded();

    @Query("SELECT * FROM CategoryBlock WHERE endTimeHour < :search")
    List<CategoryBlock> getCategoryBlocksForDeadline(int search);


    // Query for Task CatBlock Relation
    @Transaction
    @Query("SELECT * FROM CategoryBlock")
    List<CategoryBlockHaveTasks> getTasksForCategoryBlock();
}
