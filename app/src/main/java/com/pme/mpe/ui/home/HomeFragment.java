package com.pme.mpe.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pme.mpe.storage.dao.TasksPackageDao;
import com.pme.mpe.storage.repository.TasksPackageRepository;
import com.pme.mpe.ui.block.BlockAdapter;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;
import com.pme.mpe.ui.block.TaskViewModel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TaskViewModel taskViewModel;
    private FloatingActionButton calendarBtn;
    private TextView calendarDay;
    private TextView calendarMonth;
    private TextView calendarYear;
    private TasksPackageRepository tasksPackageRepository;
    private LinearLayout noBlockHome;
    private LocalDate blockDate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Calendar c = Calendar.getInstance();
        TimeZone tz = c.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        blockDate = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();
        Date cD = c.getTime();
        String cDate = DateFormat.getDateInstance(DateFormat.FULL).format(cD);
        String[] spliteCDate = cDate.split(",");
        calendarDay = root.findViewById(R.id.Day);
        calendarMonth = root.findViewById(R.id.Month);
        calendarYear = root.findViewById(R.id.Year);
        calendarBtn = root.findViewById(R.id.calendar_selector);
        RecyclerView rvBlock = root.findViewById(R.id.recycler_main);
        noBlockHome = root.findViewById(R.id.no_block_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (buildBlockList().size() != 0) {
            BlockAdapter itemAdapter = new BlockAdapter(buildBlockList(), buildTaskList(), taskViewModel, tasksPackageRepository);
            rvBlock.setAdapter(itemAdapter);
            noBlockHome.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getContext().getResources().getDisplayMetrics());
            noBlockHome.requestLayout();
            rvBlock.getLayoutParams().height = LinearLayout.LayoutParams.FILL_PARENT;
            rvBlock.requestLayout();
        }
        if (buildBlockList().size() == 0) {
            rvBlock.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getContext().getResources().getDisplayMetrics());
            rvBlock.requestLayout();
            noBlockHome.getLayoutParams().height = LinearLayout.LayoutParams.FILL_PARENT;
            noBlockHome.requestLayout();

        }
        rvBlock.setLayoutManager(layoutManager);
        calendarDay.setText(spliteCDate[0]);
        calendarMonth.setText(spliteCDate[1]);
        calendarYear.setText(spliteCDate[2]);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 3);
                DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialogBlock();
                datePicker.setArguments(dialogBundle);
                datePicker.show(getFragmentManager(), "Date Picker");*/
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, day);
                        TimeZone tz = c.getTimeZone();
                        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
                        blockDate = LocalDateTime.ofInstant(c.toInstant(), zid).toLocalDate();
                        String cDate1 = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                        String[] spliteCDate = cDate1.split(",");
                        calendarDay.setText(spliteCDate[0]);
                        calendarMonth.setText(spliteCDate[1]);
                        calendarYear.setText(spliteCDate[2]);
                        if (buildBlockList().size() != 0) {
                            BlockAdapter itemAdapter = new BlockAdapter(buildBlockList(), buildTaskList(), taskViewModel, tasksPackageRepository);
                            rvBlock.setAdapter(itemAdapter);
                            noBlockHome.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, view.getContext().getResources().getDisplayMetrics());
                            noBlockHome.requestLayout();
                            rvBlock.getLayoutParams().height = LinearLayout.LayoutParams.FILL_PARENT;
                            rvBlock.requestLayout();
                        }
                        if (buildBlockList().size() == 0) {
                            rvBlock.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, view.getContext().getResources().getDisplayMetrics());
                            rvBlock.requestLayout();
                            noBlockHome.getLayoutParams().height = LinearLayout.LayoutParams.FILL_PARENT;
                            noBlockHome.requestLayout();

                        }

                    }
                }, year, month, day).show();
            }
        });
        return root;
    }

    private List<CategoryBlock> buildBlockList() {
        return homeViewModel.getCategoryBlocks(blockDate);
    }

    private List<Task> buildTaskList() {
        return homeViewModel.getTasks();
    }


}