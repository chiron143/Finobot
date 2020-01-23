package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Edu_det implements Serializable
{
    private String edu_comp_chgs_per;

    private String edu_tuit_fee_per;

    private String edu_stat;

    private String edu_books;

    private String edu_tuit_fee;

    private String edu_stat_per;

    private String edu_ext_tuit_per;

    private String edu_books_per;

    private String edu_comp_chgs;

    private String edu_ext_tuit;

    public String getEdu_comp_chgs_per ()
    {
        return edu_comp_chgs_per;
    }

    public void setEdu_comp_chgs_per (String edu_comp_chgs_per)
    {
        this.edu_comp_chgs_per = edu_comp_chgs_per;
    }

    public String getEdu_tuit_fee_per ()
    {
        return edu_tuit_fee_per;
    }

    public void setEdu_tuit_fee_per (String edu_tuit_fee_per)
    {
        this.edu_tuit_fee_per = edu_tuit_fee_per;
    }

    public String getEdu_stat ()
{
    return edu_stat;
}

    public void setEdu_stat (String edu_stat)
    {
        this.edu_stat = edu_stat;
    }

    public String getEdu_books ()
{
    return edu_books;
}

    public void setEdu_books (String edu_books)
    {
        this.edu_books = edu_books;
    }

    public String getEdu_tuit_fee ()
{
    return edu_tuit_fee;
}

    public void setEdu_tuit_fee (String edu_tuit_fee)
    {
        this.edu_tuit_fee = edu_tuit_fee;
    }

    public String getEdu_stat_per ()
    {
        return edu_stat_per;
    }

    public void setEdu_stat_per (String edu_stat_per)
    {
        this.edu_stat_per = edu_stat_per;
    }

    public String getEdu_ext_tuit_per ()
    {
        return edu_ext_tuit_per;
    }

    public void setEdu_ext_tuit_per (String edu_ext_tuit_per)
    {
        this.edu_ext_tuit_per = edu_ext_tuit_per;
    }

    public String getEdu_books_per ()
    {
        return edu_books_per;
    }

    public void setEdu_books_per (String edu_books_per)
    {
        this.edu_books_per = edu_books_per;
    }

    public String getEdu_comp_chgs ()
{
    return edu_comp_chgs;
}

    public void setEdu_comp_chgs (String edu_comp_chgs)
    {
        this.edu_comp_chgs = edu_comp_chgs;
    }

    public String getEdu_ext_tuit ()
{
    return edu_ext_tuit;
}

    public void setEdu_ext_tuit (String edu_ext_tuit)
    {
        this.edu_ext_tuit = edu_ext_tuit;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [edu_comp_chgs_per = "+edu_comp_chgs_per+", edu_tuit_fee_per = "+edu_tuit_fee_per+", edu_stat = "+edu_stat+", edu_books = "+edu_books+", edu_tuit_fee = "+edu_tuit_fee+", edu_stat_per = "+edu_stat_per+", edu_ext_tuit_per = "+edu_ext_tuit_per+", edu_books_per = "+edu_books_per+", edu_comp_chgs = "+edu_comp_chgs+", edu_ext_tuit = "+edu_ext_tuit+"]";
    }
}