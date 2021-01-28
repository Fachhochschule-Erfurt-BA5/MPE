package com.pme.mpe.storage.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pme.mpe.model.relations.CategoryBlockHaveTasks;
import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.storage.dao.TasksPackageDao;
import com.pme.mpe.storage.database.ToDoDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TasksPackageRepository {
    public static final String LOG_TAG = "TasksPackageRepository";

    //Application application;

    private TasksPackageDao tasksPackageDao;

    private LiveData<List<Category>> allCategories;
    private LiveData<List<CategoryBlock>> allCategoryBlocks;

    private static TasksPackageRepository INSTANCE;

    //////////////////Singleton Implementation//////////////////
    public static TasksPackageRepository getRepository(Application application)
    {
        if(INSTANCE == null)
        {
            synchronized (TasksPackageRepository.class) {
                if(INSTANCE == null)
                {
                    INSTANCE = new TasksPackageRepository(application);
                }
            }
        }

        return INSTANCE;
    }

    //////////////////Constructor//////////////////
    public TasksPackageRepository(Application application)
    {
        ToDoDatabase db = ToDoDatabase.getDatabase(application);
        this.tasksPackageDao = db.tasksPackageDao();

        this.getCategoryBlocksLiveData();
        this.getCategoriesLiveData();
    }

    //////////////////Live Data Map//////////////////
    public LiveData<List<Category>> getCategoriesLiveData()
    {
        if(this.allCategories == null)
        {
            this.allCategories = Transformations.map(
                    this.queryLiveData(this.tasksPackageDao::getCategoriesWithCategoryBlocks),
                    input -> input
                            .stream()
                            .map(CategoryWithCatBlocksAndTasksRelation::merge)
                            .collect(Collectors.toList())
            );
        }
        return this.allCategories;
    }

    public LiveData<List<CategoryBlock>> getCategoryBlocksLiveData()
    {
        if(this.allCategoryBlocks == null)
        {
            this.allCategoryBlocks = Transformations.map(
                    this.queryLiveData(this.tasksPackageDao::getCategoryBlocksWhitTasks),
                    input -> input
                            .stream()
                            .map(CategoryBlockHaveTasks::merge)
                            .collect(Collectors.toList())
            );
        }
        return this.allCategoryBlocks;
    }

    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query )
    {
        try {
            return ToDoDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

    //////////////////Insert Transactions//////////////////
    public void insertCategory(Category category)
    {
        category.setCreated(LocalDate.now());
        category.setUpdated(category.getCreated());
        category.setVersion(1);

        //Add the default Category Block
        ToDoDatabase.execute( () -> tasksPackageDao.insertCategoryBlock(category.getCategoryBlockList().get(0)));

        ToDoDatabase.execute( () -> tasksPackageDao.insertCategory(category));
    }

    /*
        Please user the method category.addCategoryBlock to ensure security
        The method returns the just added CategoryBlock,
        which should be manually added whit the following function
    */
    public void insertCategoryBlock(CategoryBlock categoryBlock)
    {
        categoryBlock.setCreated(LocalDate.now());
        categoryBlock.setUpdated(categoryBlock.getCreated());
        categoryBlock.setVersion(1);

        ToDoDatabase.execute( () -> tasksPackageDao.insertCategoryBlock(categoryBlock));
    }

    /*
        Please user the method category.createAndAssignTaskToCategory to ensure security
        The method returns the just added Task,
        which should be manually added whit the following function
    */
    public void insertTask(Task task)
    {
        task.setCreated(LocalDate.now());
        task.setUpdated(task.getCreated());
        task.setVersion(1);

        ToDoDatabase.execute( () -> tasksPackageDao.insertTask(task));
    }



    //test to delete a category (Hamza Harti)
    public void deleteCategory(Category category)
    {
        ToDoDatabase.execute( () -> tasksPackageDao.deleteCategory(category));
    }

    //test to update a category (Hamza Harti)
    public void updateCategory(Category category)
    {
        ToDoDatabase.execute( () -> tasksPackageDao.updateCategory(category));
    }
}
