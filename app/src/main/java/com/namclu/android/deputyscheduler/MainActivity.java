package com.namclu.android.deputyscheduler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.namclu.android.deputyscheduler.fragments.NewShiftFragment;
import com.namclu.android.deputyscheduler.fragments.ShiftListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);

        if (savedInstanceState == null) {
            ShiftListFragment shiftListFragment = ShiftListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shiftListFragment).commit();
        }

        fab = (FloatingActionButton) findViewById(R.id.float_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewShiftFragment newShiftFragment = NewShiftFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, newShiftFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void showFloatingButton(boolean showButton) {
        fab.setVisibility(showButton ? View.VISIBLE : View.GONE);
    }


}
