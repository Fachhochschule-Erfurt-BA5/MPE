package com.pme.mpe.model.util;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pme.mpe.activities.BlockCategoryActivity.EditBlockCategoryActivity;
import com.pme.mpe.activities.BlockCategoryActivity.NewBlockCategoryActivity;
import com.pme.mpe.activities.TaskActivity.EditTaskActivity;
import com.pme.mpe.activities.TaskActivity.NewTaskActivity;
import com.pme.mpe.ui.home.HomeFragment;

import java.util.Calendar;

public class DatePickerDialogBlock extends DialogFragment {
    int dialogID = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dialogID = this.getArguments().getInt("DialogID");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;

        switch (dialogID) {
            case 0:
                datePickerDialog = new DatePickerDialog(getActivity(), (NewBlockCategoryActivity) getActivity(), year, month, day);
                break;
            case 1:
                datePickerDialog = new DatePickerDialog(getActivity(), (EditBlockCategoryActivity) getActivity(), year, month, day);
                break;
            case 2:
                datePickerDialog = new DatePickerDialog(getActivity(), (NewTaskActivity) getActivity(), year, month, day);
                break;
            case 3:
                datePickerDialog = new DatePickerDialog(getActivity(), (EditTaskActivity) getActivity(), year, month, day);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dialogID);
        }
        return datePickerDialog;
    }
}
