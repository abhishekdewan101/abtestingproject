package com.example.remoteviews.abtesting.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a.dewan on 10/10/17.
 */

public class ABExperimentModel {

    @SerializedName("isExperimentRunning")
    boolean mIsExperimentRunning;

    @SerializedName("experimentNumber")
    int mExperimentNumber;

    @SerializedName("fileName")
    String mFileName;


    public ABExperimentModel(boolean mIsExperimentRunning, int mExperimentNumber, String mFileName) {
        this.mIsExperimentRunning = mIsExperimentRunning;
        this.mExperimentNumber = mExperimentNumber;
        this.mFileName = mFileName;
    }

    public boolean ismIsExperimentRunning() {
        return mIsExperimentRunning;
    }

    public void setmIsExperimentRunning(boolean mIsExperimentRunning) {
        this.mIsExperimentRunning = mIsExperimentRunning;
    }

    public int getmExperimentNumber() {
        return mExperimentNumber;
    }

    public void setmExperimentNumber(int mExperimentNumber) {
        this.mExperimentNumber = mExperimentNumber;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }
}
