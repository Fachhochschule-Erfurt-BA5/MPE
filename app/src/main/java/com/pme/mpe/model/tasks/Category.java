package com.pme.mpe.model.tasks;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
@Entity
public class Category {

    @Ignore
    public static final String LOG_TAG = "Category";

    /* /////////////////////Attributes///////////////////////// */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    public long categoryId;

    @NotNull
    @ColumnInfo(name = "version")
    private int version;

    @NotNull
    @ColumnInfo(name = "created")
    private LocalDate created;

    @NotNull
    @ColumnInfo(name = "updated")
    private LocalDate updated;

    @NotNull
    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @NotNull
    @ColumnInfo(name = "color")
    private String color;

    @NotNull
    @ColumnInfo(name = "letterColor")
    private String letterColor;

    // represent the User Id create this Cat
    @NotNull
    @ColumnInfo(name = "userID")
    public long userId;

    @Ignore
    private List<Task> taskList;

    @Ignore
    private List<CategoryBlock> categoryBlockList;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Category.
     *
     * @param userId       the user id
     * @param categoryName the category name
     * @param color        the color
     */
    public Category(long userId, String categoryName, String color, String letterColor) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.color = color;
        this.letterColor = letterColor;
        this.taskList = new ArrayList<>();
        this.categoryBlockList = new ArrayList<>();

        CategoryBlock cb = new CategoryBlock(this);
        this.categoryBlockList.add(cb);
    }


    /* /////////////////////Getter/Setter///////////////////////// */

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @NotNull
    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(@NotNull LocalDate created) {
        this.created = created;
    }

    @NotNull
    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(@NotNull LocalDate updated) {
        this.updated = updated;
    }

    @NotNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotNull String categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull
    public String getColor() {
        return color;
    }

    public void setColor(@NotNull String color) {
        this.color = color;
    }

    @NotNull
    public String getLetterColor() {
        return letterColor;
    }

    public void setLetterColor(@NotNull String letterColor) {
        this.letterColor = letterColor;
    }

    @NotNull
    public long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull long userId) {
        this.userId = userId;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<CategoryBlock> getCategoryBlockList() {
        return categoryBlockList;
    }

    public void setCategoryBlockList(List<CategoryBlock> categoryBlockList) {
        this.categoryBlockList = categoryBlockList;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Create and assign task to category.
     *
     * @param name        the name
     * @param description the description
     * @param duration    the duration
     * @param deadline    the deadline
     */
    public Task createAndAssignTaskToCategory(String name, String description, int duration, LocalDate deadline, String taskColor)
    {
        Task createdTask = new Task(name, description, this.categoryId, duration, deadline, taskColor);
        this.taskList.add(createdTask);
        return createdTask;
    }

    /**
     * Created fixed task and assign to a given Category Box.
     *
     * @param name          the name
     * @param description   the description
     * @param duration      the duration
     * @param deadline      the deadline
     * @param categoryBlock the category block
     * @return the boolean
     * @throws TaskFixException      the task fix exception
     * @throws TaskDeadlineException the task deadline exception
     */
    public Task createdFixedTaskAndAssignToCategoryBlock(String name, String description, int duration, LocalDate deadline, CategoryBlock categoryBlock, String taskColor) throws TaskFixException, TaskDeadlineException {
        Task createdTask = new Task(name, description, this.categoryId, duration, deadline, categoryBlock.getCatBlockId(), categoryBlock, taskColor);
        this.taskList.add(createdTask);

        if(categoryBlock.isTheDeadlineInBoundOfCategoryBlock(deadline))
        {
            if(categoryBlock.isEnoughTimeForATaskAvailable(createdTask))
            {
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

        return createdTask;
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
     * Gets default category block.
     *
     * @return the default category block
     */
    public CategoryBlock getDefaultCategoryBlock()
    {
        CategoryBlock defaultCB = null;

        for (int i = 0; i < this.categoryBlockList.size(); i++) {
            if (this.categoryBlockList.get(i).getTitle() == "Default CB")
            {
                defaultCB = this.categoryBlockList.get(i);
            }
        }
        return defaultCB;
    }

    /**
     * Add category block taking into account the surrounding Category Blocks
     *
     * @param title         the title
     * @param date          the date
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     * @param user          the user
     * @return the category block
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public CategoryBlock addCategoryBlock(String title, LocalDate date, int startTimeHour, int endTimeHour, User user) throws CategoryBlockException, TimeException {

        if(areTheGivenHoursValid(startTimeHour, endTimeHour))
        {
            //Retrieve all Category Blocks from the required day
            List<CategoryBlock> categoryBlocksOnDay = user.getAllCategoryBlocksFromUserForAGivenDate(date, user);

            if(categoryBlocksOnDay != null)
            {
                //Sort the List with the Starting Time
                Collections.sort(categoryBlockList);

                //Only if there are more or two Category Blocks in the day
                if(categoryBlocksOnDay.size() >= 2)
                {
                    boolean slotFound = true;

                    for (int i = 0; i < categoryBlocksOnDay.size(); i++) {
                        //Check that start time does not intercede whit another Category Block
                        if(startTimeHour >= categoryBlocksOnDay.get(i).getEndTimeHour() && endTimeHour <= categoryBlocksOnDay.get(i+1).getStartTimeHour())
                        {
                            CategoryBlock cb = new CategoryBlock(title,this.getCategoryId(), date, startTimeHour, endTimeHour);
                            this.categoryBlockList.add(cb);
                            return cb;
                        }
                        else {
                            slotFound = false;
                        }
                    }
                    if(!slotFound)
                    {
                        Log.w(LOG_TAG, "The given Time Interferes with another Category Block");
                        throw new CategoryBlockException("The given Time Interferes with another Category Block");
                    }
                }
                //If there is only one category block for that day
                else if (categoryBlocksOnDay.size() == 1)
                {
                    if(startTimeHour >= categoryBlocksOnDay.get(0).getEndTimeHour() || endTimeHour <= categoryBlocksOnDay.get(0).getStartTimeHour())
                    {
                        CategoryBlock cb = new CategoryBlock(title,this.getCategoryId(), date, startTimeHour, endTimeHour);
                        this.categoryBlockList.add(cb);
                        return cb;
                    }
                    else {
                        Log.w(LOG_TAG, "The given Time Interferes whit another Category Block");
                        throw new CategoryBlockException("The given Time Interferes whit another Category Block");
                    }
                }
                else if (categoryBlocksOnDay.size() == 0)
                {
                    CategoryBlock cb = new CategoryBlock(title,this.getCategoryId(), date, startTimeHour, endTimeHour);
                    this.categoryBlockList.add(cb);
                    return cb;
                }
            }
        }
        return null;
    }

    /**
     * Delete category block.
     * If there are Assigned Tasks to the Category Block, they would be unassigned
     *
     * @param categoryBlock the category block
     * @throws TaskFixException the task fix exception
     */
    public void deleteCategoryBlock(CategoryBlock categoryBlock) throws TaskFixException {
        if(!categoryBlock.isDefaultCB())
        {
            categoryBlock.prepareCategoryBlockToBeDeleted();
            this.categoryBlockList.remove(categoryBlock);
        }
    }

    /**
     * Is it possible to change time on category block boolean.
     *
     * @param categoryBlock   the category block
     * @param newStartingTime the new starting time
     * @param newEndTime      the new end time
     * @param user            the user
     * @return the boolean
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public boolean isItPossibleToChangeTimeOnCategoryBlock(CategoryBlock categoryBlock, int newStartingTime, int newEndTime, User user) throws CategoryBlockException, TimeException {
        boolean result = true;

        if(areTheGivenHoursValid(newStartingTime, newEndTime))
        {
            //Get all Category Blocks from the same day
            List<CategoryBlock> categoryBlocksOnDay = user.getAllCategoryBlocksFromUserForAGivenDate(categoryBlock.getDate(), user);

            if(categoryBlocksOnDay != null)
            {
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
        }

        return result;
    }

    /**
     * Update start and end time from a category block.
     *
     * @param categoryBlock   the category block
     * @param newStartingTime the new starting time
     * @param newEndTime      the new end time
     * @param user            the user
     * @throws CategoryBlockException the category block exception
     * @throws TimeException          the time exception
     */
    public CategoryBlock updateStartAndEndTimeFromACategoryBlock(CategoryBlock categoryBlock, int newStartingTime, int newEndTime, User user)
            throws CategoryBlockException, TimeException {

        if(!categoryBlock.isDefaultCB())
        {
            if(isItPossibleToChangeTimeOnCategoryBlock(categoryBlock, newStartingTime, newEndTime, user))
            {
                //If it does not interfere with another Category Block, save the change
                int indexOfGivenCategoryBlock = this.getCategoryBlockList().indexOf(categoryBlock);
                this.getCategoryBlockList().get(indexOfGivenCategoryBlock).setStartTimeHour(newStartingTime);
                this.getCategoryBlockList().get(indexOfGivenCategoryBlock).setEndTimeHour(newEndTime);
                return this.getCategoryBlockList().get(indexOfGivenCategoryBlock);
            }
        }
        return null;
    }

    /**
     * [DEPRECATED] Gets all category blocks for a given date.
     *
     * @param date the date
     * @return the all category blocks for a given date
     */
    private List<CategoryBlock> getAllCategoryBlocksForAGivenDate(LocalDate date)
    {
        List<CategoryBlock> categoryBlocks = new ArrayList<CategoryBlock>();

        for (int i = 0; i < this.getCategoryBlockList().size(); i++) {
            if(this.getCategoryBlockList().get(i).getDate() == date) {
                        categoryBlocks.add(this.categoryBlockList.get(i));
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
     * None fitting task whit the given category blocks, are to be added to the Default Category Block of a Category
     *
     * @return the boolean
     */
    public void autoAssignTasks() {
        removeAllSoftFixedTasksFromCB();

        Collections.sort(taskList);

        for (int i = 0; i < this.taskList.size(); i++) {
            //Only relevant for not fixed tasks
            if(!taskList.get(i).isTaskFixed())
            {
                Collections.sort(categoryBlockList);

                boolean categoryBlockFound = false;
                int defaultCBPosition = 0;

                for (int j = 0; j < categoryBlockList.size(); j++) {

                    if(!categoryBlockList.get(j).isDefaultCB())
                    {
                        //Is the deadline in bounds
                        if(categoryBlockList.get(j).isTheDeadlineInBoundOfCategoryBlock(taskList.get(i).getDeadline()))
                        {
                            //Is enough time on category Block?
                            if(categoryBlockList.get(j).isEnoughTimeForATaskAvailableTakingIntoAccountSoftFixedTasks(taskList.get(i)))
                            {
                                //Add task to the soft fixed tasks of the found block
                                taskList.get(i).setTaskSoftFixed(true);
                                categoryBlockList.get(j).addTaskToSoftFixedTasks(taskList.get(i));
                                categoryBlockFound = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        defaultCBPosition = j;
                    }
                }
                //Assign to default CB if not a better place was found
                if(!categoryBlockFound)
                {
                    taskList.get(i).setTaskSoftFixed(true);
                    categoryBlockList.get(defaultCBPosition).addTaskToSoftFixedTasks(taskList.get(i));
                }
            }
        }
    }

    private void removeAllSoftFixedTasksFromCB()
    {
        for (int i = 0; i < this.categoryBlockList.size(); i++) {
            if( this.categoryBlockList.get(i).getSoftFixedTasks().size() > 0)
            {
                for (int j = this.categoryBlockList.get(i).getSoftFixedTasks().size() - 1; j >= 0 ; j--) {
                    this.categoryBlockList.get(i).getSoftFixedTasks().get(j).setTaskSoftFixed(false);
                    this.categoryBlockList.get(i).getSoftFixedTasks().remove(j);
                }
            }
        }
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
                ", categoryName='" + categoryName + '\'' +
                ", taskList=" + taskList +
                ", categoryBlockList=" + categoryBlockList +
                '}';
    }
}
