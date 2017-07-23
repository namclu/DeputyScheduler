package com.namclu.android.deputyscheduler.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by namlu on 7/23/2017.
 */

public class ShiftResponse {
    @SerializedName("shifts")
    private List<Shift> mShifts;

    public List<Shift> getShifts() {
        return mShifts;
    }

    public void setShifts(List<Shift> shifts) {
        mShifts = shifts;
    }


}
