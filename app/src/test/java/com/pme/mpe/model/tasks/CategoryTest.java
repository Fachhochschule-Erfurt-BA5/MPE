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

    Category testCategory = new Category(testUser.getUserId(), "Test Category", "FFFFFF", "000000");

    @Test
    public void aTaskMayBeFixedAndUnfixedFromACategoryBlock() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.createAndAssignTaskToCategory("Test-Task", "Description", 2, may5, "#FEFEFE");
        testCategory.addCategoryBlock("test", may5, 10, 14 , testUser);

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
        testCategory.addCategoryBlock("test", may5, 10, 14 , testUser);

        //Create Task and Fix to category Block
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, testCategory.getCategoryBlockList().get(1), "#FEFEFE");

        //Assert the the Task is fixed to the category block
        assertTrue(testCategory.getCategoryBlockList().get(1).getAssignedTasks().get(0) == testCategory.getTaskList().get(0));
    }

    @Test(expected = TaskFixException.class)
    public void aThrowableShouldBeThrownWhenTryingToAssignTaskToCategoryBlockWithNotEnoughTime() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        CategoryBlock cb = testCategory.addCategoryBlock("test", may5, 10, 11 , testUser);

        //An Exception should be thrown here because of the duration
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, cb, "#FEFEFE");
    }

    @Test(expected = TaskDeadlineException.class)
    public void aThrowableShouldBeThrownWhenTryingToAssignTaskToCategoryBlockNotInBoundFromDeadline() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        CategoryBlock cb = testCategory.addCategoryBlock("test", may1, 10, 15, testUser);

        //An Exception should be thrown here because of the deadline
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",2, may5, cb, "#FEFEFE");
    }

    @Test
    public void aCategoryBlockMayBeDeletedIfNotTasksAssigned() throws TaskFixException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 15, testUser );

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
        testCategory.addCategoryBlock("test", may5, 10, 15, testUser );
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",
                2, may5, testCategory.getCategoryBlockList().get(0),"#FEFEFE");

        //Assert that the Category Block from the Task is the right one
        assertTrue(testCategory.getTaskList().get(0).getCategoryBlock() == testCategory.getCategoryBlockList().get(0));

        //Delete category block
        testCategory.deleteCategoryBlock(testCategory.getCategoryBlockList().get(0));

        //Assert that the Task which had a Fixed Category Block no longer has one
        assertTrue(testCategory.getTaskList().get(0).getCategoryBlock().getCatBlockId() == 0);
    }

    @Test(expected = TaskFixException.class)
    public void ByChangingTheDurationOfAFixedTaskAndExceedingTheAvailableTimeOnCategoryBlockTheTaskIsUnfixed() throws TaskFixException, TaskDeadlineException, CategoryBlockException, TimeException {
        //Given
        testCategory.addCategoryBlock("test", may5, 10, 13, testUser );
        testCategory.createdFixedTaskAndAssignToCategoryBlock("Test-Task", "Description",
                2, may5, testCategory.getCategoryBlockList().get(1),"#FEFEFE");

        //By changing the duration of the Task an exception should be thrown
        testCategory.getTaskList().get(0).setDuration(5);

        //The task should not be longer fixed to the category block
        assertTrue(!testCategory.getTaskList().get(0).isTaskFixed());
    }

    @Test(expected = CategoryBlockException.class)
    public void ByCreatingACategoryBlockBetweenTwoOtherCBWhereItDoesNotFitAnExceptionShouldBeThrown() throws CategoryBlockException, TimeException {
        //Given
        testUser.addOneCategoryToUserCategories(testCategory);
        testCategory.addCategoryBlock("test", may5, 10, 11, testUser );
        testCategory.addCategoryBlock("test", may5, 13, 15, testUser );

        //By adding a non fitting Category Block an exception is thrown
        testCategory.addCategoryBlock("test", may5, 11, 15, testUser );
    }

    @Test(expected = CategoryBlockException.class)
    public void ByCreatingACategoryBlockThatInterferesWithAnotherCBAnExceptionShouldBeThrown() throws CategoryBlockException, TimeException {
        //Given
        testUser.addOneCategoryToUserCategories(testCategory);
        testCategory.addCategoryBlock("test", may5, 10, 12, testUser );

        //By adding a Category Block on the same time an exception is thrown
        testCategory.addCategoryBlock("test", may5, 9, 11, testUser );
    }

    @Test(expected = CategoryBlockException.class)
    public void ExpandingTheTimeOfACategoryBlockAndInterferingWithAnotherShouldThrowAnException() throws CategoryBlockException, TimeException {
        //Given
        testUser.addOneCategoryToUserCategories(testCategory);
        CategoryBlock cb = testCategory.addCategoryBlock("test", may5, 10, 12, testUser );
        testCategory.addCategoryBlock("test", may5, 12, 14, testUser );

        //Changing the end time of the first CB an exception should be thrown
        testCategory.updateStartAndEndTimeFromACategoryBlock(cb, 9, 13, testUser);
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

    @Test
    public void autoAssignTest() throws TimeException, CategoryBlockException, TaskFixException, TaskDeadlineException {
        //Given
        testUser.addOneCategoryToUserCategories(testCategory);
        //Add 4 Category Blocks
        CategoryBlock cb1 = testCategory.addCategoryBlock("test1", may1, 10, 13, testUser );
        CategoryBlock cb2 = testCategory.addCategoryBlock("test1", may1, 15, 18, testUser );
        CategoryBlock cb3 = testCategory.addCategoryBlock("test1", may5, 10, 12, testUser );
        CategoryBlock cb4 = testCategory.addCategoryBlock("test1", may5, 14, 16, testUser );

        //Identify the default CB
        CategoryBlock defaultCB = testCategory.getDefaultCategoryBlock();

        //Just to be sure
        //Taking into account that a category always have a Default Category Block
        assertTrue(testCategory.getCategoryBlockList().size() == 5);

        //Some Category Blocks have some fixed Tasks
        Task fixedTask1 = testCategory.createdFixedTaskAndAssignToCategoryBlock("FixedTestTask1", "Descrip", 1, may1, cb1, "#FEFEFE");
        Task fixedTask2 = testCategory.createdFixedTaskAndAssignToCategoryBlock("FixedTestTask2", "Descrip", 2, may5, cb3, "#FEFEFE");
        Task fixedTask3 = testCategory.createdFixedTaskAndAssignToCategoryBlock("FixedTestTask3", "Descrip", 1, may5, cb4, "#FEFEFE");

        //Just to be sure
        assertTrue(cb1.getAssignedTasks().size() == 1);
        assertTrue(cb3.getAssignedTasks().size() == 1);
        assertTrue(cb4.getAssignedTasks().size() == 1);
        assertTrue(fixedTask1.isTaskFixed());
        assertTrue(fixedTask2.isTaskFixed());
        assertTrue(fixedTask3.isTaskFixed());

        //We want a couple of none fixed tasks
        Task testTask1 = testCategory.createAndAssignTaskToCategory("TestTask1", "Descrip", 2, may1, "#FEFEFE");
        Task testTask2 = testCategory.createAndAssignTaskToCategory("TestTask2", "Descrip", 2, may1, "#FEFEFE");
        Task testTask3 = testCategory.createAndAssignTaskToCategory("TestTask3", "Descrip", 1, may5, "#FEFEFE");
        Task taskGoingToDefaultBD = testCategory.createAndAssignTaskToCategory("taskGoingToDefaultBD", "Descrip", 5, may5, "#FEFEFE");

        //Test Task should not be soft fixed (yet)
        assertTrue(!testTask1.isTaskSoftFixed());
        assertTrue(!testTask2.isTaskSoftFixed());
        assertTrue(!testTask3.isTaskSoftFixed());
        assertTrue(!taskGoingToDefaultBD.isTaskSoftFixed());

        //Just to be sure
        assertTrue(testCategory.getTaskList().size() == 7);

        //Before the Auto Assign method
        assertTrue(cb1.returnRemainingFreeTimeOnSlot() == 2);
        assertTrue(cb2.returnRemainingFreeTimeOnSlot() == 3);
        assertTrue(cb3.returnRemainingFreeTimeOnSlot() == 0);
        assertTrue(cb4.returnRemainingFreeTimeOnSlot() == 1);
        assertTrue(defaultCB.getSoftFixedTasks().size() == 0);

        //Call the auto assign method
        testCategory.autoAssignTasks();

        //Test if the tasks are soft fixed
        assertTrue(testTask1.isTaskSoftFixed());
        assertTrue(testTask2.isTaskSoftFixed());
        assertTrue(testTask3.isTaskSoftFixed());

        //CB1 should be assigned to cb1
        assertTrue(cb1.getSoftFixedTasks().size() == 1);
        //And still have the fixed task
        assertTrue(cb1.getAssignedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb1.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 0);

        //CB2 should receives any the TestTask2, Test Task would fit, but the deadline is not in bounds
        assertTrue(cb2.getSoftFixedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb2.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 1);

        //CB3 should not receive any tasks (Not enough time remaining for task)
        assertTrue(cb3.getSoftFixedTasks().size() == 0);
        //And still have the fixed task
        assertTrue(cb3.getAssignedTasks().size() == 1);
        assertTrue(cb3.returnRemainingFreeTimeOnSlot() == 0);

        //CB4 received the TestTask3
        assertTrue(cb4.getSoftFixedTasks().size() == 1);
        //And still have the fixed task
        assertTrue(cb4.getAssignedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb4.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 0);

        //The non fitting task should go to the default CB
        assertTrue(defaultCB.getSoftFixedTasks().size() == 1);

        //Lets try once again the auto assign method to test the removeAllSoftFixedTasksFromCB method
        testCategory.autoAssignTasks();

        //Test if the tasks are soft fixed
        assertTrue(testTask1.isTaskSoftFixed());
        assertTrue(testTask2.isTaskSoftFixed());
        assertTrue(testTask3.isTaskSoftFixed());

        //CB1 should be assigned to cb1
        assertTrue(cb1.getSoftFixedTasks().size() == 1);
        //And still have the fixed task
        assertTrue(cb1.getAssignedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb1.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 0);

        //CB2 should receives any the TestTask2, Test Task would fit, but the deadline is not in bounds
        assertTrue(cb2.getSoftFixedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb2.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 1);

        //CB3 should not receive any tasks (Not enough time remaining for task)
        assertTrue(cb3.getSoftFixedTasks().size() == 0);
        //And still have the fixed task
        assertTrue(cb3.getAssignedTasks().size() == 1);
        assertTrue(cb3.returnRemainingFreeTimeOnSlot() == 0);

        //CB4 received the TestTask3
        assertTrue(cb4.getSoftFixedTasks().size() == 1);
        //And still have the fixed task
        assertTrue(cb4.getAssignedTasks().size() == 1);
        //And the remaining time including the soft task
        assertTrue(cb4.returnRemainingFreeTimeOnSlotTakingIntoAccountSoftFixedTasks() == 0);

        //The non fitting task should go to the default CB
        assertTrue(defaultCB.getSoftFixedTasks().size() == 1);
    }
}
