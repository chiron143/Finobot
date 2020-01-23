package com.finobot.finobot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.settings.Interfaces.UpdateOnNavigation;
import com.purplepath.purplepath.settings.adapter.SettingsPagerAdapter;

public class SettingsActivity extends BaseFragment implements View.OnClickListener{
    OnActivityBackPressedListener backPressedListener;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SettingsPagerAdapter pagerAdapter;
    private String [] tabTitles={"Selections","Assumptions","Settings"};
    private  ActionBar actionBar;
    int currentPosition=0;
    private Context mContext;
    UpdateOnNavigation fragmentToUpdate;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_settings, container, false);

        mContext=getContext();
        backPressedListener=(OnActivityBackPressedListener)mContext;
        backPressedListener.setActionBarTitle("Settings");

        viewPager= (ViewPager) view.findViewById(R.id.settings_viewPager);
        tabLayout=(TabLayout) view.findViewById(R.id.settings_tabLayout);
        pagerAdapter=new SettingsPagerAdapter(getChildFragmentManager(),tabTitles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout)view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int newPosition) {
                fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
                fragmentToUpdate.updateAllFields();
                currentPosition=newPosition;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

       return view;
    }

    /*@Override
    public void onBackPressed() {
        Log.i("spcheck", "onBackPressed: from activity");
        fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
        fragmentToUpdate.updateAllFields();
        super.onBackPressed();
    }*/

    @Override
    public void onPause() {
        super.onPause();
        Log.i("spcheck", "onPause: ");
        fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
        fragmentToUpdate.updateAllFields();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("spcheck", "onDestroyView ");
        fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
        fragmentToUpdate.updateAllFields();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow:
            {
                Log.i("spcheck", "relative_left_arrow");
                fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
                fragmentToUpdate.updateAllFields();
                backPressedListener.onActivityBackPressed();

            }
            break;
            case R.id.relative_center_home:
            {
                Log.i("spcheck", "relative_center_home");
                fragmentToUpdate= (UpdateOnNavigation) pagerAdapter.getItem(currentPosition);
                fragmentToUpdate.updateAllFields();

                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;
            case R.id.relative_right_arrow:
            {

            }
            break;

        }
    }


}
