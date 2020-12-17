package com.pme.mpe.storage.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pme.mpe.model.tasks.Task;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task... tasks);

    @Update
    void update(Task... tasks);

    @Delete
    void delete(Task... tasks);

    @Query("DELETE FROM TASK")
    void deleteAll();

    @Query("SELECT count(*) FROM Task")
    int count();

    @Query("SELECT * FROM Task")
    List<Task> getTasks();

    @Query("SELECT * FROM Task ORDER BY created ASC")
    List<Task> getATasksSortByCreateDate();

    @Query("SELECT * FROM Task ORDER BY id DESC LIMIT 1")
    Task getLastTaskAdded();

    @Query("SELECT * FROM Task WHERE deadline LIKE :search")
    List<Task> getTasksForDeadline(LocalDate search);


}
