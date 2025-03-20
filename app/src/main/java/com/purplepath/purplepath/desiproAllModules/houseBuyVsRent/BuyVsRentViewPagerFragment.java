package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finobot.finobot.R;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.adapter.DesiproPagerAdapter;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */
public class BuyVsRentViewPagerFragment extends BaseFragment {

    @BindView(R.id.desipro_viewpager)
    ViewPager viewPager;

    @BindView(R.id.desipro_tabs)
    TabLayout tabLayout;

    public ActivityMethodsInterface methodsInterface;

    private DesiproPagerAdapter pagerAdapter;

    private Context mContext;
    public BuyVsRentViewPagerFragment() {
    }

    BuyFragment buyFragment;//=new BuyFragment();
    RentFragment rentFragment;//=new RentFragment();

    final int BUY_INDEX=0,RENT_INDEX=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_buy_vs_rent_view_pager, container, false);
        ButterKnife.bind(this,view);

        /*if(savedInstanceState==null){
            buyFragment=new BuyFragment();
            rentFragment=new RentFragment();
        }*/

        mContext=getContext();

        try {
            methodsInterface = (ActivityMethodsInterface) mContext;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        pagerAdapter=new DesiproPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(buyFragment,"Buy");
        pagerAdapter.addFragment(rentFragment,"Rent");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buyFragment=BuyFragment.newInstance();
        rentFragment=RentFragment.newInstance();


    }

    @Override
    public void onResume() {
        super.onResume();
        methodsInterface.showFAB();
    }

    public void setErrorInBuyFragment(int index){
        viewPager.setCurrentItem(BUY_INDEX);
        buyFragment.setErrorBuyFragment(index);

    }
    public void setErrorInRentFragment(int index){
        viewPager.setCurrentItem(RENT_INDEX);
        rentFragment.setErrorRentFragment(index);

    }
}
