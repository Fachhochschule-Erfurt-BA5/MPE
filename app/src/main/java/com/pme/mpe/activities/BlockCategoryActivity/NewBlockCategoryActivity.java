package com.pme.mpe.activities.BlockCategoryActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
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

public class NewBlockCategoryActivity extends AppCompatActivity {

    private EditText blockName;
    private TextView blockDate;
    private TextView blockStart;
    private TextView blockFinish;
    private Button blockSave;
    private Spinner categorySpinner;
    private NewBlockActivityViewModel newBlockActivityViewModel;
    private CategoryViewModel categoryViewModel;
    protected String categoryName;
    private ArrayList<String> categoriesList;
    private TasksPackageRepository tasksPackageRepository;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private LocalDate localDateCategoryBlock;
    private int start;
    private int finish;


    private final View.OnClickListener timePickerDialog = v -> {
        DialogFragment timePicker = new com.pme.mpe.model.util.TimePickerDialog();
        timePicker.show(getSupportFragmentManager(), "Time Picker");
    };


    private final View.OnClickListener datePickerDialog = v -> {
        DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialog();
        datePicker.show(getSupportFragmentManager(), "Date Picker");
    };

    private final View.OnClickListener saveBlockClickListener = v -> {

        if (v.getId() == R.id.block_save_btn) {
            int categoryID = (int) tasksPackageRepository.getCategoryWithName(categoryName).getCategoryId();
            CategoryBlock newCategoryBlock = new CategoryBlock(blockName.getText().toString(),categoryID,localDateCategoryBlock,start,finish);
            newBlockActivityViewModel.saveBlock(newCategoryBlock);
            Intent blockIntent = new Intent(getApplicationContext(), BlockFragment.class);
            startActivity(blockIntent);
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_block_category);
        newBlockActivityViewModel = new ViewModelProvider(this).get(NewBlockActivityViewModel.class);
        blockName = findViewById(R.id.block_name_input);
        blockDate = findViewById(R.id.block_date_select);
        blockStart = findViewById(R.id.block_start_select);
        blockFinish = findViewById(R.id.block_finish_select);
        blockSave = findViewById(R.id.block_save_btn);
        categorySpinner = findViewById(R.id.block_category_spinner);

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

        blockStart.setOnClickListener(this.timePickerDialog);
        blockFinish.setOnClickListener(this.timePickerDialog);
        blockDate.setOnClickListener(this.datePickerDialog);
        blockSave.setOnClickListener(this.saveBlockClickListener);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimeZone tz = c.getTimeZone();
                ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
                localDateCategoryBlock = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();

            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                if (view.getId() == R.id.block_start_select) {
                    start = hourOfDay;
                }
                if (view.getId() == R.id.block_finish_select) {
                    finish = hourOfDay;
                }
            }
        };


    }
}
