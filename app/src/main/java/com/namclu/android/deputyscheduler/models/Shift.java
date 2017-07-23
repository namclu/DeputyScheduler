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
    private String mCompanyName;
    private String mLogoUrl;
    private String mShiftDate;
    private String mStartTime;
    private String mEndTime;
    private String mStartLatitude;
    private String mStartLongitude;
    private String mEndLatitude;
    private String mEndLongitude;
    private String mImage;

    // New shift constructor
    public Shift(String companyName, String shiftDate, String startTime) {
        setCompanyName(companyName);
        setShiftDate(shiftDate);
        setStartTime(startTime);
    }

    /* Getter and Setters */
    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        mLogoUrl = logoUrl;
    }

    public String getShiftDate() {
        return mShiftDate;
    }

    public void setShiftDate(String shiftDate) {
        mShiftDate = shiftDate;
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

