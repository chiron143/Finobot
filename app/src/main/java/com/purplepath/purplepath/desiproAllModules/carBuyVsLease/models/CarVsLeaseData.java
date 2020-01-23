package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;

/**
 * Created by dinesh on 23/01/18.
 */

public class CarVsLeaseData implements Serializable {

    private String message;

    private Car_lease car_lease;

    private Car_cash car_cash;

    private Car_loan car_loan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Car_lease getCar_lease ()
    {
        return car_lease;
    }

    public void setCar_lease (Car_lease car_lease)
    {
        this.car_lease = car_lease;
    }

    public Car_cash getCar_cash ()
    {
        return car_cash;
    }

    public void setCar_cash (Car_cash car_cash)
    {
        this.car_cash = car_cash;
    }

    public Car_loan getCar_loan ()
    {
        return car_loan;
    }

    public void setCar_loan (Car_loan car_loan)
    {
        this.car_loan = car_loan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", car_lease = "+car_lease+", car_cash = "+car_cash+", car_loan = "+car_loan+"]";
    }

}
