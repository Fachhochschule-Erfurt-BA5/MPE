package com.pme.mpe.model.tasks;

import android.util.Log;

import com.pme.mpe.model.format.Day;
import com.pme.mpe.model.format.Month;
import com.pme.mpe.model.format.Week;
import com.pme.mpe.model.tasks.exceptions.CategoryBlockException;
import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.user.User;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Category.
 * Each User has at least one Category.
 * A Category has 0 or more Task and Category Blocks
 */
public class Category {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "Category";

    /* /////////////////////Attributes///////////////////////// */

    private long id;
    private int version;

    private LocalDate created;
    private LocalDate updated;

    private final User user;
    private String categoryName;
    private String color;

    private List<Task> taskList;
    private List<CategoryBlock> categoryBlockList;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Category.
     *
     * @param user         the user
     * @param categoryName the category name
     * @param color        the color
     */
    public Category(User user, String categoryName, String color) {
        this.user = user;
        this.categoryName = categoryName;
        this.color = color;
        this.taskList = new ArrayList<>();
        this.categoryBlockList = new ArrayList<>();
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public LocalDate getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(LocalDate created) {
        this.created = created;
    }

    /**
     * Gets updated.
     *
     * @return the updated
     */
    public LocalDate getUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets task list.
     *
     * @return the task list
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Gets category block list.
     *
     * @return the category block list
     */
    public List<CategoryBlock> getCategoryBlockList() {
        return categoryBlockList;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Create and assign task to category.
     *
     * @param name          the name
     * @param description   the description
     * @param duration      the duration
     * @param deadlineYear  the deadline year
     * @param deadlineMonth the deadline month
     * @param deadlineDay   the deadline day
     */
    public void createAndAssignTaskToCategory(String name, String description, int duration, int deadlineYear, Month deadlineMonth, int deadlineDay)
    {
        Task createdTask = new Task(name, description, this, duration, deadlineYear, deadlineMonth, deadlineDay, this.user);
        this.taskList.add(createdTask);
    }

    /**
     * Created fixed task and assign to a given Category Box.
     *
     * @param name          the name
     * @param description   the description
     * @param duration      the duration
     * @param deadlineYear  the deadline year
     * @param deadlineMonth the deadline month
     * @param deadlineDay   the deadline day
     * @param categoryBlock the category block
     * @return the boolean
     * @throws TaskFixException      the task fix exception
     * @throws TaskDeadlineException the task deadline exception
     */
    public boolean createdFixedTaskAndAssignToCategoryBlock(String name, String description, int duration, int deadlineYear, Month deadlineMonth,
                                                            int deadlineDay, CategoryBlock categoryBlock) throws TaskFixException, TaskDeadlineException {
        if(categoryBlock.isTheDeadlineInBoundOfCategoryBlock(deadlineYear, deadlineMonth, deadlineDay))
        {
            Task createdTask = new Task(name, description, this, duration, deadlineYear, deadlineMonth, deadlineDay,
                    categoryBlock, this.user);

            if(categoryBlock.isEnoughTimeForATaskAvailable(createdTask))
            {
                this.taskList.add(createdTask);
                categoryBlock.addTaskToFixedTasks(createdTask);
            }
            else
            {
                Log.w(LOG_TAG, "Not Enough time in this Category Block");
                throw new TaskFixException("Not Enough time in this Category Block");
            }
        }
        else
        {
            Log.w(LOG_TAG, "Deadline not possible");
            throw new TaskDeadlineException("Deadline is before the Category Block");
        }

        return true;
    }

    /**
     * Assign task to category block boolean.
     *
     * @param task          the task
     * @param categoryBlock the category block
     * @return the boolean
     * @throws TaskFixException      the task fix exception
     * @throws TaskDeadlineException the task deadline exception
     */
    public boolean assignFixedTaskToCategoryBlock(Task task, CategoryBlock categoryBlock) throws TaskFixException, TaskDeadlineException {
        boolean assignedSuccessfully = task.fixTaskToCategoryBlock(categoryBlock);

        if(assignedSuccessfully)
        {
            Log.i(LOG_TAG, "Task assigned successfully");
        }
        else
        {
            Log.w(LOG_TAG, "Assigning not possible");
        }

        return assignedSuccessfully;
    }

    /**
     * Deassign task from category block.
     *
     * @param task the task
     * @throws TaskFixException the task fix exception
     */
    public void deassignFixedTaskFromCategoryBlock(Task task) throws TaskFixException {
        task.unfixTaskFromCategoryBlock();
    }


    /**
     * Add category block taking into account the surrounding Category Blocks
     *
     * @param month         the month
     * @param week          the week
     * @param day           the day
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public void addCategoryBlock(Month month, Week week, Day day, int startTimeHour, int endTimeHour) throws CategoryBlockException, TimeException {

        if(areTheGivenHoursValid(startTimeHour, endTimeHour))
        {
            //Retrieve all Category Blocks from the required day
            List<CategoryBlock> categoryBlocksOnDay = getAllCategoryBlocksForAGivenDate(month, week, day);
            //Sort the List with the Starting Time
            Collections.sort(categoryBlockList);

            //Only if there are more or two Category Blocks in the day
            if(categoryBlocksOnDay.size() >= 2)
            {
                boolean slotFound = true;

                for (int i = 0; i < categoryBlocksOnDay.size(); i++) {
                    //Check that start time does not intercede whit another Category Block
                    if(startTimeHour >= this.getCategoryBlockList().get(i).getEndTimeHour() && endTimeHour <= this.getCategoryBlockList().get(i+1).getStartTimeHour())
                    {
                        CategoryBlock cb = new CategoryBlock(this, month, week, day, startTimeHour, endTimeHour);
                        this.categoryBlockList.add(cb);
                        break;
                    }
                    else {
                        slotFound = false;
                    }
                }
                if(!slotFound)
                {
                    Log.w(LOG_TAG, "The given Time Interferes whit another Category Block");
                    throw new CategoryBlockException("The given Time Interferes whit another Category Block");
                }
            }
            //If there is only one category block for that day
            else if (categoryBlocksOnDay.size() == 1)
            {
                if(startTimeHour >= this.getCategoryBlockList().get(0).getEndTimeHour() || endTimeHour <= this.getCategoryBlockList().get(0).getStartTimeHour())
                {
                    CategoryBlock cb = new CategoryBlock(this, month, week, day, startTimeHour, endTimeHour);
                    this.categoryBlockList.add(cb);
                }
                else {
                    Log.w(LOG_TAG, "The given Time Interferes whit another Category Block");
                    throw new CategoryBlockException("The given Time Interferes whit another Category Block");
                }
            }
            else if (categoryBlocksOnDay.size() == 0)
            {
                CategoryBlock cb = new CategoryBlock(this, month, week, day, startTimeHour, endTimeHour);
                this.categoryBlockList.add(cb);
            }
        }
    }

    /**
     * Delete category block.
     * If there are Assigned Tasks to the Category Block, they would be unassigned
     *
     * @param categoryBlock the category block
     * @throws TaskFixException the task fix exception
     */
    public void deleteCategoryBlock(CategoryBlock categoryBlock) throws TaskFixException {
        categoryBlock.prepareCategoryBlockToBeDeleted();
        this.categoryBlockList.remove(categoryBlock);
    }

    /**
     * Is it possible to change time on category block boolean.
     *
     * @param categoryBlock   the category block
     * @param newStartingTime the new starting time
     * @param newEndTime      the new end time
     * @return the boolean
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public boolean isItPossibleToChangeTimeOnCategoryBlock(CategoryBlock categoryBlock, int newStartingTime, int newEndTime) throws CategoryBlockException, TimeException {
        boolean result = true;

        if(areTheGivenHoursValid(newStartingTime, newEndTime))
        {
            //Get all Category Blocks from the same day
            List<CategoryBlock> categoryBlocksOnDay = getAllCategoryBlocksForAGivenDate(categoryBlock.getMonth(), categoryBlock.getWeek(), categoryBlock.getDay());

            //Sort the List with the Starting Time
            Collections.sort(categoryBlockList);

            int indexOnSortedList = categoryBlocksOnDay.indexOf(categoryBlock);

            //Comparison whit both the next an the previous Category Blocks
            if(categoryBlocksOnDay.size() >= 3)
            {
                if(categoryBlocksOnDay.get(indexOnSortedList - 1).getEndTimeHour() <= newStartingTime)
                {
                    if (categoryBlocksOnDay.get(indexOnSortedList + 1).getStartTimeHour() >= newEndTime)
                    {

                    }
                    else
                    {
                        Log.w(LOG_TAG, "The End Time Interferes whit another Category Block");
                        throw new CategoryBlockException("The End Time Interferes whit another Category Block");
                    }
                }
                else
                {
                    Log.w(LOG_TAG, "The Start Time Interferes whit another Category Block");
                    throw new CategoryBlockException("The Start Time Interferes whit another Category Block");
                }
            }
            else if (categoryBlocksOnDay.size() == 2)
            {
                //Just check underneath
                if(indexOnSortedList == 1)
                {
                    if(categoryBlocksOnDay.get(0).getEndTimeHour() <= newStartingTime) {}
                    else
                    {
                        Log.w(LOG_TAG, "The Start Time Interferes whit another Category Block");
                        throw new CategoryBlockException("The Start Time Interferes whit another Category Block");
                    }
                }
                else {
                    if(categoryBlocksOnDay.get(1).getStartTimeHour() >= newEndTime) {}
                    else
                    {
                        Log.w(LOG_TAG, "The End Time Interferes whit another Category Block");
                        throw new CategoryBlockException("The End Time Interferes whit another Category Block");
                    }
                }
            }
        }

        return result;
    }

    /**
     * Update start and end time from a category block.
     *
     * @param categoryBlock   the category block
     * @param newStartingTime the new starting time
     * @param newEndTime      the new end time
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public void updateStartAndEndTimeFromACategoryBlock(CategoryBlock categoryBlock, int newStartingTime, int newEndTime)
            throws CategoryBlockException, TimeException {

        if(isItPossibleToChangeTimeOnCategoryBlock(categoryBlock, newStartingTime, newEndTime))
        {
            //If it does not interfere with another Category Block, save the change
            int indexOfGivenCategoryBlock = this.getCategoryBlockList().indexOf(categoryBlock);
            this.getCategoryBlockList().get(indexOfGivenCategoryBlock).setStartTimeHour(newStartingTime);
            this.getCategoryBlockList().get(indexOfGivenCategoryBlock).setEndTimeHour(newEndTime);
        }

    }

    /**
     * Gets all category blocks for a given date.
     *
     * @param month the month
     * @param week  the week
     * @param day   the day
     * @return the all category blocks for a given date
     */
    public List<CategoryBlock> getAllCategoryBlocksForAGivenDate(Month month, Week week, Day day)
    {
        List<CategoryBlock> categoryBlocks = new ArrayList<CategoryBlock>();

        for (int i = 0; i < this.getCategoryBlockList().size(); i++) {
            if(this.getCategoryBlockList().get(i).getMonth() == month) {
                if(this.getCategoryBlockList().get(i).getWeek() == week) {
                    if(this.getCategoryBlockList().get(i).getDay() == day) {
                        categoryBlocks.add(this.categoryBlockList.get(i));
                    }
                }
            }
        }

        return categoryBlocks;
    }

    /**
     * Are the given hours valid boolean.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the boolean
     * @throws TimeException the time exception
     */
    public boolean areTheGivenHoursValid(int startTime, int endTime) throws TimeException {
        if(0 <= startTime && startTime <= 24)
        {
            if(0 <= endTime && endTime <= 24)
            {
                if(endTime > startTime)
                {
                    return true;
                }
                else
                {
                    Log.w(LOG_TAG, "The End Time Should be after the Start Time");
                    throw new TimeException("The End Time Should be after the Start Time");
                }
            }
            else {
                Log.w(LOG_TAG, "The End Time is not valid");
                throw new TimeException("The End Time is not valid");
            }
        }
        else
        {
            Log.w(LOG_TAG, "The Start Time is not valid");
            throw new TimeException("The Start Time is not valid");
        }
    }

    /**
     * Auto assign tasks boolean.
     *
     * @return the boolean
     */
    public boolean autoAssignTasks()
    {

        return true;
    }

    /* /////////////////////Overrides/////////////////////////// */

    /**
     * To string string.
     *
     * @return the string
     */
    @NotNull
    @Override
    public String toString() {
        return "Category{" +
                "user=" + user +
                ", categoryName='" + categoryName + '\'' +
                ", taskList=" + taskList +
                ", categoryBlockList=" + categoryBlockList +
                '}';
    }
}
