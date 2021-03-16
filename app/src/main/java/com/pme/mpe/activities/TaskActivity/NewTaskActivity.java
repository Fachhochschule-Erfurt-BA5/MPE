package com.pme.mpe.activities.TaskActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.block.BlockFragment;
import com.pme.mpe.ui.category.CategoryViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.widget.TimePicker;


public class NewTaskActivity extends AppCompatActivity {

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

    private ArrayList<String> categoriesList = new ArrayList<String>();

    private ImageButton predefinedColor;
    private SwitchCompat isFixed; //how to save it?
    private Button saveTaskBtn;

    private TextView taskHex;
    private ColorSelector colorSelector;
    private ImageButton taskColor;
    String letterColor ="#000000";
    String cardColor = "#54aadb";


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
            categoryID = (int) tasksPackageRepository.getCategoryWithName(categoryName).getCategoryId();
            Task newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(),  categoryID, duration, localDateTask);
            newTaskActivityViewModel.saveTasks(newTask);
            Intent taskIntent = new Intent(getApplicationContext(), BlockFragment.class); //change to TaskFragment
            startActivity(taskIntent);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_task);
        newTaskActivityViewModel = new ViewModelProvider(this).get(NewTaskActivityViewModel.class);
        taskName = findViewById(R.id.task_name_input);
        taskDescription = findViewById(R.id.task_descrip_input);
        taskDate = findViewById(R.id.task_date_select);
        taskTime = findViewById(R.id.task_start_select);
        taskDuration = findViewById(R.id.task_duration_select);
        categorySpinner = findViewById(R.id.task_category_spinner);
        // Switch (fixed?)
        taskHex = findViewById(R.id.task_color_output);
        taskColor = findViewById(R.id.task_color_btn);
        taskSave = findViewById(R.id.save_task);

        ArrayAdapter<String> adapterSpinnerCategories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesList);
        adapterSpinnerCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapterSpinnerCategories);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                for (Category category : categories) {
                    categoriesList.add(category.getCategoryName());
                }
                adapterSpinnerCategories.notifyDataSetChanged();
            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        taskTime.setOnClickListener(this.timePickerDialog);
        taskDate.setOnClickListener(this.datePickerDialog);
        taskSave.setOnClickListener(this.saveTaskClickListener);
        taskColor.setOnClickListener(this.taskColorClickListener);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimeZone tz = c.getTimeZone();
                ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
                localDateTask = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();

            }
        };
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                if (view.getId() == R.id.task_duration_select) {
                    duration = hourOfDay;
                }

            }
        };

    }
}







