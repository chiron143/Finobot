package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

/**
 * Created by Bert on 26-Aug-16.
 */
public class Input
{
    private String level_1_ids;

    private String level_3_ids;

    private String level_2_ids;

    private String family_id;

    private String user_id;

    private String is_own_user;

    public String getLevel_1_ids ()
    {
        return level_1_ids;
    }

    public void setLevel_1_ids (String level_1_ids)
    {
        this.level_1_ids = level_1_ids;
    }

    public String getLevel_3_ids ()
    {
        return level_3_ids;
    }

    public void setLevel_3_ids (String level_3_ids)
    {
        this.level_3_ids = level_3_ids;
    }

    public String getLevel_2_ids ()
    {
        return level_2_ids;
    }

    public void setLevel_2_ids (String level_2_ids)
    {
        this.level_2_ids = level_2_ids;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getIs_own_user ()
    {
        return is_own_user;
    }

    public void setIs_own_user (String is_own_user)
    {
        this.is_own_user = is_own_user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [level_1_ids = "+level_1_ids+", level_3_ids = "+level_3_ids+", level_2_ids = "+level_2_ids+", family_id = "+family_id+", user_id = "+user_id+", is_own_user = "+is_own_user+"]";
    }
}
