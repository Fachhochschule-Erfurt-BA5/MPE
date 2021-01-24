package com.pme.mpe.model.tasks;

import com.pme.mpe.model.tasks.exceptions.CategoryBlockException;
import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.user.User;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CategoryTest {
    //Given
    LocalDate may1 = LocalDate.of(2021, 5, 1);
    LocalDate may5 = LocalDate.of(2021, 5, 5);

    User testUser = new User("Max", "Mustermann", "test@mail.com", "verySecurePassword", "No-Profile-Image");

    Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF");

    @Test
    public void aTaskMayBeFixedAndUnfixedFromACategoryBlock() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.createAndAssignTaskToCategory("Test-Task", "Description", 2, may5);
        testCategory.addCategoryBlock("test", may5, 10, 14 );

        //Step 1 No Task Fixed to Category Block
        assertTrue(testCategory.getCategoryBlockList().get(1).getAssignedTasks().size() == 0);

        //Fix task to Category Block
        testCategory.assignFixedTaskToCategoryBlock(testCategory.getTaskList().get(0) ,testCategory.getCategoryBlockList().get(1));

        //Step 2 there is a Fixed to Category Block
        assertTrue(testCategory.getCategoryBlockList().get(1).getAssignedTasks().get(0) == testCategory.getTaskList().get(0));

        //Unfix Task From Category Block
        testCategory.deassignFixedTaskFromCategoryBlock(testCategory.getTaskList().get(0));

        //Step 3 No Task Fixed to Category Block
        assertTrue(testCategory.getCategoryBlockList().get(1).getAssignedTasks().size() == 0);
    }

    @Test
    public void aFixedTaskMayBeCreated() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 14 );

        //Create Task and Fix to category Block
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, testCategory.getCategoryBlockList().get(1));

        //Assert the the Task is fixed to the category block
        assertTrue(testCategory.getCategoryBlockList().get(1).getAssignedTasks().get(0) == testCategory.getTaskList().get(0));
    }

    @Test(expected = TaskFixException.class)
    public void aThrowableShouldBeThrownWhenTryingToAssignTaskToCategoryBlockWithNotEnoughTime() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        CategoryBlock cb = testCategory.addCategoryBlock("test", may5, 10, 11 );

        //An Exception should be thrown here because of the duration
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, cb);
    }

    @Test(expected = TaskDeadlineException.class)
    public void aThrowableShouldBeThrownWhenTryingToAssignTaskToCategoryBlockNotInBoundFromDeadline() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        CategoryBlock cb = testCategory.addCategoryBlock("test", may1, 10, 15 );

        //An Exception should be thrown here because of the deadline
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, cb);
    }

    @Test
    public void aCategoryBlockMayBeDeletedIfNotTasksAssigned() throws TaskFixException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 15 );

        //Assert the Category Block is in the List from the Category
        assertTrue(testCategory.getCategoryBlockList().size() >= 1);

        //Delete Category Block
        testCategory.deleteCategoryBlock(testCategory.getCategoryBlockList().get(1));

        //Assert the Size of the list is now zero
        assertTrue(testCategory.getCategoryBlockList().size() == 1);
    }

    @Test
    public void ByDeletingACategoryBlockWithFixedTasksAllTasksShouldBeUnassigned() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 15 );
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",
                2, may5, testCategory.getCategoryBlockList().get(0));

        //Assert that the Category Block from the Task is the right one
        assertTrue(testCategory.getTaskList().get(0).getCategoryBlock() == testCategory.getCategoryBlockList().get(0));

        //Delete category block
        testCategory.deleteCategoryBlock(testCategory.getCategoryBlockList().get(0));

        //Assert that the Task which had a Fixed Category Block no longer has one
        assertTrue(testCategory.getTaskList().get(0).getCategoryBlock().getCatBlockId() == 0);
    }

    @Test
    public void ByChangingTheDurationOfAFixedTaskAndExceedingTheAvailableTimeOnCategoryBlockTheTaskIsUnfixed() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 13 );
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",
                2, may5, testCategory.getCategoryBlockList().get(1));

        //By changing the duration of the Task an exception should be thrown
        testCategory.getTaskList().get(0).setDuration(5);

        //The task should not be longer fixed to the category block
        assertTrue(!testCategory.getTaskList().get(0).isTaskFixed());
    }

    @Test(expected = CategoryBlockException.class)
    public void ByCreatingACategoryBlockBetweenTwoOtherCBWhereItDoesNotFitAnExceptionShouldBeThrown() throws CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 11 );
        testCategory.addCategoryBlock("test", may5, 13, 15 );

        //By adding a non fitting Category Block an exception is thrown
        testCategory.addCategoryBlock("test", may5, 11, 15 );
    }

    @Test(expected = CategoryBlockException.class)
    public void ByCreatingACategoryBlockThatInterferesWithAnotherCBAnExceptionShouldBeThrown() throws CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 12 );

        //By adding a Category Block on the same time an exception is thrown
        testCategory.addCategoryBlock("test", may5, 9, 11 );
    }

    @Test(expected = CategoryBlockException.class)
    public void ExpandingTheTimeOfACategoryBlockAndInterferingWithAnotherShouldThrowAnException() throws CategoryBlockException, TimeException {
        //Given
        CategoryBlock cb = testCategory.addCategoryBlock("test", may5, 10, 12 );
        testCategory.addCategoryBlock("test", may5, 12, 14 );

        //Changing the end time of the first CB an exception should be thrown
        testCategory.updateStartAndEndTimeFromACategoryBlock(cb, 9, 13);
    }

    @Test(expected = TimeException.class)
    public void anExceptionShouldBeThrownWithInvalidValues() throws TimeException, CategoryBlockException {
        boolean test = testCategory.areTheGivenHoursValid(10, 8);
    }

    @Test(expected = TimeException.class)
    public void anExceptionShouldBeThrownWithInvalidValues2() throws TimeException, CategoryBlockException {
        boolean test = testCategory.areTheGivenHoursValid(100, 200);
    }

    @Test(expected = Test.None.class)
    public void noExceptionShouldBeThrownWithValidHours() throws TimeException, CategoryBlockException {
        boolean test = testCategory.areTheGivenHoursValid(10, 15);
    }
}
