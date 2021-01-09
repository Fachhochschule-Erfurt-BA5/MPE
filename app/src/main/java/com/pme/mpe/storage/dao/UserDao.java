package com.pme.mpe.storage.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.pme.mpe.model.relations.UserCategoryRelation;
import com.pme.mpe.model.user.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("DELETE FROM User")
    void deleteAll();

    @Query("SELECT count(*) FROM User")
    int count();

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User ORDER BY firstName ASC")
    List<User> getUsersSortByName();

    @Query("SELECT * FROM User ORDER BY userId DESC LIMIT 1")
    User getLastUserAdded();

    @Query("SELECT * FROM User WHERE userId LIKE :search")
    List<User> getUsersForId(long search);

    // Query for the relation between the User and Category
    @Transaction
    @Query("SELECT * FROM User")
    public List<UserCategoryRelation> getCategoriesForUser();
}
