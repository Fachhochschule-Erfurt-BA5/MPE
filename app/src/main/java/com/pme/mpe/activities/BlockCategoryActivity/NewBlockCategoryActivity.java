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

import com.pme.mpe.MainActivity;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.Category;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.category.CategoryViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class NewBlockCategoryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText blockName;
    private TextView blockDate;
    private TextView blockStart;
    private TextView blockFinish;
    private Button blockSave;
    private Spinner categorySpinner;
    private NewBlockActivityViewModel newBlockActivityViewModel;
    private CategoryViewModel categoryViewModel;
    protected String categoryName = "";
    private ArrayList<String> categoriesList = new ArrayList<String>();
    private TasksPackageRepository tasksPackageRepository;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private LocalDate localDateCategoryBlock;
    private int start;
    private int finish;
    private int flag = 0;
    private int flagD = 0;


    private final View.OnClickListener saveBlockClickListener = v -> {

        if (v.getId() == R.id.block_save_btn) {

            int categoryID = (int) newBlockActivityViewModel.nameToIDCategory(categoryName).getCategoryId();
            CategoryBlock newCategoryBlock = new CategoryBlock(blockName.getText().toString(), categoryID, localDateCategoryBlock, start, finish);
            newBlockActivityViewModel.saveBlock(newCategoryBlock);
            Intent blockIntent = new Intent(getApplicationContext(), MainActivity.class);
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

        blockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 0);
                flag = 1;
                DialogFragment timePicker = new com.pme.mpe.model.util.TimePickerDialogBlock();
                timePicker.setArguments(dialogBundle);
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });
        blockFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 0);
                flag = 2;
                DialogFragment timePicker = new com.pme.mpe.model.util.TimePickerDialogBlock();
                timePicker.setArguments(dialogBundle);
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        blockDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 0);
                DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialogBlock();
                datePicker.setArguments(dialogBundle);
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        blockSave.setOnClickListener(this.saveBlockClickListener);


    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        TimeZone tz = c.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        localDateCategoryBlock = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();
        //String dateChosen = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        int stringMonth = month + 1;
        String dateChosen = dayOfMonth + "/" + stringMonth + "/" + year;
        blockDate.setText(dateChosen);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        if (flag == 1) {
            flag = 0;
            start = hourOfDay;
            String timeChosen = hourOfDay + ":00";
            blockStart.setText(timeChosen);
        }
        if (flag == 2) {
            flag = 0;
            finish = hourOfDay;
            String timeChosen = hourOfDay + ":00";
            blockFinish.setText(timeChosen);

        }
    }
}
