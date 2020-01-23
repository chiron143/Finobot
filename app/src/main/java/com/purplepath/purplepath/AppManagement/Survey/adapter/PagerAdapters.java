package com.purplepath.purplepath.AppManagement.Survey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.purplepath.purplepath.AppManagement.Survey.SurveyFragmentView;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveymodel;
import com.purplepath.purplepath.AppManagement.Survey.nextpageinterpage.NextInterface;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muruga on 6/14/17.
 */

public class PagerAdapters extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private List<String> tabTitles = new ArrayList<String>();
    private Surveymodel mQuizmodel;

    private  NextInterface nextInterface;

    public PagerAdapters(FragmentManager childFragmentManager, Surveymodel quizmodel, NextInterface nextInterface) {
        super(childFragmentManager);
        this.mQuizmodel = quizmodel;
        this.nextInterface=nextInterface;

    }

    public void updateView(ArrayList<String> name)
    {
        tabTitles=name;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        if(mQuizmodel!=null) {
            if(mQuizmodel.getData().getQuiz().size()>position)
                return (SurveyFragmentView.newInstance(position, mQuizmodel.getData().getQuiz().get(position),nextInterface));
            else
                return (SurveyFragmentView.newInstance(position,null,nextInterface));
        }
        else
            return (SurveyFragmentView.newInstance(position,null,nextInterface));
    }

    @Override
    public int getCount() {

        return mQuizmodel.getData().getQuiz().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position).toString();

    }
}