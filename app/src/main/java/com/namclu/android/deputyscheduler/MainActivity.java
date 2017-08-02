package com.namclu.android.deputyscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.namclu.android.deputyscheduler.fragments.NewShiftFragment;
import com.namclu.android.deputyscheduler.fragments.ShiftListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String NEW_SHIFT = "New Shift";

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);

        if (savedInstanceState == null) {
            ShiftListFragment shiftListFragment = ShiftListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shiftListFragment).commit();
        }

        mFab = (FloatingActionButton) findViewById(R.id.float_action_button);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewShiftFragment newShiftFragment = NewShiftFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, newShiftFragment, NEW_SHIFT)
                        .addToBackStack(NEW_SHIFT)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
    }

    public void showFloatingButton(boolean showButton) {
        mFab.setVisibility(showButton ? View.VISIBLE : View.GONE);
    }


}
