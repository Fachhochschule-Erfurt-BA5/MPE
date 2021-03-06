package com.pme.mpe.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.user.User;

import java.util.List;

public class UserCategoryRelation {
    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userID"
    )
    public List<Category> categories;
}
