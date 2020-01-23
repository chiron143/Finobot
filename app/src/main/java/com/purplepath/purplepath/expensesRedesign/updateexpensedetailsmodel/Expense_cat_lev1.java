package com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel;

/**
 * Created by srinivasan on 9/1/2016.
 */
public class Expense_cat_lev1
{
    private String tb_field_name;

    private String lev1_name;

    private String id;

    private String type;

    public String getTb_field_name ()
    {
        return tb_field_name;
    }

    public void setTb_field_name (String tb_field_name)
    {
        this.tb_field_name = tb_field_name;
    }

    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tb_field_name = "+tb_field_name+", lev1_name = "+lev1_name+", id = "+id+", type = "+type+"]";
    }
}
