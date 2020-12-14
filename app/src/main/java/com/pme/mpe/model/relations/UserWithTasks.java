package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.user.User;

import java.util.List;

public class UserWithTasks {
    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "taskCreatorId"
    )
    public List<Task> tasks;
}
