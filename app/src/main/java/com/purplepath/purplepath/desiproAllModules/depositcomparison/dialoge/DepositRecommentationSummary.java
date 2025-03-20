package com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by pravinr on 12/4/17.
 */

public class DepositRecommentationSummary extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.recomendationTab)
    TabLayout recomendationTab;
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    ViewPager recomendationViewPager;
    DeciproAdapter viewPagerAdapter;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>(Arrays.asList("Deposit-1", "Deposit-2", "Deposit-3"));
    DepositCompModel depositComparisonModel;
    DepositRecomendationInnerFragment deposit1, deposit2, deposit3;
    Context mContext;

    public static DepositRecommentationSummary newInstance(DepositCompModel depositCompModel) {

        Bundle args = new Bundle();
        args.putSerializable("depositCompModel", depositCompModel);
        DepositRecommentationSummary fragment = new DepositRecommentationSummary();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedListener= (OnActivityBackPressedListener) getContext();

        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depositcomparison_summary, container,false);

        ButterKnife.bind(this, view);
        backPressedListener.setActionBarTitle("Deposit Comparison");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
        recomendationViewPager = view.findViewById(R.id.recomendationViewPager1);
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("depositCompModel")) {
                depositComparisonModel = (DepositCompModel) args.getSerializable("depositCompModel");
                if (depositComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    deposit1 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 1);
                    deposit2 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 2);
                    deposit3 = DepositRecomendationInnerFragment.newInstance(depositComparisonModel, 3);

                    fragmentList.add(deposit1);
                    fragmentList.add(deposit2);
                    fragmentList.add(deposit3);

                    viewPagerAdapter = new DeciproAdapter(getChildFragmentManager(), fragmentList, titles);
                    recomendationViewPager.setAdapter(viewPagerAdapter);
                    recomendationTab.setupWithViewPager(recomendationViewPager);

                    String bestLoan = depositComparisonModel.getData().getBest_dep_array().get(0);
                    if (bestLoan.equalsIgnoreCase("dep1")) {
                        recomendationViewPager.setCurrentItem(0);
                    } else if (bestLoan.equalsIgnoreCase("dep2")) {
                        recomendationViewPager.setCurrentItem(1);
                    } else if (bestLoan.equalsIgnoreCase("dep3")) {
                        recomendationViewPager.setCurrentItem(2);
                    }
                }

            }
        }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}