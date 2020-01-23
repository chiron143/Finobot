package com.purplepath.purplepath.user.MyAccount.getModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 24/07/17.
 */

public class Data implements Serializable {

    private String message;

//    private User_profile_det[] user_profile_det;

    private ArrayList<User_profile_det> user_profile_det;

    public String getMessage ()
    {
        return message;
    }

    public ArrayList<User_profile_det> getUser_profile_det() {
        return user_profile_det;
    }

    public void setUser_profile_det(ArrayList<User_profile_det> user_profile_det) {
        this.user_profile_det = user_profile_det;
    }

    public void setMessage (String message)

    {
        this.message = message;
    }

//    public User_profile_det[] getUser_profile_det ()
//    {
//        return user_profile_det;
//    }
//
//    public void setUser_profile_det (User_profile_det[] user_profile_det)
//    {
//        this.user_profile_det = user_profile_det;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_profile_det = "+user_profile_det+"]";
    }
}
