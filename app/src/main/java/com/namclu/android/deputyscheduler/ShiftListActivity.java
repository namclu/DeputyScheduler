package com.namclu.android.deputyscheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.namclu.android.deputyscheduler.adapters.ShiftAdapter;
import com.namclu.android.deputyscheduler.models.Shift;

import java.util.ArrayList;
import java.util.List;

public class ShiftListActivity extends AppCompatActivity {

    private static final String TAG = ShiftListActivity.class.getSimpleName();

    // Global variables
    private List<Shift> mShifts;
    private ShiftAdapter mShiftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_list);

        // Initialize variables
        mShifts = new ArrayList<>();
        mShiftAdapter = new ShiftAdapter(mShifts);

        // Find references
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // RecyclerView stuff
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mShiftAdapter);

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
    }
}
