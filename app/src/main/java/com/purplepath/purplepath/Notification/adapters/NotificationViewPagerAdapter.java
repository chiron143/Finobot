package com.purplepath.purplepath.Notification.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 17-01-2017.
 */

public class NotificationViewPagerAdapter extends FragmentPagerAdapter{
    String titles[];
    ArrayList<Fragment> fragments;
    public NotificationViewPagerAdapter(FragmentManager fm, String titles[], ArrayList<Fragment> fragments) {
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size() ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
