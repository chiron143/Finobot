package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxprompt.TaxPromptShowDetailView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *@author Pratheep.S
 */

public class DetailsBuyVsRentFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.detailsTabLayout)
    TabLayout detailsTabLayout;

    @Bind(R.id.detailsViewPager)
    ViewPager detailsViewPager;

    BuyVsRentModel buyVsRentModel;
    Bundle args;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    //private OnActivityBackPressedListener mCallBackListener;

    private DeciproAdapter adapter;
    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private ArrayList<String> titles = new ArrayList<>(Arrays.asList("Renting", "Buy using cash", "Buy using Finance"));
    private Context mContext;
    public DetailsBuyVsRentFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
     //   mCallBackListener= (OnActivityBackPressedListener) mContext;
    }
    public static DetailsBuyVsRentFragment newInstance(BuyVsRentModel buyVsRentModel) {

        Bundle args = new Bundle();
        args.putSerializable("buyVsRentModel",buyVsRentModel);
        DetailsBuyVsRentFragment fragment = new DetailsBuyVsRentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details_car_buy_vs_rent, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        mContext=getContext();

        mleftRelativeLayout = view. findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view. findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view. findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        args=getArguments();
        if(null!=args){
            if(args.containsKey("buyVsRentModel")){
                buyVsRentModel= (BuyVsRentModel) args.getSerializable("buyVsRentModel");
                if (fragmentsList.size()>0) fragmentsList.clear();
                fragmentsList.add(InnerTableFragment1.newInstance(buyVsRentModel,1));
                fragmentsList.add(InnerTableFragment1.newInstance(buyVsRentModel,2));
                fragmentsList.add(InnerTableFragment1.newInstance(buyVsRentModel,3));
                //setValuesFromModel();
                adapter = new DeciproAdapter(getChildFragmentManager(),fragmentsList, titles);
                detailsViewPager.setAdapter(adapter);
                detailsTabLayout.setupWithViewPager(detailsViewPager);
            }
        }



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.networth_summary_menu,menu);
        //menu.findItem(R.id.menu_details).setVisible(false);
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //case R.id.menu_graph:
             case R.id.menu_chart:
                GraphBuyVsRentFragment fragment=GraphBuyVsRentFragment.newInstance(buyVsRentModel,1);
                 //addFragment(fragment);
                 addFragmenttoStack(fragment);
                break;

            case R.id.menu_summary:
               // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                //addFragmentToActivity(ResultBuyVsRentFragment.newInstance(buyVsRentModel));
                addFragmenttoStack(ResultBuyVsRentFragment.newInstance(buyVsRentModel));
                break;
/*
            case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;*/

        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment(GraphBuyVsRentFragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        //ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }


    private void addFragmentToActivity(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
            //    mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}
