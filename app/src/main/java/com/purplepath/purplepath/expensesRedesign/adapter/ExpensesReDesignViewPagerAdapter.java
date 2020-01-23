//package com.purplepath.purplepath.expensesRedesign.adapter;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.purplepath.purplepath.expensesRedesign.ExpensesReDesignDetailsDiscretionaryFragment;
//import com.purplepath.purplepath.expensesRedesign.ExpensesReDesignDetailsEssentialFragment;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//
///**
// * Created by Bert on 27-Jun-16.
// */
//public class ExpensesReDesignViewPagerAdapter extends FragmentPagerAdapter  {
//    int mNumOfTabs;
//    private String[] expensesTabTitles;
//    private OnSelectedDoneClickListener onSelectedDoneClickListener;
//    public ExpensesReDesignViewPagerAdapter(FragmentManager fm, String[] expenseTabTitles, OnSelectedDoneClickListener onSelectedDoneClickListe) {
//        super(fm);
//        expensesTabTitles = expenseTabTitles;
//        onSelectedDoneClickListener= onSelectedDoneClickListe;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if(position==0) {
//            fragment = new ExpensesReDesignDetailsEssentialFragment(onSelectedDoneClickListener);
//        }else if(position==1){
//            fragment =  new ExpensesReDesignDetailsDiscretionaryFragment(onSelectedDoneClickListener);
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
