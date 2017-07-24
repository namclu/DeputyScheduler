package com.namclu.android.deputyscheduler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namclu.android.deputyscheduler.R;

/**
 * Created by namlu on 7/23/2017.
 */

public class NewShiftFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    // Global variables
    private TextView mTextDatePicker;
    private TextView mTextStartTimePicker;
    private TextView mTextEndTimePicker;

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

        mTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                /*DatePickerFragment datePickerFragment = DatePickerFragment
                        .newInstance();*/
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        mTextStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment timePickerFragment = new TimePickerFragment();
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


}
