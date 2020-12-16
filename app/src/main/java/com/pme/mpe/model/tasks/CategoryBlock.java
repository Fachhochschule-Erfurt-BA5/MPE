package com.pme.mpe.model.tasks;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pme.mpe.model.format.Day;
import com.pme.mpe.model.format.Month;
import com.pme.mpe.model.format.Week;
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

    // TODO the relation between the Entities definition
    private Month month;
    private Week week;
    private Day day;

    @NotNull
    @ColumnInfo(name = "startTimeHour")
    private int startTimeHour;

    @NotNull
    @ColumnInfo(name = "endTimeHour")
    private int endTimeHour;

    // represent the Category Id, where this block belong
    public long catCatBlockId;

    @Ignore
    public Category category;

    //For the hard fixed tasks
    private List<Task> assignedTasks;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Category block.
     *
     * @param category      the category
     * @param month         the month
     * @param week          the week
     * @param day           the day
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     */
    protected CategoryBlock(Category category, Month month, Week week, Day day, int startTimeHour, int endTimeHour) {
        this.category = category;
        this.month = month;
        this.week = week;
        this.day = day;
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
     * Gets month.
     *
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Gets week.
     *
     * @return the week
     */
    public Week getWeek() {
        return week;
    }

    /**
     * Sets week.
     *
     * @param week the week
     */
    public void setWeek(Week week) {
        this.week = week;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(Day day) {
        this.day = day;
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
    public boolean isEnoughTimeForAFixedTaskUpdateAvailable(Task task, int newDuration) throws TaskFixException {
        boolean result = true;

        for (int i = 0; i < this.assignedTasks.size(); i++) {
            if(this.assignedTasks.get(i) == task)
            {
                if(returnRemainingFreeTimeOnSlot() + this.assignedTasks.get(i).getDuration() < newDuration)
                {
                    Log.w(LOG_TAG, "Not enough time in time slot");
                    throw new TaskFixException("Not enough time for an Update in time slot");
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
     * @param deadlineYear  the deadline year
     * @param deadlineMonth the deadline month
     * @param deadlineDay   the deadline day
     * @return the boolean
     */
    public boolean isTheDeadlineInBoundOfCategoryBlock(int deadlineYear, Month deadlineMonth, int deadlineDay)
    {
        if(this.month.getYear() >= deadlineYear)
        {
            if(this.month.getMonthNumber() >= deadlineMonth.getMonthNumber())
            {
                return this.day.getWeekday() >= deadlineDay;
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

    @NotNull
    @Override
    public String toString() {
        return "CategoryBlock{" +
                "category=" + category +
                ", month=" + month +
                ", week=" + week +
                ", day=" + day +
                ", startTimeHour=" + startTimeHour +
                ", endTimeHour=" + endTimeHour +
                '}';
    }

}
