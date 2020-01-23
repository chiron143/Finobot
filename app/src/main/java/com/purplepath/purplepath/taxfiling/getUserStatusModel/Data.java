package com.purplepath.purplepath.taxfiling.getUserStatusModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 4/26/18.
 */

public class Data implements Serializable {

    private String message;

    private String payment_made;

    private String form_26as;

    private String filer_status;

    private String tax_file_user_status;

    private String property_status;

    private String prepaid_tax_status;

    public String getPrepaid_tax_status() {
        return prepaid_tax_status;
    }

    public void setPrepaid_tax_status(String prepaid_tax_status) {
        this.prepaid_tax_status = prepaid_tax_status;
    }

    public String getProperty_status() {
        return property_status;
    }

    public void setProperty_status(String property_status) {
        this.property_status = property_status;
    }

    private ArrayList<Page_visited_array> page_visited_array;

    public ArrayList<Page_visited_array> getPage_visited_array() {
        return page_visited_array;
    }

    public void setPage_visited_array(ArrayList<Page_visited_array> page_visited_array) {
        this.page_visited_array = page_visited_array;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getPayment_made ()
    {
        return payment_made;
    }

    public void setPayment_made (String payment_made)
    {
        this.payment_made = payment_made;
    }

    public String getForm_26as ()
    {
        return form_26as;
    }

    public void setForm_26as (String form_26as)
    {
        this.form_26as = form_26as;
    }

    public String getFiler_status ()
    {
        return filer_status;
    }

    public void setFiler_status (String filer_status)
    {
        this.filer_status = filer_status;
    }

    public String getTax_file_user_status ()
    {
        return tax_file_user_status;
    }

    public void setTax_file_user_status (String tax_file_user_status)
    {
        this.tax_file_user_status = tax_file_user_status;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", property_status = "+property_status+", prepaid_tax_status = "+prepaid_tax_status+", payment_made = "+payment_made+", form_26as = "+form_26as+", filer_status = "+filer_status+", tax_file_user_status = "+tax_file_user_status+", page_visited_array = "+page_visited_array+"]";
    }
}

