package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class MainData implements Serializable {

    private String message;

    private Home_cash home_cash;

    private Home_rent home_rent;

    private Home_loan home_loan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Home_cash getHome_cash ()
    {
        return home_cash;
    }

    public void setHome_cash (Home_cash home_cash)
    {
        this.home_cash = home_cash;
    }

    public Home_rent getHome_rent ()
    {
        return home_rent;
    }

    public void setHome_rent (Home_rent home_rent)
    {
        this.home_rent = home_rent;
    }

    public Home_loan getHome_loan ()
    {
        return home_loan;
    }

    public void setHome_loan (Home_loan home_loan)
    {
        this.home_loan = home_loan;
    }

}
