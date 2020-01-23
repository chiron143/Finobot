package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Sk_det implements Serializable
{
    private String sk_equip_mat;

    private String sk_course_per;

    private String sk_books_per;

    private String sk_class;

    private String sk_course;

    private String sk_books;

    private String sk_class_per;

    private String sk_equip_mat_per;

    public String getSk_equip_mat ()
{
    return sk_equip_mat;
}

    public void setSk_equip_mat (String sk_equip_mat)
    {
        this.sk_equip_mat = sk_equip_mat;
    }

    public String getSk_course_per ()
    {
        return sk_course_per;
    }

    public void setSk_course_per (String sk_course_per)
    {
        this.sk_course_per = sk_course_per;
    }

    public String getSk_books_per ()
    {
        return sk_books_per;
    }

    public void setSk_books_per (String sk_books_per)
    {
        this.sk_books_per = sk_books_per;
    }

    public String getSk_class ()
{
    return sk_class;
}

    public void setSk_class (String sk_class)
    {
        this.sk_class = sk_class;
    }

    public String getSk_course ()
{
    return sk_course;
}

    public void setSk_course (String sk_course)
    {
        this.sk_course = sk_course;
    }

    public String getSk_books ()
{
    return sk_books;
}

    public void setSk_books (String sk_books)
    {
        this.sk_books = sk_books;
    }

    public String getSk_class_per ()
    {
        return sk_class_per;
    }

    public void setSk_class_per (String sk_class_per)
    {
        this.sk_class_per = sk_class_per;
    }

    public String getSk_equip_mat_per ()
    {
        return sk_equip_mat_per;
    }

    public void setSk_equip_mat_per (String sk_equip_mat_per)
    {
        this.sk_equip_mat_per = sk_equip_mat_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sk_equip_mat = "+sk_equip_mat+", sk_course_per = "+sk_course_per+", sk_books_per = "+sk_books_per+", sk_class = "+sk_class+", sk_course = "+sk_course+", sk_books = "+sk_books+", sk_class_per = "+sk_class_per+", sk_equip_mat_per = "+sk_equip_mat_per+"]";
    }
}