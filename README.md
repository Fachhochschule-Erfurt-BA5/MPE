# ToDo Android Application

Android application developted for the class "Programmierung Mobile Endgeräte".

Made by:
- Ahmad Abo Louha
- Benito Grauel
- Hamza Harti
- Alejandro Restrepo Klinge

## Concept

This app should help users to keep better track of tasks and help them organize them in an optimal way. 

A user may have various categories (School, Freetime, Work ...). For each given category the user may define slots (Category Blocks), in which he wants to invest his/her time in the given category. 

After having created a category, the user may create specific tasks for this category. A task has always a duration (1/2/3... Hours) and a deadline (When should this task be done). There are two types of tasks: Fixed tasks and non-fixed tasks.

- Fixed Tasks: They are connected with a Category Block (Time Slot on a given day) and they are created from a specific Category Block. A task that should be done on a specific date and specific time. A good example of it is an appointment. 
- Non-Fixed Tasks: They only have a deadline and a duration. Our program tries to find the best fitting Category Block for each non-fixed task. A good example of it may be summiting homework. (It doesn't matter when the user does it, but he plans to do it for a given duration and should be done before a given date, when does he do it, is not relevant).

Each time the user wants to see his schedule, the non-fixed tasks would be assigned to the best fitting category block. If the user adds new non-fixed tasks, his/her schedule is going to be updated.

### Example: 

**User A** created the category **University**. **User A** wants to invest some time doing uni stuff on a given date, so he/she creates **Category Block 1** which is going to take place on a given date and defined start and end hours. **Category Block 1** is going to take place on the *30-03-2021* between *11:00* and *14:00*. 

On that day and at that time **User A** is going to be doing some task/s from the category **University**. He/She has to make the finishing touches on a project with the deadline *01-04-2021*. He/she created a new non-fixed task **End-Up-Project Task** with the duration of 2 hours and the deadline *01-04-2021*. This task should be done before its deadline. When exactly is not important.

For the given situation the **End-Up-Project Task** is going to be done on the **Category Block 1**.

**Category Block 1**
| Tasks               |Start Hour| End Hour|
| :-------------------|:--------:| :------:|
| End-Up-Project Task | 11:00    | 13:00   |
| *Unnasign Time*     | 13:00    | 14:00   |

After having created the **Category Block 1** and the **End-Up-Project Task** he/she finds out, that he has an appointment with a Professor on the *30-03-2021* at *11:00* for 1 hour. 

**User 1** creates a fixed task **Fixed-Task 1** on the **Category Block 1** with the duration of 1 hour starting at *11:00*. This implies that the Task **End-Up-Project Task** has to change its starting and end hours. With the current situation **Category Block 1** looks like this:

**Category Block 1**
| Tasks               |Start Hour| End Hour|
| :-------------------|:--------:| :------:|
| Fixed Task 1        | 11:00    | 12:00   |
| End-Up-Project Task | 12:00    | 14:00   |

**User 1** decides to do another Category Block ( **Category Block 2** ) for the category **University** on the *25-03-2021* between *14:00* and *16:00*. The schedule is going to be updated and it would look something like this:

**Category Block 2** (25-03-2021)
| Tasks               |Start Hour| End Hour|
| :-------------------|:--------:| :------:|
| End-Up-Project Task | 14:00    | 16:00   |


**Category Block 1** (30-03-2021)
| Tasks               |Start Hour| End Hour|
| :-------------------|:--------:| :------:|
| Fixed Task 1        | 11:00    | 12:00   |
| *Unnasign Time*     | 12:00    | 14:00   |


## How did we work

We divided our team into two parts: Frontend and Backend.

- Front-End: Hamza + Benito.
- Back-End: Ahmad + Alejandro.

//TODO: HIER ERGÄNZEN (wöchentliche meeting uzw.)

## Documentation

//TODO: HIER ERGÄNZEN (Wie funktioniert das ganze, Struktur, uzw...)
### Activity
#### Block-Categotry
###### EditBlockCategory
#### Category
#### Task
#### Setting
### Core
#### Main Application
in the [Main Class](https://github.com/Fachhochschule-Erfurt-BA5/PME/blob/main/app/src/main/java/com/pme/mpe/core/MainApplication.java) The Key Value Store checks whether a user is already logged in or this is the first time the app has been used. Based on the result, the user is forwarded either to the login page or the main page of the app.
### Model
#### Tasks Package
##### Category
has the information about a category (name, ID, color ...) as well as a list of CategoryBlocks and a list of tasks and their functionality
##### Category-Block
has the information about a category block (name, ID, color ...) as well as a list of tasks and a reference to the associated category and its functionality
##### Task
hat die Information über ein Task (Name, description, duration, deadline) sowie ein Category_id, außerdem hat die infos, ob ein Task fixd oder nicht,
#### User
#### util
#### Relation
### Storage
#### DAOs
#### Database
#### Repositories
#### Key Value Store
### UI
### Resources

#### 



## License
[MIT](https://choosealicense.com/licenses/mit/)
