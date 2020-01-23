package com.purplepath.purplepath.emergencyfundAnalysis.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.emergencyfundAnalysis.EmergencyFundActual;
import com.purplepath.purplepath.emergencyfundAnalysis.EmergencyFundPlan;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 20-03-2017.
 */

public class EmergencyFundAdapter  extends FragmentStatePagerAdapter{

    String [] Title;
    ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    public EmergencyFundAdapter(FragmentManager fm,String[] Title) {
        super(fm);
        this.Title=Title;
        fragments.add(new EmergencyFundPlan());
        fragments.add(new EmergencyFundActual());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Title[position];
    }
}
