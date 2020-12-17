package com.pme.mpe.storage.contactDatabase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.github.javafaker.Faker;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.user.User;
import com.pme.mpe.storage.dao.CategoryBlockDao;
import com.pme.mpe.storage.dao.CategoryDao;
import com.pme.mpe.storage.dao.TaskDao;
import com.pme.mpe.storage.dao.UserDao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Category.class , CategoryBlock.class, Task.class, User.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    private static final String LOG_TAG_DB = "ToDoDB";

    public abstract CategoryBlockDao categoryBlockDao();
    public abstract CategoryDao categoryDao();
    public abstract TaskDao taskDao();
    public abstract UserDao userDao();

    private static final int NUMBER_OF_THREADS = 4;

    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile ToDoDatabase INSTANCE;


    public static <T> T query(Callable<T> task) throws ExecutionException, InterruptedException
    {
        return databaseWriteExecutor.invokeAny(Collections.singletonList(task));
    }

    public static void execute(Runnable runnable)
    {
        databaseWriteExecutor.execute(runnable);
    }

    static ToDoDatabase getDatabase(final Context context)
    {
        Log.i( LOG_TAG_DB, "getDatabaseCalled");
        if (INSTANCE == null)
        {
            synchronized (ToDoDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ToDoDatabase.class, "todo_database").addCallback(createCallback).build();
                }
            }
        }
        return INSTANCE;
    }


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
