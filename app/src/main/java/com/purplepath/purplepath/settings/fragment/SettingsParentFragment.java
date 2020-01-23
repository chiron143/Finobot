package com.purplepath.purplepath.settings.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.settings.adapter.SettingsPagerAdapter;

/**
 * Created by Pratheep.S on 03-01-2017.
 */

public class SettingsParentFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SettingsPagerAdapter pagerAdapter;
    private String [] tabTitles={"Settings","Assumptions","Selections"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings_parent,container,false);
        viewPager= view.findViewById(R.id.settings_viewPager);
        tabLayout= view.findViewById(R.id.settings_tabLayout);
        pagerAdapter=new SettingsPagerAdapter(getChildFragmentManager(),tabTitles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPos,prevPos;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i("spcheck", "onPageScrolled: position"+position+"  positionOffset "+ positionOffset+"positionOffsetPixels "+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("spcheck", "onPageSelected: "+position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("spcheck", "onPageScrollStateChanged: "+state);

            }
        });*/



        return view;
    }
}
