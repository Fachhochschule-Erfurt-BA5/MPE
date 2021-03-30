package com.pme.mpe.storage.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pme.mpe.model.relations.CategoryBlockHaveTasks;
import com.pme.mpe.model.relations.CategoryWithCatBlocksAndTasksRelation;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.tasks.exceptions.TaskDeadlineException;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.storage.dao.TasksPackageDao;
import com.pme.mpe.storage.database.ToDoDatabase;
import com.pme.mpe.storage.repository.exceptions.FixedTaskException;
import com.pme.mpe.storage.repository.exceptions.ObjectNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TasksPackageRepository {

    public static final String LOG_TAG = "TasksPackageRepository";


    private final TasksPackageDao tasksPackageDao;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<CategoryBlock>> allCategoryBlocks;

    private static TasksPackageRepository INSTANCE;


//////////////////Singleton Implementation//////////////////

    /**
     * Creates a Repository if none exists.
     * Returns the repository else
     *
     * @param application the application
     * @return the repository
     */
    public static TasksPackageRepository getRepository(Application application) {
        if (INSTANCE == null) {
            synchronized (TasksPackageRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TasksPackageRepository(application);
                }
            }
        }

        return INSTANCE;
    }


//////////////////Constructor//////////////////

    /**
     * Instantiates a new Tasks package repository.
     *
     * @param application the application
     */
    public TasksPackageRepository(Application application) {
        ToDoDatabase db = ToDoDatabase.getDatabase(application);
        this.tasksPackageDao = db.tasksPackageDao();

        this.getCategoryBlocksLiveData();
        this.getCategoriesLiveData();
    }

//////////////////Live Data Map//////////////////

    /**
     * Fetch the categories live data and merge the needed objects.
     * TODO: Filter just the Categories from a given User
     *
     * @return the categories live data
     */
    public LiveData<List<Category>> getCategoriesLiveData() {
        if (this.allCategories == null) {
            this.allCategories = Transformations.map(
                    this.queryLiveData(this.tasksPackageDao::getCategoriesWithCategoryBlocks),
                    input -> input
                            .stream()
                            .map(CategoryWithCatBlocksAndTasksRelation::merge)
                            .collect(Collectors.toList())
            );
        }
        return this.allCategories;
    }

    /**
     * Fetch the category blocks live data and merge the needed objects.
     * TODO: Filter just the Categories Blocks from a given User
     *
     * @return the category blocks live data
     */
    public LiveData<List<CategoryBlock>> getCategoryBlocksLiveData() {
        if (this.allCategoryBlocks == null) {
            this.allCategoryBlocks = Transformations.map(
                    this.queryLiveData(this.tasksPackageDao::getCategoryBlocksWithTasks),
                    input -> input
                            .stream()
                            .map(CategoryBlockHaveTasks::merge)
                            .collect(Collectors.toList())
            );
        }
        return this.allCategoryBlocks;
    }

//////////////////Help Functions//////////////////

    private <T> LiveData<T> queryLiveData(Callable<LiveData<T>> query) {
        try {
            return ToDoDatabase.executeWithReturn(query);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

//////////////////Transactions//////////////////

    //////////////////Get//////////////////
    public List<CategoryBlock> getCategoryBlocksByGivenDay(LocalDate localDate)
    {
        return tasksPackageDao.getCatBlocksByDay(localDate);
    }

    //////////////////Insert//////////////////

    /**
     * Insert a category to the Database.
     * The default Category Block of the just Created Category is going to be saved with this function as well
     *
     * @param category the category
     */
    public void insertCategory(Category category) {
        category.setCreated(LocalDate.now());
        category.setUpdated(category.getCreated());
        category.setVersion(1);

        long catId = 0;

        try {
            catId = ToDoDatabase.executeWithReturn(() -> tasksPackageDao.insertCategory(category));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "Just inserted Category ID: " + catId);

        //Prepare the default Category block to be saved
        CategoryBlock cb = category.getDefaultCategoryBlock();
        cb.setCB_CategoryId(catId);
        cb.setCreated(LocalDate.now());
        cb.setUpdated(cb.getCreated());
        cb.setVersion(1);

        //Save the default category block
        ToDoDatabase.execute(() -> tasksPackageDao.insertCategoryBlock(cb));
    }

    /**
     * Insert a category block.
     * To ensure Security and Consistency on the Foreign Keys use:
     * Category.addCategoryBlock() method on the Category from this Category Block
     * <p>
     * The mentioned method returns the just instantiated CategoryBlock with the correct Foreign Keys,
     * which should be manually added with this function
     *
     * @param categoryBlock the category block
     */

    public void insertCategoryBlock(CategoryBlock categoryBlock) {
        categoryBlock.setCreated(LocalDate.now());
        categoryBlock.setUpdated(categoryBlock.getCreated());
        categoryBlock.setVersion(1);

        ToDoDatabase.execute(() -> tasksPackageDao.insertCategoryBlock(categoryBlock));
    }

    /**
     * Insert either a Fixed Task or a Non-Fixed Task.
     * To ensure Security and Consistency on the Foreign Keys use:
     * Category.createAndAssignTaskToCategory() for instantiating Non-Fixed Task or
     * Category.createdFixedTaskAndAssignToCategoryBlock() for instantiating a Fixed Task
     * <p>
     * These methods return the just added Task with the correct Foreign Keys,
     * which should be manually added with this following function
     *
     * @param task the task
     */

    public void insertTask(Task task)  {
        //The Foreign key from the Category Block should be saved as well if the Task is Fixed
         if (task.isTaskFixed()) {
            task.setT_categoryBlockId(task.getCategoryBlock().getCatBlockId());

        }

        task.setCreated(LocalDate.now());
        task.setUpdated(task.getCreated());
        task.setVersion(1);

        ToDoDatabase.execute(() -> tasksPackageDao.insertTask(task));
    }

    //////////////////Update//////////////////

    /**
     * Update a category.
     *
     * @param categoryID        the category id from the Category to be updated
     * @param newCatName        the new category name
     * @param newCatColor       the new category color
     * @param newCatLetterColor the new category letter color
     * @throws ObjectNotFoundException the object not found exception
     */
    public void updateCategory(long categoryID, String newCatName, String newCatColor, String newCatLetterColor) throws ObjectNotFoundException {

        //Fetch the Category from the Database
        Category category = this.tasksPackageDao.getCategoryWithID(categoryID);

        if (category != null) {
            category.setCategoryName(newCatName);
            category.setColor(newCatColor);
            category.setLetterColor(newCatLetterColor);

            category.setUpdated(LocalDate.now());
            category.setVersion(category.getVersion() + 1);

            ToDoDatabase.execute(() -> tasksPackageDao.updateCategory(category));
        } else {
            throw new ObjectNotFoundException("Category with given ID not found on Database");
        }
    }

    /**
     * Update a category block.
     * Before trying to update a Category Block use the Method:
     * Category.updateStartAndEndTimeFromACategoryBlock() to ensure changes
     * The mentioned Method returns a Category Block
     *
     * @param categoryBlockID  the category block id from the CB to be changed
     * @param newCategoryBlock the new category block the return CB from the method mentioned above
     * @throws ObjectNotFoundException the object not found exception
     * @throws FixedTaskException      the fixed task exception
     */
    public void updateCategoryBlock(long categoryBlockID, CategoryBlock newCategoryBlock) throws ObjectNotFoundException, FixedTaskException {

        //Fetch the Category Block from the Database
        CategoryBlock categoryBlock = this.tasksPackageDao.getCategoryBlockWithID(categoryBlockID);

        if (categoryBlock != null) {
            categoryBlock.setStartTimeHour(newCategoryBlock.getStartTimeHour());
            categoryBlock.setEndTimeHour(newCategoryBlock.getEndTimeHour());
            /**
             * added by Hamza Harti
             * to be reviewed by Backenders
             */
            categoryBlock.setDate(newCategoryBlock.getDate());
            categoryBlock.setTitle(newCategoryBlock.getTitle());
            categoryBlock.setCB_CategoryId(newCategoryBlock.getCB_CategoryId());
            /** end of note*/

            List<Task> tasks = tasksPackageDao.getFixedTasksFromCB(categoryBlockID);

            if (tasks.size() > 0) {
                //Unfix all the fixed tasks
                for (int i = 0; i < tasks.size(); i++) {
                    unfixTask(tasks.get(i));
                }

                int newTimeInSlot = categoryBlock.returnRemainingFreeTimeOnSlot();

                //Try to re-fix the tasks
                for (int i = 0; i < tasks.size(); i++) {

                    if (tasks.get(i).getDuration() <= newTimeInSlot) {
                        fixExistingTaskToCategoryBlock(tasks.get(i), categoryBlock);
                        newTimeInSlot = newTimeInSlot - tasks.get(i).getDuration();
                    } else {
                        throw new FixedTaskException("One or more Tasks were unfixed with the change of time");
                    }
                }
            }

            categoryBlock.setUpdated(LocalDate.now());
            categoryBlock.setVersion(categoryBlock.getVersion() + 1);

            ToDoDatabase.execute(() -> tasksPackageDao.updateCategoryBlock(categoryBlock));
        } else {
            throw new ObjectNotFoundException("Category Block with given ID not found on Database");
        }
    }

    /**
     * Update a task.
     * It takes into account if the Task is Fixed.
     * If the task is Fixed the duration is checked, if it not longer fit on the Category Block, the task is unassign
     * If the Task is Fixed the deadline is checked and may be only changed if it is still on bound of the Category Block
     *
     * @param taskID         the task id
     * @param newName        the new name
     * @param newDescription the new description
     * @param newDuration    the new duration
     * @param deadline       the deadline
     * @throws ObjectNotFoundException the object not found exception
     * @throws TaskFixException        the task fix exception
     * @throws TimeException           the time exception
     */
    public void updateTask(long taskID, String newName, String newDescription, int newDuration, LocalDate deadline) throws ObjectNotFoundException, TaskFixException, TimeException {

        //Fetch the task from the Database
        Task task = this.tasksPackageDao.getTaskWithID(taskID);

        if (task != null) {
            task.setName(newName);
            task.setDescription(newDescription);

            task.setDuration(newDuration);
            task.setDeadline(deadline);

            task.setUpdated(LocalDate.now());
            task.setVersion(task.getVersion() + 1);

            ToDoDatabase.execute(() -> tasksPackageDao.updateTask(task));
        } else {
            throw new ObjectNotFoundException("Task with given ID not found on Database");
        }
    }

    //////////////////Delete//////////////////

    /**
     * Delete Category with its Category Blocks and Tasks.
     *
     * @param category the category
     */
    public void deleteCategory(Category category) {
        // Get the Category Id, which will be deleted
        long categoryId = category.getCategoryId();

        // Fetch all category blocks of this category
        List<CategoryBlock> categoryBlocks = tasksPackageDao.getCategoryBlocksWithCategoryID(categoryId);

        // Fetch all tasks of this category
        List<Task> tasks = tasksPackageDao.getTasksWithCategoryID(categoryId);

        // Delete all tasks (if any)
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                int finalI = i;
                ToDoDatabase.execute(() -> tasksPackageDao.deleteTask(tasks.get(finalI)));
            }
        }

        // Delete all category blocks (if any)
        if (categoryBlocks.size() > 0) {
            for (int i = 0; i < categoryBlocks.size(); i++) {
                int finalI = i;
                ToDoDatabase.execute(() -> tasksPackageDao.deleteCategoryBlock(categoryBlocks.get(finalI)));
            }
        }

        // Delete the Category after deleting all Tasks and CategoryBlocks from it
        ToDoDatabase.execute(() -> tasksPackageDao.deleteCategory(category));
    }

    /**
     * Delete category block
     * If there was fixed tasks on this category block, they would be be unfixed.
     *
     * @param categoryBlock the category block
     */
    public void deleteCategoryBlock(CategoryBlock categoryBlock) {
        // Get the Category block id
        long id = categoryBlock.getCatBlockId();

        // Get all fixed tasks with CatBlockID
        List<Task> fixedTasks = tasksPackageDao.getFixedTasksFromCB(id);

        // Unfix all the fixed tasks (if any)
       if (fixedTasks.size() > 0) {
            for (int i = 0; i < fixedTasks.size(); i++) {
                // Try to unfix the fixed Tasks in this Category Block
                try {
                   fixedTasks.get(i).unfixTaskFromCategoryBlock();



                    unfixTask(fixedTasks.get(i));

                } catch (TaskFixException | FixedTaskException e) {
                    e.getMessage();
                }
            }
        }

        // When it is finish with unfixing all fixed Tasks ==> delete this Category Block
        ToDoDatabase.execute(() -> tasksPackageDao.deleteCategoryBlock(categoryBlock));
    }


    /**
     * Delete a Single Task.
     * We don't have to update the list from fixed tasks on the CB
     * The list of fixed tasks is being generated each time the CB is fetched from DB
     * If the fixed task is deleted it wont be found in the database and therefor not in the list
     *
     * @param task the task
     */
    public void deleteTask(Task task) {
        ToDoDatabase.execute(() -> tasksPackageDao.deleteTask(task));
    }

//////////////////Helper functions//////////////////

    /**
     * Has a category block fixed tasks boolean.
     *
     * @param categoryBlock the category block
     * @return true if any fixed tasks where found for this category block
     */
    public boolean hasACategoryBlockFixedTasks(CategoryBlock categoryBlock) {
        // Get the Category block id
        long id = categoryBlock.getCatBlockId();

        // Fetch all fixed tasks from this category block
        List<Task> fixedTasks = tasksPackageDao.getFixedTasksFromCB(id);

        if (fixedTasks.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //// WICHTIG!!
    // Beim Updaten und löschen von Category block, cheken ob fixed tasks gibt!
    //
    //          if ( hasACategoryBlockFixedTasks (Category Block)
    //            Warning ! CB has fixed tasks
    //            Nutzer eingabe ( fortfahren oder abbrechen )
    //            if ( fortfahren )
    //              Update CB / Delete CB
    //            else if ( abbrechen )
    //              CB so lassen wie es war
    //          else if ( hasACategoryBlockFixedTasks (Category Block) == false )
    //              Update CB / Delete CB


    /**
     * Fix existing task to a category block.
     *
     * @param task          the task
     * @param categoryBlock the category block
     * @throws FixedTaskException the fixed task exception
     */
    public void fixExistingTaskToCategoryBlock(Task task, CategoryBlock categoryBlock) throws FixedTaskException {

        if (task.isTaskFixed()) {
            throw new FixedTaskException("This task is already fixed!");
        } else {
            if (!categoryBlock.isDefaultCB()) {
                if (categoryBlock.isEnoughTimeForATaskAvailable(task)) {
                    task.setTaskFixed(true);
                    task.setT_categoryBlockId(categoryBlock.getCatBlockId());

                    ToDoDatabase.execute(() -> tasksPackageDao.updateTask(task));
                } else {
                    throw new FixedTaskException("Not enough time for that task");
                }
            } else {
                throw new FixedTaskException("Cannot fix task to default CB");
            }

        }
    }

    /**
     * Unfix task from the Category Block.
     *
     * @param task the task
     * @throws FixedTaskException the fixed task exception
     */
    public void unfixTask(Task task) throws FixedTaskException {
        if (task.isTaskFixed()) {
            task.setTaskFixed(false);
            task.setT_categoryBlockId(0);

            ToDoDatabase.execute(() -> tasksPackageDao.updateTask(task));
        } else {
            throw new FixedTaskException("Cannot unfix a not fixed tasks!");
        }

    }

    /*get a category Id using its name (Hamza Harti)*****/
    public Category getCategoryWithName(String categoryName) {
        return tasksPackageDao.getCategoryWithName(categoryName);
    }

    public Category getCategoryWithID(long Id) {
        return tasksPackageDao.getCategoryWithID(Id);

    }

    public CategoryBlock getBlockWithCategoryIDAndName(long catId,String blockName) {
        return tasksPackageDao.getCategoryBlockWithCategoryIDAndName(catId,blockName);
    }

    public List<CategoryBlock> getCategoryBlocks(){
     return tasksPackageDao.getCategoryBlocks();
    }

    public List<Task> getTasks(){
        return tasksPackageDao.getTasks();
    }

}
