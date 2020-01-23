package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Dinesh on xvalue  14-Mar-16.
 */
public class SignUpModel implements Serializable
{
        private String status_code;

        private String status;

        private SignUpUserData data;

        private String service_name;

        public String getStatus_code ()
        {
            return status_code;
        }

        public void setStatus_code (String status_code)
        {
            this.status_code = status_code;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public SignUpUserData getData ()
        {
            return data;
        }

        public void setData (SignUpUserData data)
        {
            this.data = data;
        }

        public String getService_name ()
        {
            return service_name;
        }

        public void setService_name (String service_name)
        {
            this.service_name = service_name;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [status_code = "+status_code+", status = "+status+", data = "+data+", service_name = "+service_name+"]";
        }



}