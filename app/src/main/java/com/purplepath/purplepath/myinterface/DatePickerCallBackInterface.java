package com.purplepath.purplepath.myinterface;

/**
 * Created by Pratheep.S on 11-02-2017.
 */

public interface DatePickerCallBackInterface {
    void updateEditTextValue(String value,String title); //pass title value to update appropriate edit text  based on title value;


    void updateIndividualEditTextValue(String value,String title);
   /* public void upadateExpectedLife(String expectedLife);
    public void upadateReterimentLife(String expectedLife);*/
}
