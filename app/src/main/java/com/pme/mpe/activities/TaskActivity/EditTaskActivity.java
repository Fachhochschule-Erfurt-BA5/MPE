package com.pme.mpe.activities.TaskActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pme.mpe.R;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivity;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivityViewModel;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.storage.repository.exceptions.ObjectNotFoundException;
import com.pme.mpe.ui.category.CategoryFragment;

import java.time.LocalDate;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

    private CollapsingToolbarLayout editTask;
    private EditText taskName;
    private EditText taskDescription;
    private EditText taskDuration;
    private NewTaskActivityViewModel newTaskActivityViewModel;
    private NewTaskActivity newTaskActivity;
    private TextView taskDeadline;

    int taskID;


    private final View.OnClickListener saveCategoryClickListener = v -> {

        if (v.getId() == R.id.save_task) {
            Intent catIntent = getIntent();
            Bundle extras = catIntent.getExtras();
            taskID = extras.getInt("taskID");
            try {
                newTaskActivityViewModel.updateTask(taskID,taskName.getText().toString(),
                        taskDescription.getText().toString(),taskDuration.getInputType(),
                        LocalDate.parse((CharSequence) taskDeadline));
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            } catch (TaskFixException e) {
                e.printStackTrace();
            } catch (TimeException e) {
                e.printStackTrace();
            }
            Intent categoryIntent = new Intent(getApplicationContext(), CategoryFragment.class);
            startActivity(categoryIntent);
        }

    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTaskActivityViewModel = new ViewModelProvider(this).get(NewTaskActivityViewModel.class);
        Intent taskIntent = getIntent();
        Bundle extras = taskIntent.getExtras();
        taskName = findViewById(R.id.task_name_input);
        taskDescription = findViewById(R.id.task_item_descrip);
        taskDuration = findViewById(R.id.task_item_time);
        taskDeadline = findViewById(R.id.task_date_select);
        Button saveTask = findViewById(R.id.save_task);
        editTask = findViewById(R.id.toolbar_layout_task);

        String tasName = extras.getString("TaskName");

        taskName.setText(tasName);
        editTask.setTitle("Edit Task");

        saveTask.setOnClickListener(this.saveCategoryClickListener);

    }



}
