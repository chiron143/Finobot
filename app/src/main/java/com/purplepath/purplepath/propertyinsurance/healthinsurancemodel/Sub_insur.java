package com.purplepath.purplepath.propertyinsurance.healthinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/7/17.
 */

public class Sub_insur implements Serializable {
    private String id;

    private String ins_prod_cat;

    private String status;

    private String created_datetime;

    private String ins_id;

    private String sum_assured;

    private String modified_datetime;

    private String ins_prod_type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIns_prod_cat ()
    {
        return ins_prod_cat;
    }

    public void setIns_prod_cat (String ins_prod_cat)
    {
        this.ins_prod_cat = ins_prod_cat;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getIns_id ()
    {
        return ins_id;
    }

    public void setIns_id (String ins_id)
    {
        this.ins_id = ins_id;
    }

    public String getSum_assured ()
    {
        return sum_assured;
    }

    public void setSum_assured (String sum_assured)
    {
        this.sum_assured = sum_assured;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getIns_prod_type ()
    {
        return ins_prod_type;
    }

    public void setIns_prod_type (String ins_prod_type)
    {
        this.ins_prod_type = ins_prod_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", ins_prod_cat = "+ins_prod_cat+", status = "+status+", created_datetime = "+created_datetime+", ins_id = "+ins_id+", sum_assured = "+sum_assured+", modified_datetime = "+modified_datetime+", ins_prod_type = "+ins_prod_type+"]";
    }
}

