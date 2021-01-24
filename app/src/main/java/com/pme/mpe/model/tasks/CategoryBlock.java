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

    @Ignore
    public static final String LOG_TAG = "CategoryBlock";

    /* /////////////////////Attributes///////////////////////// */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long catBlockId;

    @NotNull
    @ColumnInfo(name = "version")
    private int version;

    @NotNull
    @ColumnInfo(name = "title")
    private String title;

    @NotNull
    @ColumnInfo(name = "created")
    private LocalDate created;

    @NotNull
    @ColumnInfo(name = "updated")
    private LocalDate updated;

    @ColumnInfo(name = "date")
    private LocalDate date;

    @ColumnInfo(name = "startTimeHour")
    private int startTimeHour;

    @ColumnInfo(name = "endTimeHour")
    private int endTimeHour;

    @NotNull
    @ColumnInfo(name = "isDefaultCB")
    private boolean isDefaultCB;

    //Represent the Category Id, where this block belong
    @NotNull
    @ColumnInfo(name = "CB_CategoryId")
    public long CB_CategoryId;

    //For the hard fixed tasks
    @Ignore
    private List<Task> assignedTasks;

    //For the soft fixed tasks
    @Ignore
    private List<Task> temporallyAssignedTasks;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Category block.
     *
     * @param title         the title
     * @param CB_CategoryId the category id
     * @param date          the date
     * @param startTimeHour the start time hour
     * @param endTimeHour   the end time hour
     */
    public CategoryBlock(String title, long CB_CategoryId, LocalDate date, int startTimeHour, int endTimeHour) {
        this.title = title;
        this.CB_CategoryId = CB_CategoryId;
        this.date = date;
        this.startTimeHour = startTimeHour;
        this.endTimeHour = endTimeHour;
        this.assignedTasks = new ArrayList<>();
        this.isDefaultCB = false;
    }

    /**
     * Instantiates a new Default Category block.
     * Each Category has only one Default Category Block, where all the non fitting task are put
     *
     * @param category the category
     */
    protected CategoryBlock(Category category)
    {
        this.title = "Default CB";
        this.CB_CategoryId = category.getCategoryId();
        this.catBlockId = category.getCategoryId();
        this.isDefaultCB = true;
    }

    //TODO:Bitte das löschen sobald es möglich ist
    /*Test for RecyclerView*/
    public CategoryBlock(String title, List<Task> assignedTasks) {
        this.title = title;
        this.assignedTasks = assignedTasks;
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    public long getCatBlockId() {
        return catBlockId;
    }

    public void setCatBlockId(long catBlockId) {
        this.catBlockId = catBlockId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(int startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public int getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(int endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public boolean isDefaultCB() {
        return isDefaultCB;
    }

    public void setDefaultCB(boolean defaultCB) {
        isDefaultCB = defaultCB;
    }

    public long getCB_CategoryId() {
        return CB_CategoryId;
    }

    public void setCB_CategoryId(long CB_CategoryId) {
        this.CB_CategoryId = CB_CategoryId;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<Task> getTemporallyAssignedTasks() {
        return temporallyAssignedTasks;
    }

    public void setTemporallyAssignedTasks(List<Task> temporallyAssignedTasks) {
        this.temporallyAssignedTasks = temporallyAssignedTasks;
    }

    /* /////////////////////Methods///////////////////////// */

    /**
     * Add task to fixed tasks.
     *
     * @param task the task
     */
    public void addTaskToFixedTasks(Task task)
    {
        if(!this.isDefaultCB)
        {
            this.assignedTasks.add(task);
        }
    }

    /**
     * Add task to fixed tasks.
     *
     * @param task the task
     */
    public void addTaskToSoftFixedTasks(Task task)
    {
        this.temporallyAssignedTasks.add(task);
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
        if(!isDefaultCB)
        {
            int totalFreeTimeOnSlot = this.endTimeHour - this.startTimeHour;

            for (int i = 0; i < this.assignedTasks.size(); i++) {
                totalFreeTimeOnSlot = totalFreeTimeOnSlot - assignedTasks.get(i).getDuration();
            }

            return totalFreeTimeOnSlot;
        }
        else
        {
            return 100000;
        }
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
        if(!isDefaultCB)
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
        else
        {
            return true;
        }
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
                '}';
    }





}
