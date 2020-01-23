package com.purplepath.purplepath.incomedetails.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.incomedetails.IncomeDynamicDetail;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 27/06/16.
 */
public class IncomeFamilyTabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    private List<String> tabTitles = new ArrayList<String>();
    private IncomeCategoryModel mCategoryModel;
    private AddFamilyDetailModel mFamilyDetailModel;
    private GetIncomeModel mIncomeModel;


    public IncomeFamilyTabAdapter(FragmentManager fm, List<String> NumOfTabs,
                                  IncomeCategoryModel mCategoryModel,
                                  AddFamilyDetailModel mFamilyDetailModel,
                                  GetIncomeModel mIncomeModel) {
        super(fm);
        this.tabTitles = NumOfTabs;
        this.mCategoryModel=mCategoryModel;
        this.mFamilyDetailModel=mFamilyDetailModel;
        this.mIncomeModel=mIncomeModel;


    }
    public void updateView(String name)
    {
        tabTitles.add(name);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
      if(mIncomeModel!=null) {
          if(mIncomeModel.getData().getUser_incomes()!=null)
//              return (IncomeUserDetailFormFrag.newInstance(position, mCategoryModel, mFamilyDetailModel, mIncomeModel));
              return  ( IncomeDynamicDetail.newInstance(position, mCategoryModel, mFamilyDetailModel, mIncomeModel));
          else
//              return (IncomeUserDetailFormFrag.newInstance(position, mCategoryModel, mFamilyDetailModel, null));
              return  ( IncomeDynamicDetail.newInstance(position, mCategoryModel, mFamilyDetailModel, null));
      }
        else
//          return (IncomeUserDetailFormFrag.newInstance(position,mCategoryModel,mFamilyDetailModel,null));
        return  ( IncomeDynamicDetail.newInstance(position,mCategoryModel,mFamilyDetailModel,null));

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