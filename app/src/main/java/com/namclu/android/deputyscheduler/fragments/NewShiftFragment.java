package com.namclu.android.deputyscheduler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namclu.android.deputyscheduler.R;

/**
 * Created by namlu on 7/23/2017.
 */

public class NewShiftFragment extends Fragment {

    public static NewShiftFragment newInstance() {

        return new NewShiftFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shift_list, container, false);
    }
}
