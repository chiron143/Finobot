package com.purplepath.purplepath.taxfiling.getvalidateform;

import java.io.Serializable;

/**
 * Created by pravinr on 6/7/18.
 */

public class Upload_array implements Serializable {

    private String form16b_upload;

    private String form16_upload;

    private String form16a_upload;

    public String getForm16b_upload ()
    {
        return form16b_upload;
    }

    public void setForm16b_upload (String form16b_upload)
    {
        this.form16b_upload = form16b_upload;
    }

    public String getForm16_upload ()
    {
        return form16_upload;
    }

    public void setForm16_upload (String form16_upload)
    {
        this.form16_upload = form16_upload;
    }

    public String getForm16a_upload ()
    {
        return form16a_upload;
    }

    public void setForm16a_upload (String form16a_upload)
    {
        this.form16a_upload = form16a_upload;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [form16b_upload = "+form16b_upload+", form16_upload = "+form16_upload+", form16a_upload = "+form16a_upload+"]";
    }
}

