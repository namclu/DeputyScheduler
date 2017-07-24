package com.namclu.android.deputyscheduler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.namclu.android.deputyscheduler.R;

/**
 * Created by namlu on 7/23/2017.
 */

public class NewShiftFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";

    // Global variables
    private EditText mEditDate;

    public static NewShiftFragment newInstance() {

        return new NewShiftFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_shift, container, false);

        mEditDate = (EditText) view.findViewById(R.id.edit_shift_date);
        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        return view;
    }


}
