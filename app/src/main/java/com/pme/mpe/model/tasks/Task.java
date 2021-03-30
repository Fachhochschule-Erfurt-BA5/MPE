package com.pme.mpe.model.tasks;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


/**
 * The type Task.
 * A task may be fixed to a CategoryBlock
 * A task may be shared between users
 */
@Entity
public class Task implements Comparable<Task> {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "Task";

    /* /////////////////////Attributes///////////////////////// */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

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
    @ColumnInfo(name = "name")
    private String name;

    @NotNull
    @ColumnInfo(name = "description")
    private String description;

    @NotNull
    @ColumnInfo(name = "duration")
    private int duration = 1;

    @NotNull
    @ColumnInfo(name = "deadline")
    private LocalDate deadline;

    @NotNull
    @ColumnInfo(name = "isTaskFixed")
    private boolean isTaskFixed;

    @NotNull
    @ColumnInfo(name = "isTaskSoftFixed")
    private boolean isTaskSoftFixed;

    @NotNull
    @ColumnInfo(name = "taskColor")
    private String taskColor;

    //represent the Category ID , which this Task have
    @NotNull
    @ColumnInfo(name = "T_categoryID")
    public long T_categoryId;

    //represent the CategoryBlock, where the task is being done
    //It may be fixed or changed dynamically
    @NotNull
    @ColumnInfo(name = "T_categoryBlockID")
    public long T_categoryBlockId;

    @Ignore
    private CategoryBlock categoryBlock;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Task which is neither fixed or shared.
     *
     * @param name         the name
     * @param description  the description
     * @param T_categoryId the category id
     * @param duration     the duration
     * @param deadline     the deadline
     * @param taskColor    the Color
     */
    public Task(String name, String description, long T_categoryId, int duration, LocalDate deadline, String taskColor) {
        this.name = name;
        this.description = description;
        this.T_categoryId = T_categoryId;
        this.duration = duration;
        this.deadline = deadline;
        this.isTaskFixed = false;
        this.taskColor = taskColor;
    }

    //TODO:Bitte das löschen sobald es möglich ist
    /*Test for RecyclerView
    @Ignore
    public Task(String name) {
        this.name = name;
    }
*/

    /**
     * Instantiates a new Task which is fixed to a Category Block.
     *
     * @param name              the name
     * @param description       the description
     * @param T_categoryId      the t category id
     * @param duration          the duration
     * @param deadline          the deadline
     * @param T_categoryBlockId the category block id
     * @param taskColor         the Color
     */
    public Task(String name, String description, long T_categoryId, int duration, LocalDate deadline, long T_categoryBlockId, CategoryBlock categoryBlock, String taskColor) {
        this.name = name;
        this.description = description;
        this.T_categoryId = T_categoryId;
        this.T_categoryBlockId = T_categoryBlockId;
        this.duration = duration;
        this.deadline = deadline;
        this.isTaskFixed = true;
        this.categoryBlock = categoryBlock;
        this.taskColor = taskColor;
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    @NotNull
    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isTaskFixed() {
        return isTaskFixed;
    }

    public void setTaskFixed(boolean taskFixed) {
        isTaskFixed = taskFixed;
    }

    public boolean isTaskSoftFixed() {
        return isTaskSoftFixed;
    }

    public void setTaskSoftFixed(boolean taskSoftFixed) {
        isTaskSoftFixed = taskSoftFixed;
    }

    public long getT_categoryId() {
        return T_categoryId;
    }

    public void setT_categoryId(long t_categoryId) {
        T_categoryId = t_categoryId;
    }

    public long getT_categoryBlockId() {
        return T_categoryBlockId;
    }

    public void setT_categoryBlockId(long t_categoryBlockId) {
        T_categoryBlockId = t_categoryBlockId;
    }

    public CategoryBlock getCategoryBlock() {
        return categoryBlock;
    }

    public void setCategoryBlock(CategoryBlock categoryBlock) {
        this.categoryBlock = categoryBlock;
    }

    public void setTaskColor(String taskColor) {
        this.taskColor = taskColor;
    }

    public String getTaskColor() {
        return this.taskColor;
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
    public boolean fixTaskToCategoryBlock(@NotNull CategoryBlock categoryBlock) throws TaskFixException, TaskDeadlineException {
        boolean result = false;

        if (categoryBlock.isTheDeadlineInBoundOfCategoryBlock(this.deadline)) {
            if (categoryBlock.isEnoughTimeForATaskAvailable(this)) {
                if (this.isTaskFixed()) {
                    if (this.categoryBlock == categoryBlock) {
                        Log.w(LOG_TAG, "Task already fixed to the given Category Block");
                        throw new TaskFixException("Task already fixed to the given Category Block");
                    } else {
                        Log.w(LOG_TAG, "Task already fixed to another Category Block");
                        throw new TaskFixException("Task already fixed to another Category Block");
                    }
                } else {
                    result = true;
                    this.isTaskFixed = true;
                    this.categoryBlock = categoryBlock;
                    categoryBlock.addTaskToFixedTasks(this);
                }
            } else {
                Log.w(LOG_TAG, "Task already fixed to another Category Block");
                throw new TaskFixException("Task already fixed to another Category Block");
            }
        } else {
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

        if (this.isTaskFixed) {
            //this.isTaskFixed = false;
            //   this.categoryBlock.removeTaskFromFixedTasks(this);
            this.categoryBlock = null;
            result = true;
        } else {
            Log.w(LOG_TAG, "Cannot unassign what is not assigned");
            throw new TaskFixException("Cannot unassign what is not assigned");
        }

        return result;
    }

    /**
     * Duration is being changed, if the new duration does not fit the assigned Category Block, it will be unassigned.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) throws TaskFixException {
        if (this.isTaskFixed && this.categoryBlock != null) {
            if (!this.categoryBlock.isEnoughTimeForAFixedTaskUpdateAvailable(this, duration)) {
                this.isTaskFixed = false;
                this.categoryBlock = null;
                this.duration = duration;

                throw new TaskFixException("New Duration does not fit the current Category Block, therefor task was unassigned");
            }
        }

        this.duration = duration;
    }

    /**
     * Update the deadline of a task.
     *
     * @param deadline the deadline
     * @throws TimeException the time exception
     */
    public void setDeadline(LocalDate deadline) throws TimeException {
        //It is only possible to set a deadline for today or the future
        if (deadline.isAfter(LocalDate.now()) || deadline.isEqual(LocalDate.now())) {
            /*if(this.isTaskFixed())
            {
                //Is the new deadline in bound of the category block ?
                if(this.getCategoryBlock().isTheDeadlineInBoundOfCategoryBlock(deadline))
                {
                    this.deadline = deadline;
                }
                else
                {
                    throw new TimeException("The Task is Fixed and Deadline is not in bound of the Category Block");
                }
            }
            else
            {*/
            this.deadline = deadline;
        }
        //}
        else {
            throw new TimeException("Not possible to change the Deadline to the past");
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + T_categoryId +
                ", duration=" + duration +
                ", taskCreator=" +
                '}';
    }

    @Override
    public int compareTo(Task task) {
        int cmp = (this.deadline.getYear() - task.deadline.getYear());
        if (cmp == 0) {
            cmp = (this.deadline.getDayOfYear() - this.deadline.getDayOfYear());
        }
        return cmp;
    }
}
