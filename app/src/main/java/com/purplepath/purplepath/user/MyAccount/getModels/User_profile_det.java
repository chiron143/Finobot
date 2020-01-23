package com.purplepath.purplepath.user.MyAccount.getModels;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 24/07/17.
 */

public class User_profile_det implements Serializable {
    private String ah_zipcode;

    private String ah_state;

    private String phone;

    private String address_home;

    private String email;

    private String name;

    private String ah_country;

    private String last_name;

    private String country_code;

    private String ah_city;

    private String alias_name;

    private String ah_res_no;

    private String ah_res_name;

    private String ah_road_street;

    private String ah_locality_area;

    public String getAh_res_no() {
        return ah_res_no;
    }

    public void setAh_res_no(String ah_res_no) {
        this.ah_res_no = ah_res_no;
    }

    public String getAh_res_name() {
        return ah_res_name;
    }

    public void setAh_res_name(String ah_res_name) {
        this.ah_res_name = ah_res_name;
    }

    public String getAh_road_street() {
        return ah_road_street;
    }

    public void setAh_road_street(String ah_road_street) {
        this.ah_road_street = ah_road_street;
    }

    public String getAh_locality_area() {
        return ah_locality_area;
    }

    public void setAh_locality_area(String ah_locality_area) {
        this.ah_locality_area = ah_locality_area;
    }

    public String getAh_zipcode ()
    {
        return ah_zipcode;
    }

    public void setAh_zipcode (String ah_zipcode)
    {
        this.ah_zipcode = ah_zipcode;
    }

    public String getAh_state ()
    {
        return ah_state;
    }

    public void setAh_state (String ah_state)
    {
        this.ah_state = ah_state;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getAddress_home ()
    {
        return address_home;
    }

    public void setAddress_home (String address_home)
    {
        this.address_home = address_home;
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

    public String getAh_country ()
    {
        return ah_country;
    }

    public void setAh_country (String ah_country)
    {
        this.ah_country = ah_country;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public String getCountry_code ()
    {
        return country_code;
    }

    public void setCountry_code (String country_code)
    {
        this.country_code = country_code;
    }

    public String getAh_city ()
    {
        return ah_city;
    }

    public void setAh_city (String ah_city)
    {
        this.ah_city = ah_city;
    }

    public String getAlias_name ()
    {
        return alias_name;
    }

    public void setAlias_name (String alias_name)
    {
        this.alias_name = alias_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ah_zipcode = "+ah_zipcode+", ah_state = "+ah_state+", phone = "+phone+", address_home = "+address_home+", email = "+email+", name = "+name+", ah_country = "+ah_country+", last_name = "+last_name+", country_code = "+country_code+", ah_city = "+ah_city+", alias_name = "+alias_name+"]";
    }
}
