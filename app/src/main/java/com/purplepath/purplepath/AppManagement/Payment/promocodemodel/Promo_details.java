package com.purplepath.purplepath.AppManagement.Payment.promocodemodel;

import java.io.Serializable;

public class Promo_details implements Serializable{
    private String id;

    private String promo_code;

    private String created_datetime;

    private String modified_datetime;

    private String expiry_date;

    private String discount;

    private String user_ids;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPromo_code ()
    {
        return promo_code;
    }

    public void setPromo_code (String promo_code)
    {
        this.promo_code = promo_code;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getExpiry_date ()
    {
        return expiry_date;
    }

    public void setExpiry_date (String expiry_date)
    {
        this.expiry_date = expiry_date;
    }

    public String getDiscount ()
    {
        return discount;
    }

    public void setDiscount (String discount)
    {
        this.discount = discount;
    }

    public String getUser_ids ()
    {
        return user_ids;
    }

    public void setUser_ids (String user_ids)
    {
        this.user_ids = user_ids;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", promo_code = "+promo_code+", created_datetime = "+created_datetime+", modified_datetime = "+modified_datetime+", expiry_date = "+expiry_date+", discount = "+discount+", user_ids = "+user_ids+"]";
    }
}

