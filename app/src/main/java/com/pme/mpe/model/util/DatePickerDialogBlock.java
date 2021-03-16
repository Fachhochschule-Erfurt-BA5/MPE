package com.pme.mpe.model.util;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pme.mpe.activities.BlockCategoryActivity.NewBlockCategoryActivity;

import java.util.Calendar;

public class DatePickerDialogBlock extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (NewBlockCategoryActivity)getActivity(),year, month, day);
    }
}
