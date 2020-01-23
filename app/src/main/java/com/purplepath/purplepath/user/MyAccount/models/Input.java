package com.purplepath.purplepath.user.MyAccount.models;

import java.io.Serializable;

/**
 * Created by Suresh on 24/07/17.
 */

public class Input implements Serializable {

    private String ah_zipcode;

    private String ah_state;

    private String phone;

    private String address_home;

    private String email;

    private String name;

    private String ah_country;

    private String last_name;

    private String user_id;

    private String country_code;

    private String ah_city;

    private String alias_name;

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

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
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
        return "ClassPojo [ah_zipcode = "+ah_zipcode+", ah_state = "+ah_state+", phone = "+phone+", address_home = "+address_home+", email = "+email+", name = "+name+", ah_country = "+ah_country+", last_name = "+last_name+", user_id = "+user_id+", country_code = "+country_code+", ah_city = "+ah_city+", alias_name = "+alias_name+"]";
    }
}
