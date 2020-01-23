package com.purplepath.purplepath.document.getfilemodels;

import java.io.Serializable;

/**
 * Created by Suresh on 04/09/17.
 */

public class Document_count implements Serializable {

    private String digital_version;

    private String count;

    public String getDigital_version ()
    {
        return digital_version;
    }

    public void setDigital_version (String digital_version)
    {
        this.digital_version = digital_version;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [digital_version = "+digital_version+", count = "+count+"]";
    }
}
