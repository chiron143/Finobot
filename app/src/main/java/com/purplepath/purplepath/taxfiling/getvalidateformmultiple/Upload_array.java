package com.purplepath.purplepath.taxfiling.getvalidateformmultiple;

import java.io.Serializable;

/**
 * Created by pravinr on 7/5/18.
 */

public class Upload_array implements Serializable {

    private String form16a_1_upload;

    private String form16_1_upload;

    private String form16b_1_upload;

    public String getForm16a_1_upload ()
    {
        return form16a_1_upload;
    }

    public void setForm16a_1_upload (String form16a_1_upload)
    {
        this.form16a_1_upload = form16a_1_upload;
    }

    public String getForm16_1_upload ()
    {
        return form16_1_upload;
    }

    public void setForm16_1_upload (String form16_1_upload)
    {
        this.form16_1_upload = form16_1_upload;
    }

    public String getForm16b_1_upload ()
    {
        return form16b_1_upload;
    }

    public void setForm16b_1_upload (String form16b_1_upload)
    {
        this.form16b_1_upload = form16b_1_upload;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [form16a_1_upload = "+form16a_1_upload+", form16_1_upload = "+form16_1_upload+", form16b_1_upload = "+form16b_1_upload+"]";
    }
}

