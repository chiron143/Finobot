package com.purplepath.purplepath.desiproAllModules.loanComparison.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 16-06-2017.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<String> best_ln_array;

    private Loan2 loan2;

    private Loan3 loan3;

    private Loan1 loan1;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<String> getBest_ln_array ()
    {
        return best_ln_array;
    }

    public void setBest_ln_array (ArrayList<String> best_ln_array)
    {
        this.best_ln_array = best_ln_array;
    }

    public Loan2 getLoan2 ()
    {
        return loan2;
    }

    public void setLoan2 (Loan2 loan2)
    {
        this.loan2 = loan2;
    }

    public Loan3 getLoan3 ()
    {
        return loan3;
    }

    public void setLoan3 (Loan3 loan3)
    {
        this.loan3 = loan3;
    }

    public Loan1 getLoan1 ()
    {
        return loan1;
    }

    public void setLoan1 (Loan1 loan1)
    {
        this.loan1 = loan1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", best_ln_array = "+best_ln_array+", loan2 = "+loan2+", loan3 = "+loan3+", loan1 = "+loan1+"]";
    }
}
