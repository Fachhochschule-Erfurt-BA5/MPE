package com.pme.mpe.activities.TaskActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pme.mpe.R;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivity;
import com.pme.mpe.activities.CategoryActivity.NewCategoryActivityViewModel;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.tasks.exceptions.TaskFixException;
import com.pme.mpe.model.tasks.exceptions.TimeException;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.storage.repository.exceptions.ObjectNotFoundException;
import com.pme.mpe.ui.block.BlockFragment;
import com.pme.mpe.ui.category.CategoryFragment;
import com.pme.mpe.ui.category.CategoryViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private TextView taskDeadline; //Deadline
    private TextView taskTime; //only when fixed?
    private TextView taskDate;
    private TextView taskDuration;
    private Button taskSave;
    private Spinner categorySpinner;
    private NewTaskActivityViewModel newTaskActivityViewModel;
    private CategoryViewModel categoryViewModel;
    protected String categoryName;
    private ArrayList<String> categoryList;
    private TasksPackageRepository tasksPackageRepository;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private LocalDate localDateTask;
    private int duration;
    int categoryID;

    private ArrayList<String> categoriesList;

    private ImageButton predefinedColor;
    private SwitchCompat isFixed; //how to save it?
    private Button saveTaskBtn;

    private TextView taskHex;
    private ColorSelector colorSelector;
    private ImageButton taskColor;
    String letterColor ="#000000";
    String cardColor = "#54aadb";

    private CollapsingToolbarLayout editTask;
    private NewTaskActivity newTaskActivity;
    private AlertDialog colorPickDialog;

    int taskID;


   /* private final View.OnClickListener saveTaskClickListener = v -> {

        if (v.getId() == R.id.save_task) {
            Intent taskBlockIntent = getIntent();
            Bundle extras = taskBlockIntent.getExtras();
            int taskID = extras.getInt("taskID");
            categoryID = (int) tasksPackageRepository.getCategoryWithName(categoryName).getCategoryId();

            Task newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(),  categoryID, taskDuration, localDateTask);
            newTaskActivityViewModel.saveTasks(newTask);
            Intent taskIntent = new Intent(getApplicationContext(), BlockFragment.class); //change to TaskFragment
            startActivity(taskIntent);
        }

    };*/

    private final View.OnClickListener taskColorClickListener = v -> {

        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog() {
            @Override
            public void colorPicked(int red, int green, int blue, int textColor) {
                taskColor.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(red,green,blue)));
                CharSequence hexColor_argb = Integer.toHexString(Color.rgb(red,green,blue));
                cardColor = "#" + hexColor_argb.charAt(2) + hexColor_argb.charAt(3) + hexColor_argb.charAt(4) + hexColor_argb.charAt(5) + hexColor_argb.charAt(6) + hexColor_argb.charAt(7);
                taskHex.setText(cardColor);
                CharSequence hexColor_argb1 = Integer.toHexString(textColor);
                letterColor = "#" + hexColor_argb1.charAt(2) + hexColor_argb1.charAt(3) + hexColor_argb1.charAt(4) + hexColor_argb1.charAt(5) + hexColor_argb1.charAt(6) + hexColor_argb1.charAt(7);
            }
        };
        colorSelector = new ColorSelector();
        colorSelector.showColorSelectorDialog(this,colorSelectorDialog);
    };

    private final View.OnClickListener timePickerDialog = v -> {
        DialogFragment timePicker = new com.pme.mpe.model.util.TimePickerDialogBlock();
        timePicker.show(getSupportFragmentManager(), "Time Picker");
    };


    private final View.OnClickListener datePickerDialog = v -> {
        DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialogBlock();
        datePicker.show(getSupportFragmentManager(), "Date Picker");
    };

    private final View.OnClickListener saveTaskClickListener = v -> {

        if (v.getId() == R.id.save_task) {
            Intent tasIntent = getIntent();
            Bundle extras = tasIntent.getExtras();
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
            Intent taskIntent = new Intent(getApplicationContext(), BlockFragment.class); //TODO: change to TaskFragment?
            startActivity(taskIntent);
        }

    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTaskActivityViewModel = new ViewModelProvider(this).get(NewTaskActivityViewModel.class);
        taskName = findViewById(R.id.task_name_input);
        taskDescription = findViewById(R.id.task_descrip_input);
        taskDate = findViewById(R.id.task_date_select);
        taskDuration = findViewById(R.id.task_duration_select);
        categorySpinner = findViewById(R.id.task_category_spinner);
        // Switch (fixed?)
        taskHex = findViewById(R.id.task_color_output);
        taskColor = findViewById(R.id.task_color_btn);
        taskSave = findViewById(R.id.save_task);
        Intent taskIntent = getIntent();
        Bundle extras = taskIntent.getExtras();


        String tasName = extras.getString("taskName");
        String tasDescr = extras.getString("taskDescription");
        int taskTimeExtra = extras.getInt("taskTime");
        LocalDate LocalDateBlock = (LocalDate) extras.getSerializable("localDateTask");
        int taskIDExtra = extras.getInt("taskID");
        String colorText = extras.getString("taskColorText");
        String colorBtn = extras.getString("taskColor");

        taskName.setText(tasName);
        taskDescription.setText(tasDescr);
        localDateTask = LocalDateBlock;
        taskColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorBtn)));
        taskID = taskIDExtra;
        letterColor = colorText;
        cardColor = colorBtn;
        taskHex.setText(colorBtn);

        editTask.setTitle("Edit Task");

        taskSave.setOnClickListener(this.saveTaskClickListener);
        taskColor.setOnClickListener(this.taskColorClickListener);

    }



}
