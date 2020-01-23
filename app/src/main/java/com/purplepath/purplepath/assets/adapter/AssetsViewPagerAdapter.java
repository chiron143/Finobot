package com.purplepath.purplepath.assets.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.purplepath.purplepath.assets.PhysicalAssetsListFragment;
import com.purplepath.purplepath.assets.FinancialAssetsListFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;

import java.util.ArrayList;

/**
 * Created by Bert on 27-Jun-16.
 */
public class AssetsViewPagerAdapter extends FragmentPagerAdapter  {
    int mNumOfTabs;
    private String[] expensesTabTitles;

    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();

    FirstTimeDoneInterface firstTimeDoneInterface;

    public AssetsViewPagerAdapter(FragmentManager fm, String[] expenseTabTitles,
                                  Boolean isSignUp, ArrayList<String> formArray, FirstTimeDoneInterface firstTimeDoneInterface) {
        super(fm);
        expensesTabTitles = expenseTabTitles;
        this.isSignUp=isSignUp;
        this.formArray=formArray;
        this.firstTimeDoneInterface=firstTimeDoneInterface;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position==0) {
            fragment =  PhysicalAssetsListFragment.newInstance(isSignUp,formArray,firstTimeDoneInterface);
        }else if(position==1){
            fragment =  FinancialAssetsListFragment.newInstance(isSignUp,formArray,firstTimeDoneInterface);
        }
        return fragment;
    }


//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if(position==0) {
//            fragment =  PhysicalAssetsListFragment.newInstance(isSignUp,formArray);
//        }else if(position==1){
//            fragment = new FinancialAssetsListFragment(isSignUp,formArray);
//        }
//        return fragment;
//    }

    @Override
    public int getCount() {
        return expensesTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return expensesTabTitles[position];
    }

}
