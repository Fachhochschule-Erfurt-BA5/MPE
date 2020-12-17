package com.pme.mpe.model.tasks;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pme.mpe.model.tasks.exceptions.TaskFixException;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Category block.
 */
@Entity
public class CategoryBlock implements Comparable<CategoryBlock>{

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "CategoryBlock";

    /* /////////////////////Attributes///////////////////////// */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long catBlockId;

    private int version;

    @NotNull
    @ColumnInfo(name = "created")
    private LocalDate created;

    @NotNull
    @ColumnInfo(name = "updated")
    private LocalDate updated;

    @NotNull
    @ColumnInfo(name = "date")
    private LocalDate date;

    @NotNull
    @ColumnInfo(name = "startTimeHour")
    private int startTimeHour;

    @NotNull
    @ColumnInfo(name = "endTimeHour")
    private int endTimeHour;

    // represent the Category Id, where this block belong
    public long catCatBlockId;

    //represent the Category Block ID , which this Task belong to
    public long taskCatBlockId;

    @Ignore
    public Category category;

    //For the hard fixed tasks
    @Ignore
    private List<Task> assignedTasks;

    /* /////////////////////Constructors/////////////////////////// */

    public CategoryBlock() {
    }

    /**
     * Instantiates a new Category block.
     *
     * @param category      the category
     * @param date          the date
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     */
    protected CategoryBlock(Category category, LocalDate date, int startTimeHour, int endTimeHour) {
        this.category = category;
        this.date = date;
        this.startTimeHour = startTimeHour;
        this.endTimeHour = endTimeHour;
        this.assignedTasks = new ArrayList<>();
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
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
     * Gets start time hour.
     *
     * @return the start time hour
     */
    public int getStartTimeHour() {
        return startTimeHour;
    }

    /**
     * Sets start time hour.
     *
     * @param startTimeHour the start time hour
     */
    public void setStartTimeHour(int startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    /**
     * Gets end time hour.
     *
     * @return the end time hour
     */
    public int getEndTimeHour() {
        return endTimeHour;
    }

    /**
     * Sets end time hour.
     *
     * @param endTimeHour the end time hour
     */
    public void setEndTimeHour(int endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    /**
     * Gets assigned tasks.
     *
     * @return the assigned tasks
     */
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    @NotNull
    public LocalDate getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(@NotNull LocalDate created) {
        this.created = created;
    }

    /**
     * Gets updated.
     *
     * @return the updated
     */
    @NotNull
    public LocalDate getUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(@NotNull LocalDate updated) {
        this.updated = updated;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Add task to fixed tasks.
     *
     * @param task the task
     */
    protected void addTaskToFixedTasks(Task task)
    {
        this.assignedTasks.add(task);
    }

    /**
     * Remove task from fixed tasks.
     *
     * @param task the task
     */
    protected void removeTaskFromFixedTasks(Task task)
    {
        this.assignedTasks.remove(task);
    }

    /**
     * Return remaining free time on slot int.
     *
     * @return the int
     */
    public int returnRemainingFreeTimeOnSlot()
    {
        int totalFreeTimeOnSlot = this.endTimeHour - this.startTimeHour;

        for (int i = 0; i < this.assignedTasks.size(); i++) {
            totalFreeTimeOnSlot = totalFreeTimeOnSlot - assignedTasks.get(i).getDuration();
        }

        return totalFreeTimeOnSlot;
    }

    /**
     * Is enough time for a task available boolean.
     *
     * @param task the task
     * @return the boolean
     */
    public boolean isEnoughTimeForATaskAvailable(Task task)
    {
        return returnRemainingFreeTimeOnSlot() > task.getDuration();
    }

    /**
     * Is enough time for a task update available boolean.
     *
     * @param task        the task
     * @param newDuration the new duration
     * @return the boolean
     */
    public boolean isEnoughTimeForAFixedTaskUpdateAvailable(Task task, int newDuration) {
        boolean result = true;

        for (int i = 0; i < this.assignedTasks.size(); i++) {
            if(this.assignedTasks.get(i) == task)
            {
                if(returnRemainingFreeTimeOnSlot() + this.assignedTasks.get(i).getDuration() < newDuration)
                {
                    Log.w(LOG_TAG, "Not enough time in time slot");
                    result = false;
                }
                else{
                    result = true;
                }
            }
            else
            {
                result = false;
            }
        }
        return result;
    }

    /**
     * Is the deadline in bound of category block boolean.
     *
     * @param taskDate the task date
     * @return the boolean
     */
    public boolean isTheDeadlineInBoundOfCategoryBlock(LocalDate taskDate)
    {
        if(this.date.getYear() >= taskDate.getYear())
        {
            if(this.date.getMonthValue() >= taskDate.getMonthValue())
            {
                return this.date.getDayOfMonth() >= taskDate.getDayOfMonth();
            }
        }
        return false;
    }

    /**
     * Prepare category block to be deleted.
     */
    public void prepareCategoryBlockToBeDeleted() throws TaskFixException {
        for (int i = 0; i < this.assignedTasks.size() ; i++) {
            this.assignedTasks.get(i).unfixTaskFromCategoryBlock();
        }
    }

    /* /////////////////////Overrides/////////////////////////// */

    @Override
    public int compareTo(CategoryBlock categoryBlock) {
        int compareStartTime= categoryBlock.getStartTimeHour();

        return this.startTimeHour - compareStartTime;
    }

    @Override
    public String toString() {
        return "CategoryBlock{" +
                "date=" + date +
                ", startTimeHour=" + startTimeHour +
                ", endTimeHour=" + endTimeHour +
                ", category=" + category +
                '}';
    }
}
