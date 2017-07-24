package com.namclu.android.deputyscheduler.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.namclu.android.deputyscheduler.R;

/**
 * Created by namlu on 7/24/2017.
 *
 * Fragment used to show Date Dialog
 */

public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.date_dialog, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
