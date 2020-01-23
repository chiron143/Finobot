package com.purplepath.purplepath.AppManagement.Vendors.Models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 18-05-2017.
 */

public class Vendors implements Serializable {

    private String phone_number;

    private String website;

    private String practice_license;

    private String state;

    private String image;

    private String pp_notes;

    private String vendor_id;

    private String country;

    private String city;

    private String title;

    private String area;

    private String email;

    private String address;

    private String pin;

    private String created_datetime;

    private String background;

    private String company;

    private String rating;

    private String modified_datetime;

    private String vendor_type;

    private String specialization;

    private String vendor_name;

    public String getPhone_number ()
    {
        return phone_number;
    }

    public void setPhone_number (String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getWebsite ()
    {
        return website;
    }

    public void setWebsite (String website)
    {
        this.website = website;
    }

    public String getPractice_license ()
    {
        return practice_license;
    }

    public void setPractice_license (String practice_license)
    {
        this.practice_license = practice_license;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getPp_notes ()
    {
        return pp_notes;
    }

    public void setPp_notes (String pp_notes)
    {
        this.pp_notes = pp_notes;
    }

    public String getVendor_id ()
    {
        return vendor_id;
    }

    public void setVendor_id (String vendor_id)
    {
        this.vendor_id = vendor_id;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getPin ()
    {
        return pin;
    }

    public void setPin (String pin)
    {
        this.pin = pin;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getBackground ()
    {
        return background;
    }

    public void setBackground (String background)
    {
        this.background = background;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getVendor_type ()
    {
        return vendor_type;
    }

    public void setVendor_type (String vendor_type)
    {
        this.vendor_type = vendor_type;
    }

    public String getSpecialization ()
    {
        return specialization;
    }

    public void setSpecialization (String specialization)
    {
        this.specialization = specialization;
    }

    public String getVendor_name ()
    {
        return vendor_name;
    }

    public void setVendor_name (String vendor_name)
    {
        this.vendor_name = vendor_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone_number = "+phone_number+", website = "+website+", practice_license = "+practice_license+", state = "+state+", image = "+image+", pp_notes = "+pp_notes+", vendor_id = "+vendor_id+", country = "+country+", city = "+city+", title = "+title+", area = "+area+", email = "+email+", address = "+address+", pin = "+pin+", created_datetime = "+created_datetime+", background = "+background+", company = "+company+", rating = "+rating+", modified_datetime = "+modified_datetime+", vendor_type = "+vendor_type+", specialization = "+specialization+", vendor_name = "+vendor_name+"]";
    }
}
