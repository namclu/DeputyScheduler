package com.namclu.android.deputyscheduler.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by namlu on 7/24/2017.
 *
 * Fragment used to show Date Dialog
 */

public class DatePickerFragment extends DialogFragment {

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance(@NonNull Calendar calendar) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("calendar", calendar);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        Calendar calendar = (Calendar) getArguments().getSerializable("calendar");
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog.OnDateSetListener parentFragment =
                (DatePickerDialog.OnDateSetListener) getParentFragment();
        return new DatePickerDialog(getActivity(), parentFragment, year, month, day);
    }
}
