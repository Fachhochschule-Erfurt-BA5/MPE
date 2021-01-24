package com.pme.mpe.storage.repository;

import android.content.Context;

import com.pme.mpe.model.user.User;
import com.pme.mpe.storage.dao.UserDao;
import com.pme.mpe.storage.database.ToDoDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    public static final String LOG_TAG = "UserRepository";

    private UserDao userDao;

    public UserRepository( Context context ) {
        ToDoDatabase db = ToDoDatabase.getDatabase( context );
        this.userDao = db.userDao();
    }

    public List<User> getUsers()
    {
        return this.query( () -> this.userDao.getUsers() );
    }

    private List<User> query( Callable<List<User>> query )
    {
        try {
            return ToDoDatabase.query( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void insert(User user) {
        user.setCreated(LocalDate.now());
        user.setUpdated(user.getCreated());
        user.setVersion(1);
        ToDoDatabase.execute( () -> userDao.insert( user ) );
    }
}
