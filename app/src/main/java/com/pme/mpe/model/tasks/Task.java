package com.pme.mpe.model.tasks;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.user.User;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


/**
 * The type Task.
 * A task may be fixed to a CategoryBlock
 * A task may be shared between users
 */
@Entity
public class Task {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "Task";

    /* /////////////////////Attributes///////////////////////// */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    private int version;

    @NotNull
    @ColumnInfo(name = "created")
    private LocalDate created;

    @NotNull
    @ColumnInfo(name = "updated")
    private LocalDate updated;

    @NotNull
    @ColumnInfo(name = "name")
    private String name;

    @NotNull
    @ColumnInfo(name = "description")
    private String description;

    @NotNull
    @ColumnInfo(name = "duration")
    private int duration = 1;

    //represent the Category ID , which this Task have
    public long taskCategoryId;

    @NotNull
    @ColumnInfo(name = "deadline")
    private LocalDate deadline;

    // For the case of a fixed Task, otherwise is null and false
    // TODO the relation between the Entities definition
    @Ignore
    private CategoryBlock categoryBlock;

    @Ignore
    private Category category;

    @Ignore
    private User user;

    @NotNull
    @ColumnInfo(name = "isTaskFixed")
    private boolean isTaskFixed;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Task.
     */
    public Task() {
    }

    /**
     * Instantiates a new Task which is neither fixed or shared.
     *
     * @param name         the name
     * @param description  the description
     * @param taskCategory the category Id
     * @param duration     the duration
     * @param deadline     the deadline
     */
    protected Task(String name, String description, Category taskCategory, int duration, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.taskCategoryId = taskCategory.categoryId;
        this.duration = duration;
        this.deadline = deadline;
        this.categoryBlock = null;
        this.isTaskFixed = false;
    }

    /**
     * Instantiates a new Task which is fixed to a Category Block.
     *
     * @param name          the name
     * @param description   the description
     * @param taskCategory  the category
     * @param duration      the duration
     * @param deadline      the deadline
     * @param categoryBlock the category block
     */
    protected Task(String name, String description, Category taskCategory, int duration, LocalDate deadline, CategoryBlock categoryBlock) {
        this.name = name;
        this.description = description;
        this.taskCategoryId = taskCategory.categoryId;
        this.duration = duration;
        this.deadline = deadline;
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
    public long getCategory() {
        return taskCategoryId;
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
     * Gets deadline.
     *
     * @return the deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Sets deadline.
     *
     * @param deadline the deadline
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
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

    /**
     * Sets Is task fixed boolean.
     *
     * @param isTaskFixed
     */
    public void isTaskFixed(boolean isTaskFixed){
        this.isTaskFixed = isTaskFixed;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Fix task to category block.
     *
     * @param categoryBlock the category block
     * @return a boolean which tells if the desired function was successful
     * @throws TaskFixException      the task fix exception
     * @throws TaskDeadlineException the task deadline exception
     */
    protected boolean fixTaskToCategoryBlock(CategoryBlock categoryBlock) throws TaskFixException, TaskDeadlineException {
        boolean result = false;

        if(categoryBlock.isTheDeadlineInBoundOfCategoryBlock(this.deadline))
        {
            if(categoryBlock.isEnoughTimeForATaskAvailable(this))
            {
                if(this.isTaskFixed())
                {
                    if(this.categoryBlock == categoryBlock)
                    {
                        Log.w(LOG_TAG, "Task already fixed to the given Category Block");
                        throw new TaskFixException("Task already fixed to the given Category Block");
                    }
                    else
                    {
                        Log.w(LOG_TAG, "Task already fixed to another Category Block");
                        throw new TaskFixException("Task already fixed to another Category Block");
                    }
                }
                else
                {
                    result = true;
                    this.isTaskFixed = true;
                    this.categoryBlock = categoryBlock;
                    categoryBlock.addTaskToFixedTasks(this);
                }
            }
            else
            {
                Log.w(LOG_TAG, "Task already fixed to another Category Block");
                throw new TaskFixException("Task already fixed to another Category Block");
            }
        }
        else
        {
            Log.w(LOG_TAG, "Deadline is before the Category Block");
            throw new TaskDeadlineException("Deadline is before the Category Block");
        }


        return result;
    }

    /**
     * Unfix task from category block.
     *
     * @return the boolean
     * @throws TaskFixException the task fix exception
     */
    public boolean unfixTaskFromCategoryBlock() throws TaskFixException {
        boolean result = false;

        if(this.isTaskFixed)
        {
            this.isTaskFixed = false;
            this.categoryBlock.removeTaskFromFixedTasks(this);
            this.categoryBlock = null;
            result = true;
        }
        else
        {
            Log.w(LOG_TAG, "Cannot unassign what is not assigned");
            throw new TaskFixException("Cannot unassign what is not assigned");
        }

        return result;
    }

    /**
     * Duration is being changed, if the new duration does not fit the assigned Category Block, it will be unassigned.
     *
     * @param duration the duration
     * @throws TaskFixException the task fix exception
     */
    public void setDuration(int duration) {
        if(this.isTaskFixed && this.categoryBlock != null)
        {
            if(!this.categoryBlock.isEnoughTimeForAFixedTaskUpdateAvailable(this, duration))
            {
                Log.w(LOG_TAG, "New Duration does not fit the current Category Block, therefor task was unassigned");
                this.isTaskFixed = false;
                this.categoryBlock = null;
                this.duration = duration;
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
                ", categoryId=" + taskCategoryId +
                ", duration=" + duration +
                ", taskCreator=" +
                '}';
    }
}
