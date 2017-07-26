package com.namclu.android.deputyscheduler.models;

/**
 * Created by namlu on 7/24/2017.
 *
 * Model for the body to post to shift/start or shift/end
 */

public class ShiftPostBody {
    private String mTime;
    private String mLatitude;
    private String mLongitude;

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        this.mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        this.mLongitude = longitude;
    }
}
