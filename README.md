# ToDo Android Application

Android application developted for the class "Programmierung Mobile
Endgeräte".

Made by:
- Ahmad Abo Louha
- Benito Grauel
- Hamza Harti
- Alejandro Restrepo Klinge

## Concept

This app should help users to keep better track of tasks and help them
organize them in an optimal way.

A user may have various categories (School, Freetime, Work ...). For
each given category the user may define slots (Category Blocks), in
which he wants to invest his/her time in the given category.

After having created a category, the user may create specific tasks for
this category. A task has always a duration (1/2/3... Hours) and a
deadline (When should this task be done). There are two types of tasks:
Fixed tasks and non-fixed tasks.

- Fixed Tasks: They are connected with a Category Block (Time Slot on a
  given day) and they are created from a specific Category Block. A task
  that should be done on a specific date and specific time. A good
  example of it is an appointment.
- Non-Fixed Tasks: They only have a deadline and a duration. Our program
  tries to find the best fitting Category Block for each non-fixed task.
  A good example of it may be summiting homework. (It doesn't matter
  when the user does it, but he plans to do it for a given duration and
  should be done before a given date, when does he do it, is not
  relevant).

Each time the user wants to see his schedule, the non-fixed tasks would
be assigned to the best fitting category block. If the user adds new
non-fixed tasks, his/her schedule is going to be updated.

### Example:

**User A** created the category **University**. **User A** wants to
invest some time doing uni stuff on a given date, so he/she creates
**Category Block 1** which is going to take place on a given date and
defined start and end hours. **Category Block 1** is going to take place
on the *30-03-2021* between *11:00* and *14:00*.

On that day and at that time **User A** is going to be doing some task/s
from the category **University**. He/She has to make the finishing
touches on a project with the deadline *01-04-2021*. He/she created a
new non-fixed task **End-Up-Project Task** with the duration of 2 hours
and the deadline *01-04-2021*. This task should be done before its
deadline. When exactly is not important.

For the given situation the **End-Up-Project Task** is going to be done
on the **Category Block 1**.

**Category Block 1** | Tasks |Start Hour| End Hour| |
:-------------------|:--------:| :------:| | End-Up-Project Task | 11:00
| 13:00 | | *Unnasign Time* | 13:00 | 14:00 |

After having created the **Category Block 1** and the **End-Up-Project
Task** he/she finds out, that he has an appointment with a Professor on
the *30-03-2021* at *11:00* for 1 hour.

**User 1** creates a fixed task **Fixed-Task 1** on the **Category Block
1** with the duration of 1 hour starting at *11:00*. This implies that
the Task **End-Up-Project Task** has to change its starting and end
hours. With the current situation **Category Block 1** looks like this:

**Category Block 1** | Tasks |Start Hour| End Hour| |
:-------------------|:--------:| :------:| | Fixed Task 1 | 11:00 |
12:00 | | End-Up-Project Task | 12:00 | 14:00 |

**User 1** decides to do another Category Block ( **Category Block 2** )
for the category **University** on the *25-03-2021* between *14:00* and
*16:00*. The schedule is going to be updated and it would look something
like this:

**Category Block 2** (25-03-2021) | Tasks |Start Hour| End Hour| |
:-------------------|:--------:| :------:| | End-Up-Project Task | 14:00
| 16:00 |


**Category Block 1** (30-03-2021) | Tasks |Start Hour| End Hour| |
:-------------------|:--------:| :------:| | Fixed Task 1 | 11:00 |
12:00 | | *Unnasign Time* | 12:00 | 14:00 |


## How did we work

We divided our team into two parts: Frontend and Backend.

- Front-End: Hamza + Benito.
- Back-End: Ahmad + Alejandro.

Meetings once or twice a week to discuss and explain finished tasks and
to split new tasks.

## Front End Documentation

### Activity

#### Block-Categotry

EditBlockCategory:
- Logic to edit a present Block-Category

NewBlockCategory:
- Logic to create a new Block-Category

ViewModel:
- has methods to save and update a Block-Category

#### Category

EditCategory:
- Logic to edit a present Category

NewCategory:
- Logic to create a new Category

ViewModel:
- has methods to save and update a Category

#### Task

EditTask:
- Logic to edit a present Task

NewTask:
- Logic to create a new Task

ViewModel:
- has methods to save, update and create a new TaskActivittyViewModel

#### Setting

### UI

Login:
- Username (Email):
- Password :

Home:
- display the users Category-Blocks and the associated tasks with the
  day, month and year
- select a date
- add Task with

Blocks (with add Block):
- show your Category-Blocks
- add a Block from your given Categories with a name, date, start and
  finish time

Categories (with add Category):
- show your Categories
- add a Category with a name and a color

### Resources

## Back End Documentation

### Main Application

Application Class, Inherits from `android.app.Application`. In this
[Class](https://github.com/Fachhochschule-Erfurt-BA5/PME/blob/main/app/src/main/java/com/pme/mpe/core/MainApplication.java)
the Key Value Store checks whether a user is already logged in or if this
is the first time the app is being used. Based on the result, the user
is forwarded either to the login page or the main page of the app.

### Model

Contains the models classes for the application

#### Tasks Package

##### Category

Has the information about a Category (name, ID, color ...) as well as a
list of the CategoryBlocks and a list of Tasks which belong to the Category.
It works as the main control point for adding new Category Block and Tasks and
modifying existing ones. Because a Category knows all the Category Blocks and
Tasks that belong to itself, it was the better place to store this logic.

##### Category-Block

Has the information about a Category Block (name, ID, color ...) as well
as a two lists of tasks and a reference to the associated category. One of
the List takes care of the `Fixed Tasks` and the other takes care of the
`Non-Fixed Tasks`. The difference between the two kinds of tasks is going
to be explained in the paragraph next to this one. This difference is
important at this level because some Tasks change their Category Block
dynamically (Non Fixed Tasks) and the Category Block needs to keep track
of which Tasks may changed and which stay on the same place.

##### Task

Has the information about a Task (name, description, duration, deadline)
as well as a reference to a Category (To which it belongs). A task may be
Fixed or Non-Fixed or even change its behaviour on the Runtime.
* Fixed Task have a Category Block to which they are "Married", that means
that the Task must be done on a given Category Block.
* Non Fixed Task on the other part just need to be done before a Deadline,
the function `Category.autoAssignTasks()` finds the best fitting Category
Block for a Non-Fixed-Task and "Connect" them for the Front End to show them
together.

#### User Package

##### User

Has the information about a User (name, first name, email ...) as well
as a List of the Categories created by a User.

#### Relations

The relationship between the model components is defined in this
package, e.g. CategoryBlockHaveTasks.

##### CategoryBlockHaveTasks

This class contains a reference to the parent class "CategoryBlock"
represented by ID and a list of tasks belonging to the ID. It is also
checks whether the tasks in the list are fixed or soft fixed tasks,
then the tasks are added to the associated attribute list, e.g. if the
Task fix is:

```java
this.categoryBlock.addTaskToFixedTasks (tasks.get (i));
```

##### CategoryWithCatBlockAndTasksRelation

This class contains a reference to the parent class "Category"
represented by ID, it has also a list of CategoryBlocks and a list of
tasks belonging to the Category.

```java
public class CategoryWithCatBlocksAndTasksRelation {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "CB_CategoryId"
    )
    public List<CategoryBlock> categoryBlocks;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "T_categoryID"
    )
    public List<Task> tasks;

    public Category merge()
    {
        this.category.setCategoryBlockList(this.categoryBlocks);
        this.category.setTaskList(this.tasks);

        return this.category;
    }
}
```

##### UserCategoryRelation

This class contains a reference to the parent class "User" represented
by userId, and a list of Category represented the child class belonging
to the Category.

#### Util

Helper classes like PasswordHashing, TimerPickerDialogBlock...

### Storage

#### DAOs

Data Access Objects, which used to define the database interactions.

##### [TaskPackageDao](https://github.com/Fachhochschule-Erfurt-BA5/PME/blob/main/app/src/main/java/com/pme/mpe/storage/dao/TasksPackageDao.java)

Has the required queries to enable access to Tasks, CategoryBlocks and
Categories

```java
 @Insert
    long insertCategory(Category category);
 
 //Live Data Queries
 @Transaction
 @Query("SELECT * FROM Category")
    LiveData<List<CategoryWithCatBlocksAndTasksRelation>> getCategoriesWithCategoryBlocks();
```

##### [UserDao](https://github.com/Fachhochschule-Erfurt-BA5/PME/blob/main/app/src/main/java/com/pme/mpe/storage/dao/UserDao.java)

Required queries to enable access to the User's information

```java
 @Query("SELECT securePassword FROM User WHERE email LIKE :email")
    String getSecuredPasswordWithEMail(String email);
```

#### [Database](https://github.com/Fachhochschule-Erfurt-BA5/PME/tree/main/app/src/main/java/com/pme/mpe/storage/database)

Defines the database configuration and serves as the app's main access
point to the persisted data

#### [Repository](https://github.com/Fachhochschule-Erfurt-BA5/PME/tree/main/app/src/main/java/com/pme/mpe/storage/repository)

Has the logic required to access data sources stored in Database

#### [Key Value Store](https://github.com/Fachhochschule-Erfurt-BA5/PME/blob/main/app/src/main/java/com/pme/mpe/storage/KeyValueStore.java)

Saves the information about registered users, the class has
methods for writing and reading the user data in / from KVS. When the
app is opened, it is checked whether the user is using the app for the
first time, and then a decision is made as to whether the user is
redirected to the login page or to the home page. In the first case, the
username and id are saved in KVS and he does not have to log in again.

## License

[MIT](https://choosealicense.com/licenses/mit/)
