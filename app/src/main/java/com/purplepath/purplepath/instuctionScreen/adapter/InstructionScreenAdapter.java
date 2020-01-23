package com.purplepath.purplepath.instuctionScreen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pravinr on 9/26/17.
 */

public class InstructionScreenAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    public InstructionScreenAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {

        Log.i("spcheck", "getCount: "+fragments.size());
        return fragments.size();
    }
}
