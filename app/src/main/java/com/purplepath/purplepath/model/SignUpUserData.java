package com.purplepath.purplepath.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by dinesh on 06/05/16.
 */
public class SignUpUserData implements Serializable {


        private String message;

        private User_details user_details;

        private String user_id;

        private String cust_id;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public ArrayList<Restricted_menus> getRestricted_menus() {
        return restricted_menus;
    }

        public void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        this.restricted_menus = restricted_menus;
        }

        private ArrayList<Restricted_menus> restricted_menus;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public User_details getUser_details ()
        {
            return user_details;
        }

        public void setUser_details (User_details user_details)
        {
            this.user_details = user_details;
        }

        public String getUser_id ()
        {
            return user_id;
        }

        public void setUser_id (String user_id)
        {
            this.user_id = user_id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [message = "+message+",restricted_menus="+restricted_menus+", user_details = "+user_details+", user_id = "+user_id+",cust_id = "+cust_id+"]";

        }

}
