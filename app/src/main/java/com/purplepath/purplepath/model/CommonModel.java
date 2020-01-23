package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 08-Jul-16.
 */
public class CommonModel implements Serializable
{
    private String Message;

    private String MailSentStatus;

    private String Code;

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    public String getMailSentStatus ()
    {
        return MailSentStatus;
    }

    public void setMailSentStatus (String MailSentStatus)
    {
        this.MailSentStatus = MailSentStatus;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Message = "+Message+", MailSentStatus = "+MailSentStatus+", Code = "+Code+"]";
    }
}