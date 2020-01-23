package com.purplepath.purplepath.goal;

import java.io.Serializable;

/**
 * Created by Bert on 20-Jun-16.
 */
public class GoalFamilyDetails implements Serializable
{
    private String fid;

    private String occupation;

    private String status;

    private String no_of_work_years;

    private String current_org;

    private String current_designation;

    private String education;

    private String id;

    private String relationship;

    private String created_datetime;

    private String name;

    private String dob;

    private String age;

    private String gender;

    private String user_id;

    private String marital_status;

    private String modified_datetime;

    public String getFid ()
    {
        return fid;
    }

    public void setFid (String fid)
    {
        this.fid = fid;
    }

    public String getOccupation ()
    {
        return occupation;
    }

    public void setOccupation (String occupation)
    {
        this.occupation = occupation;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getNo_of_work_years ()
    {
        return no_of_work_years;
    }

    public void setNo_of_work_years (String no_of_work_years)
    {
        this.no_of_work_years = no_of_work_years;
    }

    public String getCurrent_org ()
    {
        return current_org;
    }

    public void setCurrent_org (String current_org)
    {
        this.current_org = current_org;
    }

    public String getCurrent_designation ()
    {
        return current_designation;
    }

    public void setCurrent_designation (String current_designation)
    {
        this.current_designation = current_designation;
    }

    public String getEducation ()
    {
        return education;
    }

    public void setEducation (String education)
    {
        this.education = education;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRelationship ()
    {
        return relationship;
    }

    public void setRelationship (String relationship)
    {
        this.relationship = relationship;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getMarital_status ()
    {
        return marital_status;
    }

    public void setMarital_status (String marital_status)
    {
        this.marital_status = marital_status;
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
        return "ClassPojo [fid = "+fid+", occupation = "+occupation+", status = "+status+", no_of_work_years = "+no_of_work_years+", current_org = "+current_org+", current_designation = "+current_designation+", education = "+education+", id = "+id+", relationship = "+relationship+", created_datetime = "+created_datetime+", name = "+name+", dob = "+dob+", age = "+age+", gender = "+gender+", user_id = "+user_id+", marital_status = "+marital_status+", modified_datetime = "+modified_datetime+"]";
    }
}
