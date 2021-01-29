package com.pme.mpe.model.user;


import android.util.Log;

import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.exceptions.CategoryBlockException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.user.exceptions.CategoryException;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void aCategoryCanBeAddedAndDeletedFromAnUser() throws CategoryException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");

        //Step 1 (Add Category)
        Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF", "000000");
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
        Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF","000000");

        //Assertion Step
        testUser.removeOneCategoryFromUserCategories(testCategory);
    }

    @Test
    public void aCategoryMayBeUpdated() throws CategoryException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");
        Category oldCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF","000000");
        Category newCategory = new Category(testUser.getUserId(), "Updated Category", "FFFFFF","000000");
        testUser.addOneCategoryToUserCategories(oldCategory);

        //Update Category
        testUser.updateOneCategoryFromUserCategories(oldCategory, newCategory);

        //Assertion Step
        assertTrue(testUser.getUserCategories().get(0) == newCategory);
    }

    @Test
    public void positiveTestFromMethodGetAllCategoryBlocksFromUserForAGivenDate() throws TimeException, CategoryBlockException {
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");
        Category category = new Category(testUser.getUserId(), "Test Category", "FFFFFF","000000");
        testUser.addOneCategoryToUserCategories(category);
        LocalDate localDate = LocalDate.of(2022, 5, 5);
        category.addCategoryBlock("testCB", localDate, 10, 12, testUser);
        category.addCategoryBlock("testCB", localDate, 16, 17, testUser);

        //Assertion Step
        assertTrue(testUser.getAllCategoryBlocksFromUserForAGivenDate(localDate, testUser).size() == 2);
    }

    @Test
    public void negativeTestFromMethodGetAllCategoryBlocksFromUserForAGivenDate(){
        //Given
        User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");
        Category category = new Category(testUser.getUserId(), "Test Category", "FFFFFF","000000");
        testUser.addOneCategoryToUserCategories(category);
        LocalDate localDate = LocalDate.now();

        User otherUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");

        //Trying to retrieve the category block and giving a parameter other than the user itself, no list is returner
        //Assertion Step
        assertTrue(testUser.getAllCategoryBlocksFromUserForAGivenDate(localDate, otherUser) == null);
    }
}
