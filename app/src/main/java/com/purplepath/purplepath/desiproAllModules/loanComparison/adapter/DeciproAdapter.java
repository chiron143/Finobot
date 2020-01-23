package com.purplepath.purplepath.desiproAllModules.loanComparison.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 12-06-2017.
 */

public class DeciproAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentList;
    ArrayList<String> titles;

    public DeciproAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> titles) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
