package com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 28/06/17.
 */

    //this class DepositRecomendationDialog to DepositRecommentationSummary

public class DepositRecomendationDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.recomendationTab)
    TabLayout recomendationTab;

   /* @BindView(R.id.recomendationViewPager)
    ViewPager recomendationViewPager;*/

    @BindView(R.id.closebtnId)
    ImageView closebtnId;

    ViewPager recomendationViewPager;
    DeciproAdapter viewPagerAdapter;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    ArrayList<String> titles= new ArrayList<>(Arrays.asList("Deposit-1","Deposit-2","Deposit-3"));
    DepositCompModel depositComparisonModel;
    DepositRecomendationInnerFragment deposit1,deposit2,deposit3;
    Context mContext;
    public static DepositRecomendationDialog newInstance(DepositCompModel depositCompModel) {

        Bundle args = new Bundle();
        args.putSerializable("depositCompModel",depositCompModel);
        DepositRecomendationDialog fragment = new DepositRecomendationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        int height=getResources().getDisplayMetrics().heightPixels;
        int width=getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setLayout((int)(width*.95),(int)(height*.95));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_loan_recomendation,container);

        ButterKnife.bind(this,view);
        recomendationViewPager= view.findViewById(R.id.recomendationViewPager1);
        Bundle args=getArguments();
        if(args!=null){
            if(args.containsKey("depositCompModel")){
                depositComparisonModel= (DepositCompModel) args.getSerializable("depositCompModel");
                if(depositComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    deposit1 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 1);
                    deposit2 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 2);
                    deposit3 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 3);

                    fragmentList.add(deposit1);
                    fragmentList.add(deposit2);
                    fragmentList.add(deposit3);

                    viewPagerAdapter = new DeciproAdapter(getChildFragmentManager(), fragmentList, titles);
                    recomendationViewPager.setAdapter(viewPagerAdapter);
                    recomendationTab.setupWithViewPager(recomendationViewPager);
                    String bestLoan=depositComparisonModel.getData().getBest_dep_array().get(0);
                    if(bestLoan.equalsIgnoreCase("dep1")){
                        recomendationViewPager.setCurrentItem(0);
                    }else if(bestLoan.equalsIgnoreCase("dep2")){
                        recomendationViewPager.setCurrentItem(1);
                    }else if(bestLoan.equalsIgnoreCase("dep3")){
                        recomendationViewPager.setCurrentItem(2);
                    }
                }

            }
        }


        closebtnId.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closebtnId:
                getDialog().dismiss();
                break;
        }
    }
}