package com.purplepath.purplepath.insurance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.fragment.GeneralInsuranceListFragment;
import com.purplepath.purplepath.insurance.fragment.LifeInsuranceListFragment;

import java.util.ArrayList;

/**
 * Created by Bert on 27-Jun-16.
 */
public class InsuranceViewPagerAdapter extends FragmentPagerAdapter  {
    int mNumOfTabs;
    private String[] expensesTabTitles;

    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    FirstTimeDoneInterface firstTimeDoneInterface;

    public InsuranceViewPagerAdapter(FragmentManager fm, String[] expenseTabTitles,
                                     Boolean isSignUp, ArrayList<String> formArray,
                                     FirstTimeDoneInterface firstTimeDoneInterface) {
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

            fragment =  LifeInsuranceListFragment.newInstance(isSignUp,formArray,firstTimeDoneInterface);
        }else if(position==1){

            fragment =  GeneralInsuranceListFragment.newInstance(isSignUp,formArray,firstTimeDoneInterface);
        }
        return fragment;
    }

//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if(position==0) {
//            fragment =  new LifeInsuranceListFragment();
//        }else if(position==1){
//            fragment = new GeneralInsuranceListFragment();
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
