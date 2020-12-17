package com.pme.mpe.storage.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.pme.mpe.model.relations.CatCatBlockRelation;
import com.pme.mpe.model.relations.CategoryHaveTasks;
import com.pme.mpe.model.tasks.Category;

import java.util.List;

public interface CategoryDao {

    @Insert
    void insert(Category... categories);

    @Update
    void update(Category... categories);

    @Delete
    void delete(Category... categories);

    @Query("DELETE FROM Category")
    void deleteAll();

    @Query("SELECT count(*) FROM Category")
    int count();

    @Query("SELECT * FROM Category")
    List<Category> getCategories();

    @Query("SELECT * FROM Category ORDER BY categoryName ASC")
    List<Category> getCategoriesSortByName();

    @Query("SELECT * FROM Category ORDER BY id DESC LIMIT 1")
    Category getLastCategoryAdded();

    @Query("SELECT * FROM Category WHERE id LIKE :search")
    List<Category> getCategoriesForId(long search);

    // Query for Relation between Task and category
    @Transaction
    @Query("SELECT * FROM Category")
    public List<CategoryHaveTasks> getTasksForCategory();


    // Query for Relation between CategoryBlock and Category
    @Transaction
    @Query("SELECT * FROM Category")
    public List<CatCatBlockRelation> getCategoryBlocksForCategory();
}
