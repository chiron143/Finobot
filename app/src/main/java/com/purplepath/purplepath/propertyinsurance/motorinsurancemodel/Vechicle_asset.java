package com.purplepath.purplepath.propertyinsurance.motorinsurancemodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/22/17.
 */

public class Vechicle_asset implements Serializable {


    private ArrayList<Assets> assets;

    public ArrayList<Assets> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<Assets> assets) {
        this.assets = assets;
    }

    private String overall_value;

    private String overall_car_count;

    private String overall_bike_count;



    public String getOverall_value ()
    {
        return overall_value;
    }

    public void setOverall_value (String overall_value)
    {
        this.overall_value = overall_value;
    }

    public String getOverall_car_count ()
    {
        return overall_car_count;
    }

    public void setOverall_car_count (String overall_car_count)
    {
        this.overall_car_count = overall_car_count;
    }

    public String getOverall_bike_count ()
    {
        return overall_bike_count;
    }

    public void setOverall_bike_count (String overall_bike_count)
    {
        this.overall_bike_count = overall_bike_count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [assets = "+assets+", overall_value = "+overall_value+", overall_car_count = "+overall_car_count+", overall_bike_count = "+overall_bike_count+"]";
    }
}

