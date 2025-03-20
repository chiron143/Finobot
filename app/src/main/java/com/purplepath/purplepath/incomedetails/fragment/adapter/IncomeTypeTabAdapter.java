package com.purplepath.purplepath.incomedetails.fragment.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.purplepath.purplepath.incomedetails.fragment.fragments.PostRetairementFragment;
import com.purplepath.purplepath.incomedetails.fragment.fragments.PreRetirementFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 20/06/16.
 */
public class IncomeTypeTabAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    private List<String> tabTitles = new ArrayList<String>();


    public IncomeTypeTabAdapter(FragmentManager fm, List<String> NumOfTabs) {
        super(fm);
        this.tabTitles = NumOfTabs;

    }
    public void updateView(String name)
    {
        tabTitles.add(name);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return (PreRetirementFragment.newInstance(position));
        else
            return (PostRetairementFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position).toString();

    }
}