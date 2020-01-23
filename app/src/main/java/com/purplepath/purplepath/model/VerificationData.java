package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by dinesh on 09/05/16.
 */
public class VerificationData implements Serializable
{
    private String message;

    private String verify_code;

    public String getVerify_code_mobile() {
        return verify_code_mobile;
    }

    public void setVerify_code_mobile(String verify_code_mobile) {
        this.verify_code_mobile = verify_code_mobile;
    }

    private String verify_code_mobile;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getVerify_code ()
    {
        return verify_code;
    }

    public void setVerify_code (String verify_code)
    {
        this.verify_code = verify_code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", verify_code = "+verify_code+"]";
    }
}