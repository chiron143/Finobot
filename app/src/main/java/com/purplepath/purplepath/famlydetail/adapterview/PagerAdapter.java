package com.purplepath.purplepath.famlydetail.adapterview;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.famlydetail.addTabinterface.OnAddTabChange;
import com.purplepath.purplepath.famlydetail.model.Family_details;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private List<String> tabTitles = new ArrayList<String>();
    private ArrayList<Family_details> mFamilyDetailModel;
    private  OnAddTabChange onAddTabChange;
    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();


    public PagerAdapter(FragmentManager fm, List<String> NumOfTabs, ArrayList<Family_details> mFamilyDetailModel,
                        OnAddTabChange onAddTabChange, Boolean isSignUp, ArrayList<String> formArray) {
        super(fm);
        this.tabTitles = NumOfTabs;
        this.mFamilyDetailModel=mFamilyDetailModel;
        this.onAddTabChange=onAddTabChange;
        this.isSignUp=isSignUp;
        this.formArray=formArray;
    }



    public void updateView(ArrayList<String> name)
    {
        tabTitles=name;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        if(mFamilyDetailModel!=null) {
            if(mFamilyDetailModel.size()>position)
            return (FamilyTabFragmentView.newInstance(position, mFamilyDetailModel.get(position),onAddTabChange,isSignUp,formArray));
        else
                return (FamilyTabFragmentView.newInstance(position,null,onAddTabChange,isSignUp,formArray));
        }
        else
            return (FamilyTabFragmentView.newInstance(position, null,onAddTabChange,isSignUp,formArray));
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