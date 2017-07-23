package com.namclu.android.deputyscheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namclu.android.deputyscheduler.BuildConfig;
import com.namclu.android.deputyscheduler.MainActivity;
import com.namclu.android.deputyscheduler.R;
import com.namclu.android.deputyscheduler.adapters.ShiftAdapter;
import com.namclu.android.deputyscheduler.models.Shift;
import com.namclu.android.deputyscheduler.rest.ApiClient;
import com.namclu.android.deputyscheduler.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by namlu on 7/23/2017.
 */

public class ShiftListFragment extends Fragment {

    private final static String USER_SHA = BuildConfig.USER_SHA;
    private final static String DEPUTY_USER_SHA = "Deputy " + USER_SHA;

    // Global variables
    private ArrayList<Shift> mShifts;
    private ShiftAdapter mShiftAdapter;
    private MainActivity mMainActivity;

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
        final RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);

        // RecyclerView stuff
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Shift>> call = apiInterface.getShifts(DEPUTY_USER_SHA);
        call.enqueue(new Callback<List<Shift>>() {
            @Override
            public void onResponse(Call<List<Shift>> call, Response<List<Shift>> response) {
                int statusCode = response.code();
                List<Shift> shifts = response.body();
                recyclerView.setAdapter(new ShiftAdapter(shifts));
            }

            @Override
            public void onFailure(Call<List<Shift>> call, Throwable t) {
                Log.e("ShiftListFragment", t.toString());
            }
        });

        // Dummy data
        /*List<Shift> dummyData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dummyData.add(new Shift(i, "Sun 23 July", "09:00"));
        }

        mShifts.addAll(dummyData);

        recyclerView.setAdapter(mShiftAdapter);*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shift_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainActivity.showFloatingButton(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMainActivity.showFloatingButton(false);
    }
}
