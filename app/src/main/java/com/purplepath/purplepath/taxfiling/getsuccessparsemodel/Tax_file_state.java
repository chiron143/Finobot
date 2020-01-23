package com.purplepath.purplepath.taxfiling.getsuccessparsemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 7/8/18.
 */

public class Tax_file_state implements Serializable {
    private String id;

    private String multi_form16;

    private String form16_upload;

    private String created_datetime;

    private String multi_form16_upload;

    private String form16;

    private String agreement;

    private String add_invest;

    private String non_form16;

    private String plan_amount;

    private String user_id;

    private String initial_chat_screen;

    private String modified_datetime;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMulti_form16 ()
    {
        return multi_form16;
    }

    public void setMulti_form16 (String multi_form16)
    {
        this.multi_form16 = multi_form16;
    }

    public String getForm16_upload ()
    {
        return form16_upload;
    }

    public void setForm16_upload (String form16_upload)
    {
        this.form16_upload = form16_upload;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getMulti_form16_upload ()
    {
        return multi_form16_upload;
    }

    public void setMulti_form16_upload (String multi_form16_upload)
    {
        this.multi_form16_upload = multi_form16_upload;
    }

    public String getForm16 ()
    {
        return form16;
    }

    public void setForm16 (String form16)
    {
        this.form16 = form16;
    }

    public String getAgreement ()
    {
        return agreement;
    }

    public void setAgreement (String agreement)
    {
        this.agreement = agreement;
    }

    public String getAdd_invest ()
    {
        return add_invest;
    }

    public void setAdd_invest (String add_invest)
    {
        this.add_invest = add_invest;
    }

    public String getNon_form16 ()
    {
        return non_form16;
    }

    public void setNon_form16 (String non_form16)
    {
        this.non_form16 = non_form16;
    }

    public String getPlan_amount ()
    {
        return plan_amount;
    }

    public void setPlan_amount (String plan_amount)
    {
        this.plan_amount = plan_amount;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getInitial_chat_screen ()
    {
        return initial_chat_screen;
    }

    public void setInitial_chat_screen (String initial_chat_screen)
    {
        this.initial_chat_screen = initial_chat_screen;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", multi_form16 = "+multi_form16+", form16_upload = "+form16_upload+", created_datetime = "+created_datetime+", multi_form16_upload = "+multi_form16_upload+", form16 = "+form16+", agreement = "+agreement+", add_invest = "+add_invest+", non_form16 = "+non_form16+", plan_amount = "+plan_amount+", user_id = "+user_id+", initial_chat_screen = "+initial_chat_screen+", modified_datetime = "+modified_datetime+"]";
    }
}

