package com.namclu.android.deputyscheduler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.namclu.android.deputyscheduler.R;

import java.util.Calendar;

/**
 * Created by namlu on 7/31/2017.
 *
 * Displays the details of a Shift and allows user to submit an end time if the
 * shift is in progress.
 */

public class ShiftDetailsFragment extends Fragment {

    private static final String TAG = ShiftDetailsFragment.class.getSimpleName();

    // Global variables
    private TextView mTextDatePicker;
    private TextView mTextStartTimePicker;
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
        mTextStartTimePicker = (TextView) view.findViewById(R.id.text_start_time_picker);
        mSaveButton = (Button) view.findViewById(R.id.button_save);
        mCancelButton = (Button) view.findViewById(R.id.button_cancel);

        // Initialize views


        return view;
    }
}
