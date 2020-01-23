package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 10-Jun-16.
 */

public class AddPersonalDetailsDataListModel implements Serializable
{
    private String occupation;

    private String marriage_date;

    private String no_of_work_years;

    private String address_work;

    private String current_org;

    private String current_designation;

    private String education;

    private String country_code;

    private String address_home;

    private String planned_retirement_age;

    private String name;

    private String middle_name;

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    private String last_name;

    private String dob;

    private String age;

    private String martial_status;

    private String mob_no;

    private String gender;

    private String user_id;

    private String life_expectancy_age;

    private String married_since;

    private String no_of_ava_years;

    private String email_id;

    public String getOccupation ()
    {
        return occupation;
    }

    public void setOccupation (String occupation)
    {
        this.occupation = occupation;
    }

    public String getMarriage_date ()
    {
        return marriage_date;
    }

    public void setMarriage_date (String marriage_date)
    {
        this.marriage_date = marriage_date;
    }

    public String getNo_of_work_years ()
    {
        return no_of_work_years;
    }

    public void setNo_of_work_years (String no_of_work_years)
    {
        this.no_of_work_years = no_of_work_years;
    }

    public String getAddress_work ()
    {
        return address_work;
    }

    public void setAddress_work (String address_work)
    {
        this.address_work = address_work;
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

    public String getCountry_code ()
    {
        return country_code;
    }

    public void setCountry_code (String country_code)
    {
        this.country_code = country_code;
    }

    public String getAddress_home ()
    {
        return address_home;
    }

    public void setAddress_home (String address_home)
    {
        this.address_home = address_home;
    }

    public String getPlanned_retirement_age ()
    {
        return planned_retirement_age;
    }

    public void setPlanned_retirement_age (String planned_retirement_age)
    {
        this.planned_retirement_age = planned_retirement_age;
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

    public String getMartial_status ()
    {
        return martial_status;
    }

    public void setMartial_status (String martial_status)
    {
        this.martial_status = martial_status;
    }

    public String getMob_no ()
    {
        return mob_no;
    }

    public void setMob_no (String mob_no)
    {
        this.mob_no = mob_no;
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

    public String getLife_expectancy_age ()
    {
        return life_expectancy_age;
    }

    public void setLife_expectancy_age (String life_expectancy_age)
    {
        this.life_expectancy_age = life_expectancy_age;
    }

    public String getMarried_since ()
    {
        return married_since;
    }

    public void setMarried_since (String married_since)
    {
        this.married_since = married_since;
    }

    public String getNo_of_ava_years ()
    {
        return no_of_ava_years;
    }

    public void setNo_of_ava_years (String no_of_ava_years)
    {
        this.no_of_ava_years = no_of_ava_years;
    }

    public String getEmail_id ()
    {
        return email_id;
    }

    public void setEmail_id (String email_id)
    {
        this.email_id = email_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [occupation = "+occupation+", marriage_date = "+marriage_date+", no_of_work_years = "+no_of_work_years+", address_work = "+address_work+", current_org = "+current_org+", current_designation = "+current_designation+", education = "+education+", country_code = "+country_code+", address_home = "+address_home+", planned_retirement_age = "+planned_retirement_age+", name = "+name+", dob = "+dob+", age = "+age+", martial_status = "+martial_status+", mob_no = "+mob_no+", gender = "+gender+", user_id = "+user_id+", life_expectancy_age = "+life_expectancy_age+", married_since = "+married_since+", no_of_ava_years = "+no_of_ava_years+", email_id = "+email_id+"]";
    }
}