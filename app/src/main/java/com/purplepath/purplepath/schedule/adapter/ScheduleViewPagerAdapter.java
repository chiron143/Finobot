package com.purplepath.purplepath.schedule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 20/06/17.
 */

public class ScheduleViewPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    private String[] tabTitles;
    ArrayList<Fragment> fragments;

    public ScheduleViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] expenseTabTitles) {
        super(fm);
        tabTitles = expenseTabTitles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        /*Fragment fragment = null;
        if(position==0) {
            fragment =  new Pending_Fragment();
        }else if(position==1){
            fragment = new UpcomingSchedule();
        }*/
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
