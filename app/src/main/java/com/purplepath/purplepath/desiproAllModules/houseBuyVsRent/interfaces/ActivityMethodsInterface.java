package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces;

import androidx.fragment.app.Fragment;

/**
 * Created by Pratheep.S on 12-09-2017.
 */

public interface ActivityMethodsInterface {
    void showViewPager();
    void hideFAB();
    void showFAB();
    void showFragment(Fragment fragment);
    void updateValuesFromBuyFragment(String key,String value);
    void updateValuesFromRentFragment(String key,String value);
    void updateValuesFromHomeFragment(String key,String value);
    void onActivityBackPressed();
}
