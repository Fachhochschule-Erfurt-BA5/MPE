package com.pme.mpe.model.tasks;

import android.util.Log;

import com.pme.mpe.model.format.Day;
import com.pme.mpe.model.format.Month;
import com.pme.mpe.model.format.Week;
import com.pme.mpe.model.user.User;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
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
     * Create and assign shared task to category.
     *
     * @param name                the name
     * @param description         the description
     * @param duration            the duration
     * @param deadlineYear        the deadline year
     * @param deadlineMonth       the deadline month
     * @param deadlineDay         the deadline day
     * @param sharedWithUsersList the shared with users list
     */
    public void createAndAssignSharedTaskToCategory(String name, String description, int duration, int deadlineYear, Month deadlineMonth, int deadlineDay,
                                                    List<User> sharedWithUsersList)
    {
        Task createdTask = new Task(name, description, this, duration, deadlineYear, deadlineMonth, deadlineDay, sharedWithUsersList, this.user);
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
     */
    public boolean createdFixedTaskAndAssignToCategoryBlock(String name, String description, int duration, int deadlineYear, Month deadlineMonth,
                                                            int deadlineDay, CategoryBlock categoryBlock)
    {
        boolean result = true;

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
                result = false;
            }
        }
        else
        {
            Log.w(LOG_TAG, "Deadline not possible");
            result = false;
        }

        return result;
    }

    /**
     * Assign task to category block boolean.
     *
     * @param task          the task
     * @param categoryBlock the category block
     * @return the boolean
     */
    public boolean assignFixedTaskToCategoryBlock(Task task, CategoryBlock categoryBlock)
    {
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
     * @param task          the task
     * @param categoryBlock the category block
     */
    public void deassignFixedTaskFromCategoryBlock(Task task, CategoryBlock categoryBlock)
    {
        task.unfixTaskFromCategoryBlock();
    }


    /**
     * Add category block.
     *
     * @param month         the month
     * @param week          the week
     * @param day           the day
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     */
    public void addCategoryBlock(Month month, Week week, Day day, int startTimeHour, int endTimeHour)
    {
        CategoryBlock cb = new CategoryBlock(this, month, week, day, startTimeHour, endTimeHour);
        this.categoryBlockList.add(cb);
    }

    /**
     * Delete category block.
     *
     * @param categoryBlock the category block
     */
    public void deleteCategoryBlock(CategoryBlock categoryBlock)
    {
        categoryBlock.prepareCategoryBlockToBeDeleted();
        this.categoryBlockList.remove(categoryBlock);
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
