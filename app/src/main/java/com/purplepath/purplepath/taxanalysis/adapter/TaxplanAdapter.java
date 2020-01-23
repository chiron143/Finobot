package com.purplepath.purplepath.taxanalysis.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.healthinsurance.adapter.HealthInsuranceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravinr on 1/5/18.
 */

public class TaxplanAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TaxplanAdapter(FragmentManager manager) {
        super(manager);
    }

    public static TaxplanAdapter newInstance(FragmentManager fm) {
        return new TaxplanAdapter(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}

