package com.purplepath.purplepath.AppManagement.Payment.gettaxpaymentmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 4/20/18.
 */

public class User_tax_file_payment_details implements Serializable{

    private String amount;

    private String message;

    private String id;

    private String transaction_status;

    private String transaction_type;

    private String merchant_order_id;

    private String created_datetime;

    private String response_code;

    private String user_id;

    private String modified_datetime;

    private String msp_ref_id;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTransaction_status ()
    {
        return transaction_status;
    }

    public void setTransaction_status (String transaction_status)
    {
        this.transaction_status = transaction_status;
    }

    public String getTransaction_type ()
    {
        return transaction_type;
    }

    public void setTransaction_type (String transaction_type)
    {
        this.transaction_type = transaction_type;
    }

    public String getMerchant_order_id ()
    {
        return merchant_order_id;
    }

    public void setMerchant_order_id (String merchant_order_id)
    {
        this.merchant_order_id = merchant_order_id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getResponse_code ()
    {
        return response_code;
    }

    public void setResponse_code (String response_code)
    {
        this.response_code = response_code;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getMsp_ref_id ()
    {
        return msp_ref_id;
    }

    public void setMsp_ref_id (String msp_ref_id)
    {
        this.msp_ref_id = msp_ref_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", message = "+message+", id = "+id+", transaction_status = "+transaction_status+", transaction_type = "+transaction_type+", merchant_order_id = "+merchant_order_id+", created_datetime = "+created_datetime+", response_code = "+response_code+", user_id = "+user_id+", modified_datetime = "+modified_datetime+", msp_ref_id = "+msp_ref_id+"]";
    }
}

