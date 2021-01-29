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
                Faker faker = Faker.instance();

                for (int i = 0; i < 3; i++)
                {
                    //Add some Users
                    User user = new User(faker.name().firstName(), faker.name().lastName(), "someMail@gmail.com",
                                        "12345678", "URL");
                    user.setCreated(LocalDate.now());
                    user.setUpdated(user.getCreated());
                    user.setVersion(1);
                    long userId = userDao.insert(user);

                    //Add some Categories
                    Category category = new Category(userId, "Free Time", "#666666", "#000000");
                    category.setCreated(LocalDate.now());
                    category.setUpdated(user.getCreated());
                    category.setVersion(1);
                    category.setCategoryId(tasksPackageDao.insertCategory(category));

                    //Prepare the Default CB to be saved
                    CategoryBlock defaultCB = category.getDefaultCategoryBlock();
                    defaultCB.setCreated(LocalDate.now());
                    defaultCB.setUpdated(user.getCreated());
                    defaultCB.setVersion(1);
                    defaultCB.setCB_CategoryId(category.getCategoryId());
                    tasksPackageDao.insertCategoryBlock(defaultCB);

                    //Add a Category Block for each Category
                    CategoryBlock cb = null;
                    try {
                        cb = category.addCategoryBlock("TestCB", LocalDate.of(2021, Month.FEBRUARY, 16),
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
                                                        2, LocalDate.of(2021, Month.MARCH, 30));
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
                                                                                LocalDate.of(2021, Month.FEBRUARY, 16), cb);
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
                }
                Log.i(LOG_TAG_DB, "inserted needed elements");
            });
            }
        };
    }
