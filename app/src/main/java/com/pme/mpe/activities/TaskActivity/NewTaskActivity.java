package com.pme.mpe.activities.TaskActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.MainActivity;
import com.pme.mpe.R;
import com.pme.mpe.activities.BlockCategoryActivity.NewBlockActivityViewModel;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.model.util.ColorSelector;
import com.pme.mpe.model.util.NumberPickerDialog;
import com.pme.mpe.storage.dao.ColorSelectorDialog;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.block.BlockFragment;
import com.pme.mpe.ui.block.BlockViewModel;
import com.pme.mpe.ui.category.CategoryViewModel;

import java.security.acl.Owner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.widget.TimePicker;
import android.widget.Toast;


public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {

    private EditText taskName;
    private EditText taskDescription;
    private TextView taskDeadline; //Deadline
    private TextView taskTime; //only when fixed?
    private TextView taskDate;
    private TextView blockTextOutput;
    private TextView taskDuration;
    private Button taskSave;
    private Spinner categorySpinner;
    private Spinner blockSpinner;
    private NewTaskActivityViewModel newTaskActivityViewModel;
    private CategoryViewModel categoryViewModel;
    private BlockViewModel blockViewModel;
    protected String categoryName;
    protected String blockName;
    private ArrayList<String> categoryList;
    private TasksPackageRepository tasksPackageRepository;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private LocalDate localDateTask;
    private int duration;
    int categoryID;
    private LinearLayout blockSpinnerLayout;
    private LinearLayout colorPickerTask;
    private NewBlockActivityViewModel newBlockActivityViewModel;

    private ArrayList<String> categoriesList = new ArrayList<String>();
    private ArrayList<String> blocksList = new ArrayList<String>();

    private ImageButton predefinedColor;
    private SwitchCompat isFixed; //how to save it?
    private Button saveTaskBtn;
    private Boolean isCheckedTask = true;

    private TextView taskHex;
    private ColorSelector colorSelector;
    private ImageButton taskColor;
    String letterColor = "#000000";
    String cardColor = "#54aadb";
    private int flagTime = 0;
    private int flagDate = 0;

    void Init_Color_Picker() {
        String[] colors = getResources().getStringArray(R.array.color_picker);
        int iMax = colors.length;
        for (int iColor = 0; iColor < iMax; iColor++) {
            String color = colors[iColor];
            Button btn = new Button(this);
            btn.setBackgroundColor(Color.parseColor(color));
            LinearLayout panel_color = findViewById(R.id.task_color_picker);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn=(Button) v;
                    ColorDrawable viewColor = (ColorDrawable) btn.getBackground();
                    int colorId = viewColor.getColor();
                    String sColor=String.valueOf(colorId);
                }
            });
            panel_color.addView(btn);
        }
    }


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
<<<<<<< HEAD
            int categoryID = (int) newTaskActivityViewModel.nameToIDCategory(categoryName).getCategoryId();
            Task newTask;
            if (!isCheckedTask) {
                newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(), categoryID, duration, localDateTask);
                newTaskActivityViewModel.saveTasks(newTask);
            }
            if (isCheckedTask) {
                CategoryBlock catyBlock = newTaskActivityViewModel.getBlockWithCategoryIDAndName(categoryID, blockName);
                newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(), categoryID, duration, localDateTask, catyBlock.getCatBlockId(), catyBlock);
                newTaskActivityViewModel.saveTasks(newTask);
            }


            //Intent taskIntent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(taskIntent);
=======
            categoryID = (int) tasksPackageRepository.getCategoryWithName(categoryName).getCategoryId();
            Task newTask = new Task(taskName.getText().toString(), taskDescription.getText().toString(), categoryID, duration, localDateTask, colorSelector.toString());
            newTaskActivityViewModel.saveTasks(newTask);
            Intent taskIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(taskIntent);
>>>>>>> 7b73582e6d19431211e533f1f8a4d5cf0687b8d7
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
        taskDuration = findViewById(R.id.task_duration_select);
        isFixed = findViewById(R.id.switch_fixed);// Switch (fixed?)
        taskHex = findViewById(R.id.task_color_output);
        taskColor = findViewById(R.id.task_color_btn);
        taskSave = findViewById(R.id.save_task);
        categorySpinner = findViewById(R.id.task_category_spinner);
        blockSpinner = findViewById(R.id.task_block_category_spinner);
        blockSpinnerLayout = findViewById(R.id.task_block_category_layout);
        blockTextOutput = findViewById(R.id.task_block_category_output);
        colorPickerTask = findViewById(R.id.task_color_picker_layout);
        Init_Color_Picker();

        ArrayAdapter<String> adapterSpinnerCategories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesList);
        adapterSpinnerCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapterSpinnerCategories);

        ArrayAdapter<String> adapterSpinnerBlocks = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, blocksList);
        adapterSpinnerCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blockSpinner.setAdapter(adapterSpinnerBlocks);

        blockViewModel = new ViewModelProvider(this).get(BlockViewModel.class);
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
                int catBlockID = (int) newTaskActivityViewModel.nameToIDCategory(categoryName).getCategoryId();

                blockViewModel.getBlocks().observeForever(new Observer<List<CategoryBlock>>() {
                    @Override
                    public void onChanged(List<CategoryBlock> blocks) {

                        for (CategoryBlock block : blocks) {
                            if (block.getCB_CategoryId() == catBlockID) {
                                blocksList.add(block.getTitle());
                            }

                        }
                        if (blocksList.size() == 0) {
                            blockTextOutput.setText("no Block was found");
                            blockSpinner.setEnabled(false);

                        }
                        if (blocksList.size() != 0) {
                            blockTextOutput.setText(R.string.block_prompt);
                            blockSpinner.setEnabled(true);

                        }
                        adapterSpinnerBlocks.notifyDataSetChanged();
                    }
                });

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        blockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blockName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        isFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    blockSpinnerLayout.setAlpha(0);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getApplication().getResources().getDisplayMetrics()));
                    layoutParams.setMargins(0, 0, 0, 0);

                    blockSpinnerLayout.setLayoutParams(layoutParams);
                    blockSpinnerLayout.requestLayout();
                    isCheckedTask = false;
                }
                if (isChecked) {
                    blockSpinnerLayout.setAlpha(1);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getApplication().getResources().getDisplayMetrics());
                    int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getApplication().getResources().getDisplayMetrics());
                    int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getApplication().getResources().getDisplayMetrics());
                    int bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getApplication().getResources().getDisplayMetrics());
                    layoutParams.setMargins(left, top, right, bottom);
                    blockSpinnerLayout.setLayoutParams(layoutParams);
                    blockSpinnerLayout.requestLayout();
                    isCheckedTask = true;
                }
            }
        });


        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 2);
                DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialogBlock();
                datePicker.setArguments(dialogBundle);
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        taskDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerDialog numberPicker = new com.pme.mpe.model.util.NumberPickerDialog();
                numberPicker.setValueChangeListener(NewTaskActivity.this);
                numberPicker.show(getSupportFragmentManager(), "Number Picker");
            }
        });


        taskSave.setOnClickListener(this.saveTaskClickListener);

        taskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerTask.setAlpha(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                colorPickerTask.setLayoutParams(layoutParams);
                colorPickerTask.requestLayout();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        TimeZone tz = c.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        localDateTask = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();
        int stringMonth = month + 1;
        String dateChosen = dayOfMonth + "/" + stringMonth + "/" + year;
        taskDate.setText(dateChosen);

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        duration = picker.getValue();
        String durationChosen = duration + " h";
        taskDuration.setText(durationChosen);
    }
}







