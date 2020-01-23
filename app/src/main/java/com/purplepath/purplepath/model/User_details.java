package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by dinesh on 15/03/16.
 */

public class User_details implements Serializable
{
    private String img_url;

    private String phone;

    private String email;

    private String name;

    private String is_survey_completed;

    private String user_id;

    private String user_paid_type;

    private String cust_id;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getUser_paid_type() {
        return user_paid_type;
    }

    public void setUser_paid_type(String user_paid_type) {
        this.user_paid_type = user_paid_type;
    }


    public String getImg_url ()
    {
        return img_url;
    }

    public void setImg_url (String img_url)
    {
        this.img_url = img_url;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getIs_survey_completed ()
    {
        return is_survey_completed;
    }

    public void setIs_survey_completed (String is_survey_completed)
    {
        this.is_survey_completed = is_survey_completed;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [img_url = "+img_url+", phone = "+phone+", email = "+email+", name = "+name+", user_paid_type = "+user_paid_type+", is_survey_completed = "+is_survey_completed+", user_id = "+user_id+", cust_id = "+cust_id+"]";
    }
}