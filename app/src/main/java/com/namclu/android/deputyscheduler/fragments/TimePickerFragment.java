package com.namclu.android.deputyscheduler.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by namlu on 7/24/2017.
 */

public class TimePickerFragment extends DialogFragment {

    public TimePickerFragment() {
    }

    public static TimePickerFragment newInstance(@NonNull Calendar calendar) {
        TimePickerFragment fragment = new TimePickerFragment();
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
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog.OnTimeSetListener parentFragment =
                (TimePickerDialog.OnTimeSetListener) getParentFragment();
        return new TimePickerDialog(getActivity(), parentFragment, hour, minute, true);
    }
}
