package com.purplepath.purplepath.instuctionScreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.instuctionScreen.adapter.InstructionScreenAdapter;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Suresh on 18/09/17.
 */

public class InstructionScreenOne extends BaseFragment {


    private Context mContext;

    private InstructionScreenAdapter instructionScreenAdapter;

    private ViewPager mViewPager;

    public ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    private PageIndicator mIndicator= null;


    private OnActivityBackPressedListener mCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewone =  inflater.inflate(R.layout.instruction_screen_one, container, false);
        ButterKnife.bind(this,viewone);


        return viewone;
    }

    @Override
    public void onViewCreated(View viewone, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(viewone, savedInstanceState);

//        mCallBackListener.setActionBarTitle("Instructions");
        mViewPager = viewone.findViewById(R.id.instruction_ViewPager);
        mIndicator = viewone.findViewById(R.id.instruction_indicator);
        mCallBackListener.setActionBarTitle("How It Works");
        mFragments.add(new InstructionScreenTwo());
        mFragments.add(new InstructionScreenThree());
        mFragments.add(new InstructionScreenFour());
        mFragments.add(new InstructionScreenFive());
        mFragments.add(new InstructionScreenSix());

        instructionScreenAdapter = new InstructionScreenAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(instructionScreenAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position== 0){
                    mCallBackListener.setActionBarTitle("How It Works");
                }else if(position== 1){
                    mCallBackListener.setActionBarTitle("About yourself");
                }else if(position== 2){
                    mCallBackListener.setActionBarTitle("About your time and effort");
                }else if(position== 3){
                    mCallBackListener.setActionBarTitle("About your privacy");
                }else if(position== 4){
                    mCallBackListener.setActionBarTitle("About your security");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicator.setViewPager(mViewPager);
    }
}
