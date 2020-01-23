package com.purplepath.purplepath.taxfiling.getadharpanmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 5/22/18.
 */

public class Input implements Serializable{

    private String itr_processing_date;

    private String confirmation_text;

    private String user_id;

    private String refund_received_date;

    private String intimation_received_date;

    public String getItr_processing_date ()
    {
        return itr_processing_date;
    }

    public void setItr_processing_date (String itr_processing_date)
    {
        this.itr_processing_date = itr_processing_date;
    }

    public String getConfirmation_text ()
    {
        return confirmation_text;
    }

    public void setConfirmation_text (String confirmation_text)
    {
        this.confirmation_text = confirmation_text;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getRefund_received_date ()
    {
        return refund_received_date;
    }

    public void setRefund_received_date (String refund_received_date)
    {
        this.refund_received_date = refund_received_date;
    }

    public String getIntimation_received_date ()
    {
        return intimation_received_date;
    }

    public void setIntimation_received_date (String intimation_received_date)
    {
        this.intimation_received_date = intimation_received_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [itr_processing_date = "+itr_processing_date+", confirmation_text = "+confirmation_text+", user_id = "+user_id+", refund_received_date = "+refund_received_date+", intimation_received_date = "+intimation_received_date+"]";
    }
}

