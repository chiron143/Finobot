package com.purplepath.purplepath.famlydetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 27/07/16.
 */
public class Updated_details implements Serializable {
    private String relationship;

    private String fid;

    private String occupation;

    private String no_of_work_years;

    private String age;

    private String dob;

    private String name;

    private String current_org;

    private String gender;

    private String current_designation;

    private String user_id;

    private String marital_status;

    private String education;

    private String dependant_life_expectancy_age;

    private String dependant_retirement_age;

    public String getDependant_life_expectancy_age() {
        return dependant_life_expectancy_age;
    }

    public void setDependant_life_expectancy_age(String dependant_life_expectancy_age) {
        this.dependant_life_expectancy_age = dependant_life_expectancy_age;
    }

    public String getDependant_retirement_age() {
        return dependant_retirement_age;
    }

    public void setDependant_retirement_age(String dependant_retirement_age) {
        this.dependant_retirement_age = dependant_retirement_age;
    }

    public String getRelationship ()
    {
        return relationship;
    }

    public void setRelationship (String relationship)
    {
        this.relationship = relationship;
    }

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

    public String getNo_of_work_years ()
    {
        return no_of_work_years;
    }

    public void setNo_of_work_years (String no_of_work_years)
    {
        this.no_of_work_years = no_of_work_years;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCurrent_org ()
    {
        return current_org;
    }

    public void setCurrent_org (String current_org)
    {
        this.current_org = current_org;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getCurrent_designation ()
    {
        return current_designation;
    }

    public void setCurrent_designation (String current_designation)
    {
        this.current_designation = current_designation;
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

    public String getEducation ()
    {
        return education;
    }

    public void setEducation (String education)
    {
        this.education = education;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [relationship = "+relationship+", fid = "+fid+", occupation = "+occupation+", no_of_work_years = "+no_of_work_years+", age = "+age+", dob = "+dob+", name = "+name+", current_org = "+current_org+", gender = "+gender+", current_designation = "+current_designation+", user_id = "+user_id+", marital_status = "+marital_status+", education = "+education+"]";
    }
}
