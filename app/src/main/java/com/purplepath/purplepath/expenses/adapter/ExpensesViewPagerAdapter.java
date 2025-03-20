//package com.purplepath.purplepath.expenses.adapter;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.purplepath.purplepath.expenses.ExpensesDetailsDiscretionaryFragment;
//import com.purplepath.purplepath.expenses.ExpensesDetailsEssentialFragment;
//
///**
// * Created by Bert on 27-Jun-16.
// */
//public class ExpensesViewPagerAdapter  extends FragmentPagerAdapter  {
//    int mNumOfTabs;
//    private String[] expensesTabTitles;
//
//    public ExpensesViewPagerAdapter(FragmentManager fm, String[] expenseTabTitles) {
//        super(fm);
//        expensesTabTitles = expenseTabTitles;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if(position==0) {
//            fragment = new ExpensesDetailsEssentialFragment();
//        }else if(position==1){
//            fragment =  new ExpensesDetailsDiscretionaryFragment();
//        }
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return expensesTabTitles.length;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        // Generate title based on item position
//        return expensesTabTitles[position];
//
//    }
//
//}
