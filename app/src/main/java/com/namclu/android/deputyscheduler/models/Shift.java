package com.namclu.android.deputyscheduler.models;

/**
 * Created by namlu on 7/23/2017.
 *
 * Shift represents a single work shift which includes
 * company name, date, start/end time, start/end location, and a location image
 */

public class Shift {

    /*
    * Todo:add variable comments
    *
    * */
    private int mId;
    private String mStartTime;
    private String mEndTime;
    private String mStartDate;
    private String mStartLatitude;
    private String mStartLongitude;
    private String mEndLatitude;
    private String mEndLongitude;
    private String mImage;

    // New shift constructor
    public Shift(int id, String shiftDate, String startTime) {
        setId(id);
        setStartDate(shiftDate);
        setStartTime(startTime);
    }

    /* Getter and Setters */

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getStartLatitude() {
        return mStartLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        mStartLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return mStartLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        mStartLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return mEndLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        mEndLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return mEndLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        mEndLongitude = endLongitude;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}

