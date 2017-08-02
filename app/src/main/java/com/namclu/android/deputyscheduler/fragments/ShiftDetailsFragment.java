package com.namclu.android.deputyscheduler.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.namclu.android.deputyscheduler.R;

import java.util.Calendar;

/**
 * Created by namlu on 7/31/2017.
 *
 * Displays the details of a Shift and allows user to submit an end time if the
 * shift is in progress.
 */

public class ShiftDetailsFragment extends Fragment implements
        TimePickerDialog.OnTimeSetListener {

    private static final String TAG = ShiftDetailsFragment.class.getSimpleName();
    private static final String DIALOG_TIME = "DialogTime";


    // Global variables
    private TextView mTextDatePicker;
    private TextView mTextEndTimePicker;
    private Calendar mCalendar;
    private Button mSaveButton;
    private Button mCancelButton;

    public static ShiftDetailsFragment newInstance() {

        return new ShiftDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_details, container, false);

        // Find view ids
        mTextDatePicker = (TextView) view.findViewById(R.id.text_shift_date_picker);
        mTextEndTimePicker = (TextView) view.findViewById(R.id.text_end_time_picker);
        mSaveButton = (Button) view.findViewById(R.id.button_save);
        mCancelButton = (Button) view.findViewById(R.id.button_cancel);

        mCalendar = Calendar.getInstance();

        mTextEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getChildFragmentManager();
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mCalendar);
                timePickerFragment.show(fragmentManager, DIALOG_TIME);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        return view;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        mCalendar.set(Calendar.HOUR, hours);
        mCalendar.set(Calendar.MINUTE, minutes);

        mTextEndTimePicker.setText(String.format("%02d:%02d", hours, minutes));
    }

    private void closeFragment() {
        getActivity()
                .getSupportFragmentManager()
                .popBackStack();
    }

}