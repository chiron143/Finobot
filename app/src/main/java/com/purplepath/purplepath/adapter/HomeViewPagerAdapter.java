package com.purplepath.purplepath.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 25-01-2017.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    public HomeViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {

        //Log.i("spcheck", "getCount: "+fragments.size());
        return fragments.size();
    }
}
