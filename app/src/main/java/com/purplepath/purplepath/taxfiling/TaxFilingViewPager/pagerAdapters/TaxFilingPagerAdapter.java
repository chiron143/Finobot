package com.purplepath.purplepath.taxfiling.TaxFilingViewPager.pagerAdapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.TaxFilingChatViewPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravinr on 8/10/18.
 */

public class TaxFilingPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TaxFilingPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public static TaxFilingPagerAdapter newInstance(FragmentManager fm) {
        return new TaxFilingPagerAdapter(fm);
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
