package com.namclu.android.deputyscheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namclu.android.deputyscheduler.R;
import com.namclu.android.deputyscheduler.adapters.ShiftAdapter;
import com.namclu.android.deputyscheduler.models.Shift;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namlu on 7/23/2017.
 */

public class ShiftListFragment extends Fragment {
    private ArrayList<Shift> mShifts;
    private ShiftAdapter mShiftAdapter;

    public static ShiftListFragment newInstance() {
        return new ShiftListFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Initialize variables
        mShifts = new ArrayList<>();
        mShiftAdapter = new ShiftAdapter(mShifts);

        // Find references
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        // RecyclerView stuff
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Dummy data
        List<Shift> dummyData = new ArrayList<>();
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        dummyData.add(new Shift("Deputy", "Sun 23 July", "09:00"));
        mShifts.addAll(dummyData);

        recyclerView.setAdapter(mShiftAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shift_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
