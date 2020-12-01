package com.pme.mpe.model.tasks;

import android.util.Log;

import com.pme.mpe.model.format.Month;
import com.pme.mpe.model.user.User;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Task.
 * <p>
 * A task may be fixed to a CategoryBlock
 * A task may be shared between users
 */
public class Task {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "Task";

    /* /////////////////////Attributes///////////////////////// */

    private long id;
    private int version;
    private LocalDate created;
    private LocalDate updated;

    private String name;
    private String description;
    private final Category category;
    private int duration;
    private final User taskCreator;

    private int deadlineYear;
    private Month deadlineMonth;
    private int deadlineDay;

    // For the case of a shared Task
    private List<User> sharedWithUsersList;
    private boolean isTaskShareable;

    // For the case of a fixed Task, otherwise is null and false
    private CategoryBlock categoryBlock;
    private boolean isTaskFixed;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Task which is neither fixed or shared.
     *
     * @param name          the name
     * @param description   the description
     * @param category      the category
     * @param duration      the duration
     * @param deadlineYear  the deadline year
     * @param deadlineMonth the deadline month
     * @param deadlineDay   the deadline day
     * @param taskCreator   the task creator
     */
    protected Task(String name, String description, Category category, int duration, int deadlineYear, Month deadlineMonth, int deadlineDay, User taskCreator) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.taskCreator = taskCreator;
        this.deadlineYear = deadlineYear;
        this.deadlineMonth = deadlineMonth;
        this.deadlineDay = deadlineDay;
        this.categoryBlock = null;
        this.isTaskFixed = false;
        this.isTaskShareable = false;
        this.sharedWithUsersList = null;
    }

    /**
     * Instantiates a new Task which is fixed to a Category Block.
     *
     * @param name          the name
     * @param description   the description
     * @param category      the category
     * @param duration      the duration
     * @param deadlineYear  the deadline year
     * @param deadlineMonth the deadline month
     * @param deadlineDay   the deadline day
     * @param categoryBlock the category block
     * @param taskCreator   the task creator
     */
    protected Task(String name, String description, Category category, int duration, int deadlineYear, Month deadlineMonth, int deadlineDay,
                CategoryBlock categoryBlock, User taskCreator) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.taskCreator = taskCreator;
        this.deadlineYear = deadlineYear;
        this.deadlineMonth = deadlineMonth;
        this.deadlineDay = deadlineDay;
        this.categoryBlock = categoryBlock;
        this.isTaskFixed = true;
        this.isTaskShareable = false;
        this.sharedWithUsersList = null;
    }

    /**
     * Instantiates a new Task which is shared with at least one more user.
     *
     * @param name                the name
     * @param description         the description
     * @param category            the category
     * @param duration            the duration
     * @param deadlineYear        the deadline year
     * @param deadlineMonth       the deadline month
     * @param deadlineDay         the deadline day
     * @param sharedWithUsersList the shared with users list
     * @param taskCreator         the task creator
     */
    protected Task(String name, String description, Category category, int duration, int deadlineYear, Month deadlineMonth, int deadlineDay,
                List<User> sharedWithUsersList, User taskCreator) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.taskCreator = taskCreator;
        this.deadlineYear = deadlineYear;
        this.deadlineMonth = deadlineMonth;
        this.deadlineDay = deadlineDay;
        this.categoryBlock = null;
        this.isTaskShareable = true;
        this.sharedWithUsersList = sharedWithUsersList;
    }

    /**
     * Instantiates a new Task which is fixed and shared with at least one more user.
     *
     * @param name                the name
     * @param description         the description
     * @param category            the category
     * @param duration            the duration
     * @param taskCreator         the task creator
     * @param deadlineYear        the deadline year
     * @param deadlineMonth       the deadline month
     * @param deadlineDay         the deadline day
     * @param sharedWithUsersList the shared with users list
     * @param categoryBlock       the category block
     */
    protected Task(String name, String description, Category category, int duration, User taskCreator, int deadlineYear, Month deadlineMonth,
                int deadlineDay, List<User> sharedWithUsersList, CategoryBlock categoryBlock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.taskCreator = taskCreator;
        this.deadlineYear = deadlineYear;
        this.deadlineMonth = deadlineMonth;
        this.deadlineDay = deadlineDay;
        this.sharedWithUsersList = sharedWithUsersList;
        this.isTaskShareable = true;
        this.categoryBlock = categoryBlock;
        this.isTaskFixed = true;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets task creator.
     *
     * @return the task creator
     */
    public User getTaskCreator() {
        return taskCreator;
    }

    /**
     * Gets deadline year.
     *
     * @return the deadline year
     */
    public int getDeadlineYear() {
        return deadlineYear;
    }

    /**
     * Sets deadline year.
     *
     * @param deadlineYear the deadline year
     */
    public void setDeadlineYear(int deadlineYear) {
        this.deadlineYear = deadlineYear;
    }

    /**
     * Gets deadline month.
     *
     * @return the deadline month
     */
    public Month getDeadlineMonth() {
        return deadlineMonth;
    }

    /**
     * Sets deadline month.
     *
     * @param deadlineMonth the deadline month
     */
    public void setDeadlineMonth(Month deadlineMonth) {
        this.deadlineMonth = deadlineMonth;
    }

    /**
     * Gets deadline day.
     *
     * @return the deadline day
     */
    public int getDeadlineDay() {
        return deadlineDay;
    }

    /**
     * Sets deadline day.
     *
     * @param deadlineDay the deadline day
     */
    public void setDeadlineDay(int deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    /**
     * Gets shared with users list.
     *
     * @return the shared with users list
     */
    public List<User> getSharedWithUsersList() {
        return sharedWithUsersList;
    }

    /**
     * Is task shareable boolean.
     *
     * @return the boolean
     */
    public boolean isTaskShareable() {
        return isTaskShareable;
    }

    /**
     * Gets category block.
     *
     * @return the category block
     */
    public CategoryBlock getCategoryBlock() {
        return categoryBlock;
    }

    /**
     * Is task fixed boolean.
     *
     * @return the boolean
     */
    public boolean isTaskFixed() {
        return isTaskFixed;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Share with new users.
     *
     * @param userToAdd the user to be added
     * @return a boolean which tells if the desired function was successful
     */
    public boolean shareWithNewUsers(List<User> userToAdd)
    {
        boolean result = true;

        if(this.isTaskShareable)
        {
            for (int i = 0; i < userToAdd.size() ; i++)
            {
                for (int j = 0; j < this.sharedWithUsersList.size(); j++)
                {
                    //Be certain the user to be added is not already in the list
                    if(userToAdd.get(i) != this.sharedWithUsersList.get(j))
                    {
                        this.sharedWithUsersList.add(userToAdd.get(i));
                        String message = "User: " + userToAdd.get(i).toString() + " successfully added";
                        Log.i(LOG_TAG, message);
                        result = true;
                    }
                    else
                    {
                        Log.w(LOG_TAG, "At least one user of the given List already in this list");
                        result = false;
                    }
                }
            }
        }
        else {
            this.isTaskShareable = true;
            for (int i = 0; i < userToAdd.size() ; i++) {
                String message = "User: " + userToAdd.get(i).toString() + " successfully added";
                Log.i(LOG_TAG, message);
                this.sharedWithUsersList.add(userToAdd.get(i));
            }
        }

        return result;
    }

    /**
     * Fix task to category block.
     *
     * @param categoryBlock the category block
     * @return a boolean which tells if the desired function was successful
     */
    protected boolean fixTaskToCategoryBlock(CategoryBlock categoryBlock)
    {
        boolean result = true;

        if(categoryBlock.isTheDeadlineInBoundOfCategoryBlock(this.deadlineYear, this.deadlineMonth, this.deadlineDay))
        {
            if(categoryBlock.isEnoughTimeForATaskAvailable(this))
            {
                if(this.isTaskFixed())
                {
                    if(this.categoryBlock == categoryBlock)
                    {
                        Log.w(LOG_TAG, "Task already fixed to the given Category Block");
                    }
                    else
                    {
                        Log.w(LOG_TAG, "Task already fixed to another Category Block");
                    }
                    result = false;
                }
                else
                {
                    this.isTaskFixed = true;
                    this.categoryBlock = categoryBlock;
                    categoryBlock.addTaskToFixedTasks(this);
                }
            }
            else
            {
                Log.w(LOG_TAG, "Not enough time left on Category Block");
                result = false;
            }
        }
        else
        {
            Log.w(LOG_TAG, "Deadline is before the Category Block");
            result = false;
        }


        return result;
    }

    /**
     * Unfix task from category block.
     *
     * @return the boolean
     */
    public boolean unfixTaskFromCategoryBlock()
    {
        boolean result = true;

        if(this.isTaskFixed)
        {
            this.isTaskFixed = false;
            this.categoryBlock.removeTaskFromFixedTasks(this);
            this.categoryBlock = null;
        }
        else
        {
            Log.w(LOG_TAG, "Cannot unassign what is not assigned");
            result = false;
        }

        return result;
    }

    /**
     * Duration is being changed, if the new duration does not fit the assigned Category Block, it will be unassigned.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
        if(this.isTaskFixed && this.categoryBlock != null)
        {
            if(!this.categoryBlock.isEnoughTimeForATaskUpdateAvailable(this, duration))
            {
                Log.w(LOG_TAG, "New Duration does not fit the current Category Block, therefor task was unassigned");
                this.isTaskFixed = false;
                this.categoryBlock = null;
            }
        }

        this.duration = duration;
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", duration=" + duration +
                ", taskCreator=" + taskCreator +
                '}';
    }
}
