package com.pme.mpe.model.util;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;


import com.pme.mpe.activities.BlockCategoryActivity.EditBlockCategoryActivity;
import com.pme.mpe.activities.BlockCategoryActivity.NewBlockCategoryActivity;

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
        if (dialogID != 1) {
            return new TimePickerDialog(getActivity(), (NewBlockCategoryActivity) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        } else {
            return new TimePickerDialog(getActivity(), (EditBlockCategoryActivity) getActivity(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }
    }
}
