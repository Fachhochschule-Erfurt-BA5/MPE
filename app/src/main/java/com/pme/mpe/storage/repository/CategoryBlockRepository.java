package com.pme.mpe.storage.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pme.mpe.model.relations.CategoryBlockHaveTasks;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.dao.CategoryBlockDao;
import com.pme.mpe.storage.database.ToDoDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CategoryBlockRepository {
    /*
    public static final String LOG_TAG = "CategoryBlockRepository";


    Application application;
    private CategoryBlockDao categoryBlockDao;

    private LiveData<List<CategoryBlock>> allCategoryBlocks;

    private static CategoryBlockRepository INSTANCE;

    //Constructor
    public static CategoryBlockRepository getRepository(Application application)
    {
        if(INSTANCE == null)
        {
            synchronized (CategoryBlockRepository.class) {
                if(INSTANCE == null)
                {
                    INSTANCE = new CategoryBlockRepository(application);
                }
            }
        }

        return INSTANCE;
    }

    private CategoryBlockRepository(Context context)
    {
        ToDoDatabase db = ToDoDatabase.getDatabase(context);
        this.categoryBlockDao = db.categoryBlockDao();

        this.getCategoryBlocksLiveData();
    }


    public LiveData<List<CategoryBlock>> getCategoryBlocksLiveData()
    {
        if(this.allCategoryBlocks == null)
        {
            this.allCategoryBlocks = Transformations.map(
                    this.queryLiveData(this.categoryBlockDao::getCategoryBlocksWhitAddresses),
                    input -> input
                            .stream()
                            .map(CategoryBlockHaveTasks::merge)
                            .collect(Collectors.toList())
            );
        }
        return this.allCategoryBlocks;
    }

    public long insertAndWait(CategoryBlock categoryBlock) {
        try {
            return ToDoDatabase.executeWithReturn( () -> CategoryBlockDao.insertContactWithAddresses( contact ) );
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return -1;
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


    //Basic Query
    private List<CategoryBlock> query( Callable<List<CategoryBlock>> query )
    {
        try {
            return ToDoDatabase.query( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //If elements not found, return an empty list
        return new ArrayList<>();
    }

    //Fetch all Category Blocks
    public List<CategoryBlock> getCategoryBlocks()
    {
        return this.query( () ->
                this.categoryBlockDao.getCategoryBlocks());
    }

    //Fetch all Category Blocks from a given Category whit Category ID
    public List<CategoryBlock> getCategoryBlocksFromACategory(Long catId)
    {
        return this.query( () ->
                this.categoryBlockDao.getCategoryBlocksFromACategory(catId));
    }

    //Fetch all Category Blocks whit a given date
    public List<CategoryBlock> getCategoryBlocksInAGivenDayAndOrderByStartHour(LocalDate date)
    {
        return this.query( () ->
                this.categoryBlockDao.getCategoryBlocksInAGivenDayAndOrderByStartHour(date));
    }

    //Fetch all Category Blocks whit a given date and a give Category whit Category ID
    public List<CategoryBlock> getCategoryBlockFromDayAndCategoryAndOrderByStartHour(Long catId, LocalDate date)
    {
        return this.query( () ->
                this.categoryBlockDao.getCategoryBlockFromDayAndCategoryAndOrderByStartHour(catId, date));
    }

    //Fetch the last added Category Block
    public CategoryBlock getLastCategoryBlock()
    {
        try {
            return ToDoDatabase.query( this.categoryBlockDao::getLastCategoryBlockAdded);
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new CategoryBlock();
    }

    public

    //Before doing this please invoke the method Category.updateStartAndEndTimeFromACategoryBlock()
    public void update(CategoryBlock categoryBlock)
    {
        categoryBlock.setUpdated(LocalDate.now());
        categoryBlock.setVersion(categoryBlock.getVersion() + 1);

        ToDoDatabase.execute( () -> categoryBlockDao.update(categoryBlock));
    }

    public void insert(CategoryBlock categoryBlock)
    {
        categoryBlock.setCreated(LocalDate.now());
        categoryBlock.setUpdated(categoryBlock.getCreated());
        categoryBlock.setVersion(1);

        ToDoDatabase.execute( () -> categoryBlockDao.insert(categoryBlock));
    }
    */
}
