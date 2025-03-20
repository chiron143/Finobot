package com.purplepath.purplepath.incomedetails.fragment.incomeselection.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog.LevelSecondSelectionFragment;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 29/08/16.
 */
public class Lev2IncTabAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ArrayList<Income_cat_lev1> tabTitles = new ArrayList<Income_cat_lev1>();
    private IncLev2SelLisInterface incLev2SelLisInterface;
    HashMap<String, Income_cat_lev1> incomeCatHashMap;
    String familyId;
    ArrayList<String> selLev2List,selLev3List;
    public Lev2IncTabAdapter(FragmentManager fm, ArrayList<Income_cat_lev1> income_cat_lev1,
                             HashMap<String, Income_cat_lev1> incomeFilterHashMap,
                             String familyId, IncLev2SelLisInterface incLev2SelLisInterface,
                             ArrayList<String> selLev2List, ArrayList<String>selLev3List) {
        super(fm);
        this.tabTitles = income_cat_lev1;
        this.incomeCatHashMap=incomeFilterHashMap;
        this.familyId=familyId;
        this.incLev2SelLisInterface=incLev2SelLisInterface;
        this.selLev2List=selLev2List;
        this.selLev3List=selLev3List;
    }




    @Override
    public Fragment getItem(int position) {
            return (LevelSecondSelectionFragment.newInstance(incomeCatHashMap,tabTitles.get(position),familyId,incLev2SelLisInterface,selLev2List,selLev3List));
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position).getLev1_name();

    }
}