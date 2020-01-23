package com.purplepath.purplepath.propertyinsurance.propertymodel;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/21/17.
 */

public class House_hold_content implements Serializable {

    private ArrayList<Assets> assets;

    public ArrayList<Assets> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<Assets> assets) {
        this.assets = assets;
    }

    private String overall_value;

    public String getOverall_value ()
    {
        return overall_value;
    }

    public void setOverall_value (String overall_value)
    {
        this.overall_value = overall_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [assets = "+assets+", overall_value = "+overall_value+"]";
    }
}


