package com.purplepath.purplepath.AppManagement.Quiz.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentView;
import com.purplepath.purplepath.AppManagement.Quiz.model.Quizmodel;
import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muruga on 6/14/17.
 */

public class PagerAdapters extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private List<String> tabTitles = new ArrayList<String>();
    private Quizmodel mQuizmodel;

    private  NextInterface nextInterface;

    private DatePickerCallBackInterface mDatePickerCallBackInterface;

    private int update_postion,size_length;

    public PagerAdapters(FragmentManager childFragmentManager, Quizmodel quizmodel, NextInterface nextInterface,
                         DatePickerCallBackInterface mDatePickerCallBackInterface, int current_position, int size_length) {
        super(childFragmentManager);
        this.mQuizmodel = quizmodel;
        this.nextInterface=nextInterface;
        this.mDatePickerCallBackInterface = mDatePickerCallBackInterface;
        this.update_postion = current_position;
        this.size_length = size_length;
    }

    public void updateView(ArrayList<String> name)
    {
        tabTitles=name;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        if(mQuizmodel!=null) {
            if (mQuizmodel.getData().getQuiz().size() > position) {
                Log.d("hi", "mQuizmodel.getData().getQuiz().size() in pageradapter" + mQuizmodel.getData().getQuiz().size());
                return (QuizFragmentView.newInstance(mQuizmodel.getData().getQuiz().size(),
                        mQuizmodel.getData().getQuiz().get(position), nextInterface, mDatePickerCallBackInterface,update_postion,size_length));
            } else {
                return (QuizFragmentView.newInstance(position, null, nextInterface, mDatePickerCallBackInterface,update_postion,size_length));
            }
        }
        else{
                return (QuizFragmentView.newInstance(position, null, nextInterface, mDatePickerCallBackInterface,update_postion,size_length));
            }
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