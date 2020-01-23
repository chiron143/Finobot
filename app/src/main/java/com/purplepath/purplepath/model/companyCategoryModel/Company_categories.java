package com.purplepath.purplepath.model.companyCategoryModel;

import java.io.Serializable;

/**
 * Created by pravinr on 12/20/17.
 */

public class Company_categories implements Serializable {
    private String cat_code;

    private String id;

    private String created_datetime;

    private String company_website;

    private String user_count;

    private String modified_datetime;

    private String cat_name;

    public String getCat_code ()
    {
        return cat_code;
    }

    public void setCat_code (String cat_code)
    {
        this.cat_code = cat_code;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getCompany_website ()
    {
        return company_website;
    }

    public void setCompany_website (String company_website)
    {
        this.company_website = company_website;
    }

    public String getUser_count ()
    {
        return user_count;
    }

    public void setUser_count (String user_count)
    {
        this.user_count = user_count;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getCat_name ()
    {
        return cat_name;
    }

    public void setCat_name (String cat_name)
    {
        this.cat_name = cat_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cat_code = "+cat_code+", id = "+id+", created_datetime = "+created_datetime+", company_website = "+company_website+", user_count = "+user_count+", modified_datetime = "+modified_datetime+", cat_name = "+cat_name+"]";
    }
}

