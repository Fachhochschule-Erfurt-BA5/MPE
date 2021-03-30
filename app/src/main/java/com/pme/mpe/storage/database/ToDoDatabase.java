package com.pme.mpe.storage.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.github.javafaker.Faker;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.tasks.exceptions.CategoryBlockException;
import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.user.User;
import com.pme.mpe.model.util.LocalDateConverter;
import com.pme.mpe.storage.dao.TasksPackageDao;
import com.pme.mpe.storage.dao.UserDao;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Category.class, CategoryBlock.class, Task.class, User.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class ToDoDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "ToDoDB";

    //Contact DAO reference, will be filled by Android
    public abstract TasksPackageDao tasksPackageDao();
    public abstract UserDao userDao();

    //Singleton Instance
    private static volatile ToDoDatabase INSTANCE;

    //Executor service to perform database operations asynchronous and independent from UI thread
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Helper methods to ease external usage of ExecutorService
    //e.g. perform async database operations
    public static <T> T query(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny( Collections.singletonList( task ) );
    }

    //Helper methods to ease external usage of ExecutorService
    //e.g. perform async database operations
    public static <T> T executeWithReturn(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny(Collections.singletonList(task));
    }

    public static void execute(Runnable runnable)
    {
        databaseWriteExecutor.execute(runnable);
    }

    //Singleton 'getInstance' method to create database instance thereby opening and, if not
    //already done, init the database. Note the 'createCallback'.
    public static ToDoDatabase getDatabase(final Context context)
    {
        Log.i( LOG_TAG_DB, "getDatabase() Called");
        if (INSTANCE == null)
        {
            synchronized (ToDoDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoDatabase.class, "todo_database")
                            .addCallback(createCallback)
                            .allowMainThreadQueries()//TODO: find a way around it (Hamza Harti)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //Create DB Callback
    //Used to add some initial data
    private static RoomDatabase.Callback createCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
            {
            super.onCreate(db);

            Log.i(LOG_TAG_DB, "onCreate() called");

            execute(() ->
            {
                UserDao userDao = INSTANCE.userDao();
                TasksPackageDao tasksPackageDao = INSTANCE.tasksPackageDao();

                    //Add root User
                    User user = new User("Root", "User", "root.user@todo.de","12345678", "URL");
                    user.setCreated(LocalDate.now());
                    user.setUpdated(user.getCreated());
                    user.setVersion(1);
                    long userId = userDao.insert(user);

                    Log.i(LOG_TAG_DB, "user added");

                    //Add some Categories
                    Category category = new Category(userId, "Free Time", "#888888", "#000000");
                    category.setCreated(LocalDate.now());
                    category.setUpdated(category.getCreated());
                    category.setVersion(1);
                    category.setCategoryId(tasksPackageDao.insertCategory(category));

                    Log.i(LOG_TAG_DB, "category 1 added");

                    Category category2 = new Category(userId, "University", "#0000ff", "#ffffff");
                    category2.setCreated(LocalDate.now());
                    category2.setUpdated(category2.getCreated());
                    category2.setVersion(1);
                    category2.setCategoryId(tasksPackageDao.insertCategory(category2));

                    Log.i(LOG_TAG_DB, "category 2 added");

                    //Prepare the Default CB to be saved
                    CategoryBlock defaultCB = category.getDefaultCategoryBlock();
                    defaultCB.setCreated(LocalDate.now());
                    defaultCB.setUpdated(user.getCreated());
                    defaultCB.setVersion(1);
                    defaultCB.setCB_CategoryId(category.getCategoryId());
                    tasksPackageDao.insertCategoryBlock(defaultCB);

                    CategoryBlock defaultCB2 = category2.getDefaultCategoryBlock();
                    defaultCB2.setCreated(LocalDate.now());
                    defaultCB2.setUpdated(user.getCreated());
                    defaultCB2.setVersion(1);
                    defaultCB2.setCB_CategoryId(category2.getCategoryId());
                    tasksPackageDao.insertCategoryBlock(defaultCB2);

                    //Add a Category Block for each Category
                    CategoryBlock cb = null;
                    try {
                        cb = category.addCategoryBlock("TestCB", LocalDate.of(2021, Month.MARCH, 16),
                                                    12, 16, user);
                    } catch (CategoryBlockException e) {
                        e.printStackTrace();
                    } catch (TimeException e) {
                        e.printStackTrace();
                    }

                    if(cb != null) {
                        cb.setCreated(LocalDate.now());
                        cb.setUpdated(user.getCreated());
                        cb.setVersion(1);
                        cb.setCatBlockId(tasksPackageDao.insertCategoryBlock(cb));
                    }

                    //Add a non Fixed Task
                    Task softTask = category.createAndAssignTaskToCategory("Test Task", "Description",
                                                        2, LocalDate.of(2021, Month.MARCH, 30),"#000000");
                    softTask.setCreated(LocalDate.now());
                    softTask.setUpdated(user.getCreated());
                    softTask.setVersion(1);
                    tasksPackageDao.insertTask(softTask);

                    //Create a fixed Task
                    Task fixedTask = null;
                    if(cb != null)
                    {
                        try {
                            fixedTask = category.createdFixedTaskAndAssignToCategoryBlock("Test Fixed Task", "Fixed description", 2,
                                                                                LocalDate.of(2021, Month.FEBRUARY, 16), cb,"#000000");
                        } catch (TaskFixException e) {
                            e.printStackTrace();
                        } catch (TaskDeadlineException e) {
                            e.printStackTrace();
                        }

                        if(fixedTask != null)
                        {
                            fixedTask.setCreated(LocalDate.now());
                            fixedTask.setUpdated(user.getCreated());
                            fixedTask.setVersion(1);

                            tasksPackageDao.insertTask(fixedTask);
                        }
                    }

                Log.i(LOG_TAG_DB, "inserted needed elements");
            });
            }
        };
    }
