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
import com.pme.mpe.model.user.User;
import com.pme.mpe.model.util.LocalDateConverter;
import com.pme.mpe.storage.dao.TasksPackageDao;
import com.pme.mpe.storage.dao.UserDao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Category.class , CategoryBlock.class, Task.class, User.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class ToDoDatabase extends RoomDatabase {

    private static final String LOG_TAG_DB = "ToDoDB";

    /*
        Contact DAO reference, will be filled by Android
     */
    public abstract TasksPackageDao tasksPackageDao();
    public abstract UserDao userDao();

    /*
        Singleton Instance
     */
    private static volatile ToDoDatabase INSTANCE;

    /*
    Singleton 'getInstance' method to create database instance thereby opening and, if not
    already done, init the database. Note the 'createCallback'.
 */
    public static ToDoDatabase getDatabase(final Context context)
    {
        Log.i( LOG_TAG_DB, "getDatabaseCalled");
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

    /*
    Executor service to perform database operations asynchronous and independent from UI thread
 */
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    /*
        Helper methods to ease external usage of ExecutorService
        e.g. perform async database operations
     */
    public static <T> T executeWithReturn(Callable<T> task)
            throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny(Collections.singletonList(task));
    }

    public static void execute(Runnable runnable)
    {
        databaseWriteExecutor.execute(runnable);
    }



    /*
        Create DB Callback
        Used to add some initial data
    */
    private static RoomDatabase.Callback createCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
            {
                super.onCreate(db);

                Log.i(LOG_TAG_DB, "onCreate() called");

                execute(() ->
                {
                   UserDao dao = INSTANCE.userDao();

                    Faker faker = Faker.instance();
                    for (int i = 0; i < 10; i++)
                    {
                        User user = new User(faker.name().firstName(), faker.name().lastName(), faker.name().username(), faker.name().nameWithMiddle(), faker.funnyName().name());
                        user.setCreated(LocalDate.now());
                        user.setUpdated(user.getCreated());
                        user.setVersion(1);
                        dao.insert(user);
                    }
                    Log.i(LOG_TAG_DB, "inserted 10 Value to DB.user");
                });
            }
        };
    }
