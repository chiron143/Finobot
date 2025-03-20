package com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.pagerAdapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.healthinsurance.adapter.HealthInsuranceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravinr on 7/26/18.
 */

public class TaxPlanningPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TaxPlanningPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public static TaxPlanningPagerAdapter newInstance(FragmentManager fm) {
        return new TaxPlanningPagerAdapter(fm);
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
