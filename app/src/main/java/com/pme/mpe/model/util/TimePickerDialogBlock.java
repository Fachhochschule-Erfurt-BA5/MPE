package com.pme.mpe.model.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;


import com.pme.mpe.activities.BlockCategoryActivity.EditBlockCategoryActivity;
import com.pme.mpe.activities.BlockCategoryActivity.NewBlockCategoryActivity;
import com.pme.mpe.activities.TaskActivity.NewTaskActivity;

import java.util.Calendar;

public class TimePickerDialogBlock extends DialogFragment {
    int dialogID = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dialogID = this.getArguments().getInt("DialogID");
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;

        switch (dialogID) {
            case 0:
                timePickerDialog = new TimePickerDialog(getActivity(), (NewBlockCategoryActivity) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                break;
            case 1:
                timePickerDialog = new TimePickerDialog(getActivity(), (EditBlockCategoryActivity) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                break;
            case 2:
                timePickerDialog = new TimePickerDialog(getActivity(), (NewTaskActivity) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dialogID);
        }
        return timePickerDialog;
    }
}

