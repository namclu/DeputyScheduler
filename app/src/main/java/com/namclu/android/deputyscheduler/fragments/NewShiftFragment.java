package com.namclu.android.deputyscheduler.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.namclu.android.deputyscheduler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by namlu on 7/23/2017.
 */

public class NewShiftFragment extends Fragment implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    // Global variables
    private TextView mTextDatePicker;
    private TextView mTextStartTimePicker;
    private TextView mTextEndTimePicker;
    private Calendar mCalendar;

    public static NewShiftFragment newInstance() {

        return new NewShiftFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_shift, container, false);

        // Initialize views
        mTextDatePicker = (TextView) view.findViewById(R.id.text_shift_date_picker);
        mTextStartTimePicker = (TextView) view.findViewById(R.id.text_start_time_picker);
        mTextEndTimePicker = (TextView) view.findViewById(R.id.text_end_time_picker);

        mCalendar = Calendar.getInstance();

        mTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(Calendar.getInstance());
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        mTextStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(Calendar.getInstance());
                timePickerFragment.show(fragmentManager, DIALOG_TIME);
            }
        });

        mTextEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(fragmentManager, DIALOG_TIME);
            }
        });

        return view;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        mCalendar.set(Calendar.HOUR, hours);
        mCalendar.set(Calendar.MINUTE, minutes);

        mTextStartTimePicker.setText(String.format("%02d:%02d", hours, minutes));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mCalendar.set(year, month, day);

        mTextDatePicker.setText(new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(mCalendar.getTime()));
    }
}
