package com.purplepath.purplepath.myinterface;

/**
 * Created by vishnu on 10-09-2016.
 */
public interface OnActivityBackPressedListener {
    void onActivityBackPressed();
    void setActionBarTitle(String mTitle);
    void setActionBarExpTitle(String mTitle);
    void closeDropDownTab();
}
