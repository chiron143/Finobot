package com.purplepath.purplepath.goaltimeline.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 17/11/16.
 */
public class GoalAgeTimeLineModel implements Serializable {

    private int mAge;
    private int mLifeExpectancy;
    private int mYear;

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    private ArrayList<Goal_tmln> mGoalAgeTimeLine;

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public void setmLifeExpectancy(int mLifeExpectancy) {
        this.mLifeExpectancy = mLifeExpectancy;
    }

    public void setmGoalAgeTimeLine(ArrayList<Goal_tmln> mGoalAgeTimeLine) {
        this.mGoalAgeTimeLine = mGoalAgeTimeLine;
    }

    public int getmLifeExpectancy() {
        return mLifeExpectancy;
    }

    public ArrayList<Goal_tmln> getmGoalAgeTimeLine() {
        return mGoalAgeTimeLine;
    }
}
