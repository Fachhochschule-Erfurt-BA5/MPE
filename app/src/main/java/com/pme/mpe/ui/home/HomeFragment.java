package com.pme.mpe.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pme.mpe.ui.block.BlockAdapter;
import com.pme.mpe.R;
import com.pme.mpe.model.tasks.CategoryBlock;
import com.pme.mpe.model.tasks.Task;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private HomeViewModel homeViewModel;
    private FloatingActionButton calendarBtn;
    private TextView calendarDay;
    private TextView calendarMonth;
    private TextView calendarYear;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Date c = Calendar.getInstance().getTime();
        String cDate = DateFormat.getDateInstance(DateFormat.FULL).format(c);
        String[] spliteCDate = cDate.split(",");
        calendarDay = root.findViewById(R.id.Day);
        calendarMonth = root.findViewById(R.id.Month);
        calendarYear = root.findViewById(R.id.Year);
        calendarBtn = root.findViewById(R.id.calendar_selector);
        RecyclerView rvBlock = root.findViewById(R.id.recycler_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        BlockAdapter itemAdapter = new BlockAdapter(buildBlockList(),buildTaskList());
        rvBlock.setAdapter(itemAdapter);
        rvBlock.setLayoutManager(layoutManager);
        calendarDay.setText(spliteCDate[0]);
        calendarMonth.setText(spliteCDate[1]);
        calendarYear.setText(spliteCDate[2]);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dialogBundle = new Bundle();
                dialogBundle.putInt("DialogID", 3);
                DialogFragment datePicker = new com.pme.mpe.model.util.DatePickerDialogBlock();
                datePicker.setArguments(dialogBundle);
                assert getFragmentManager() != null;
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        return root;
    }
    private List<CategoryBlock> buildBlockList() {
        return homeViewModel.getCategoryBlocks();
    }

    private List<Task> buildTaskList() {
        return homeViewModel.getTasks();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendarYear.setText(String.valueOf(year));
        calendarMonth.setText(String.valueOf(month));
        calendarDay.setText(String.valueOf(dayOfMonth));
    }

}