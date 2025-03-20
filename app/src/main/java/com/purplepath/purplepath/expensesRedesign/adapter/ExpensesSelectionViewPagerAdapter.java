//package com.purplepath.purplepath.expensesRedesign.adapter;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.purplepath.purplepath.myinterface.OnCategoriesSelectedItemListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//
///**
// * Created by Bert on 18-Aug-16.
// */
//public class ExpensesSelectionViewPagerAdapter extends FragmentPagerAdapter {
//    int mNumOfTabs;
//    private String[] expensesTabTitles;
//    private OnCategoriesSelectedItemListener onCategoriesSelectedItm;
//    private OnSelectedDoneClickListener onSelectedDoneClickListener;
//    private int mPosition=0;
//    public ExpensesSelectionViewPagerAdapter(FragmentManager fm, String[] expenseTabTitles, OnCategoriesSelectedItemListener onCategoriesSelectedItem, OnSelectedDoneClickListener onSelectedDoneClickListe, int mtabPosition) {
//        super(fm);
//        expensesTabTitles = expenseTabTitles;
//        onCategoriesSelectedItm = onCategoriesSelectedItem;
//        onSelectedDoneClickListener= onSelectedDoneClickListe;
//        mPosition=mtabPosition;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        if(position==0) {
////            fragment = new ExpensesSelectionDetailsEssentialFragment(onCategoriesSelectedItm, onSelectedDoneClickListener,mPosition);
//        }else if(position==1){
////            fragment =  new ExpensesSelectionDetailsDiscretionaryFragment(onCategoriesSelectedItm, onSelectedDoneClickListener,mPosition);
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
//}