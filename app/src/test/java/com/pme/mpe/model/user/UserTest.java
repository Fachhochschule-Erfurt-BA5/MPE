package com.pme.mpe.model.user;


import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.user.exceptions.CategoryException;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void aCategoryCanBeAddedAndDeletedFromAnUser() throws CategoryException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");

        //Step 1 (Add Category)
        Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF");
        testUser.addOneCategoryToUserCategories(testCategory);
        assertTrue(testUser.getUserCategories().size() > 0);

        //Step 2 (Remove Category)
        testUser.removeOneCategoryFromUserCategories(testCategory);
        assertTrue(testUser.getUserCategories().size() == 0);
    }

    @Test(expected = CategoryException.class)
    public void aThrowableShouldBeThrownWhenTryingToDeleteANonExistingCategory() throws CategoryException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");
        Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF");

        //Assertion Step
        testUser.removeOneCategoryFromUserCategories(testCategory);
    }

    @Test
    public void aCategoryMayBeUpdated() throws CategoryException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");
        Category oldCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF");
        Category newCategory = new Category(testUser.getUserId(), "Updated Category", "FFFFFF");
        testUser.addOneCategoryToUserCategories(oldCategory);

        //Update Category
        testUser.updateOneCategoryFromUserCategories(oldCategory, newCategory);

        //Assertion Step
        assertTrue(testUser.getUserCategories().get(0) == newCategory);
    }
}
