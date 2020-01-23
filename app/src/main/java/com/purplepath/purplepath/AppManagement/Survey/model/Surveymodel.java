package com.purplepath.purplepath.AppManagement.Survey.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pravinr on 6/14/17.
 */

public class Surveymodel implements Parcelable {


    Creator<Surveymodel> obj=new Creator<Surveymodel>() {
        @Override
        public Surveymodel createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Surveymodel[] newArray(int size) {
            return new Surveymodel[0];
        }
    };
    private String status_code;

    private String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }



    private Surveydata data;

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

    public Surveydata getData ()
    {
        return data;
    }

    public void setData (Surveydata data)
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

