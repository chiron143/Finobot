package com.purplepath.purplepath.settings.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.purplepath.purplepath.settings.fragment.AssumptionsFragment;
import com.purplepath.purplepath.settings.fragment.CalenderFragment;
import com.purplepath.purplepath.settings.fragment.SelectionsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pratheep.S on 04-01-2017.
 */

public class SettingsPagerAdapter  extends FragmentPagerAdapter{

    String [] title;
    List<Fragment> fragment=new ArrayList<Fragment>();

    public SettingsPagerAdapter(FragmentManager fm,String [] title ) {
        super(fm);
        this.title=title;
//        fragment.add(new SettingsFragment());
        fragment.add(new SelectionsFragment());
        fragment.add(new AssumptionsFragment());
        fragment.add(new CalenderFragment());

    }

    @Override
    public Fragment getItem(int position) {
                return fragment.get(position);
    }

    @Override
    public int getCount() {

        return fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
