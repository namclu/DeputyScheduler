package com.namclu.android.deputyscheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.namclu.android.deputyscheduler.fragments.ShiftListFragment;

public class ShiftListActivity extends AppCompatActivity {

    private static final String TAG = ShiftListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_list);

        LinearLayout fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);
        ShiftListFragment shiftListFragment = ShiftListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shiftListFragment).commit();
    }
}
