package com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.expenseEDCOC.selectiondialog.LevelSecondSelectionFragment;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 01/06/17.
 */

public class Lev2IncTabAdapterExp  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ArrayList<ExpensesLevelZeroData> tabTitles = new ArrayList<ExpensesLevelZeroData>();
    private IncLev2SelLisInterface incLev2SelLisInterface;
    HashMap<String, ExpensesLevelZeroData> incomeCatHashMap;

    ArrayList<String> selLev2List,selLev3List;
    public Lev2IncTabAdapterExp(FragmentManager fm, ArrayList<ExpensesLevelZeroData> income_cat_lev1,
                                HashMap<String, ExpensesLevelZeroData> incomeFilterHashMap, IncLev2SelLisInterface incLev2SelLisInterface,
                                ArrayList<String> selLev2List, ArrayList<String>selLev3List) {
        super(fm);
        this.tabTitles = income_cat_lev1;
        this.incomeCatHashMap=incomeFilterHashMap;
        this.incLev2SelLisInterface=incLev2SelLisInterface;
        this.selLev2List=selLev2List;
        this.selLev3List=selLev3List;
    }




    @Override
    public Fragment getItem(int position) {
        return (LevelSecondSelectionFragment.newInstance(incomeCatHashMap,tabTitles.get(position),incLev2SelLisInterface,selLev2List,selLev3List));
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position).getLev0_name();

    }
}