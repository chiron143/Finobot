package com.purplepath.purplepath.desiproAllModules.loanComparison;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by pravinr on 11/7/17.
 */

public class LoanComparisonSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;

    @Bind(R.id.recomendationTab)
    TabLayout recomendationTab;


    ViewPager recomendationViewPager;
    DeciproAdapter viewPagerAdapter;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    ArrayList<String> titles= new ArrayList<>(Arrays.asList("Loan-1","Loan-2","Loan-3"));
    LoanComparisonModel loanComparisonModel;
    LoanRecomendationInnerFragment loan1,loan2,loan3;

    public static LoanComparisonSummary newInstance(LoanComparisonModel loanComparisonModel) {

        Bundle args = new Bundle();
        args.putSerializable("LoanComparisonModel",loanComparisonModel);
        LoanComparisonSummary fragment = new LoanComparisonSummary();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_loancomparison_summary, container, false);
        ButterKnife.bind(this,view);
        backPressedListener.setActionBarTitle("LoanComparison");
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
                if (args.containsKey("LoanComparisonModel")) {
                    loanComparisonModel = (LoanComparisonModel) args.getSerializable("LoanComparisonModel");
                    if (loanComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        loan1 = LoanRecomendationInnerFragment.newInstance(loanComparisonModel, 1);
                        loan2 = LoanRecomendationInnerFragment.newInstance(loanComparisonModel, 2);
                        loan3 = LoanRecomendationInnerFragment.newInstance(loanComparisonModel, 3);

                        fragmentList.add(loan1);
                        fragmentList.add(loan2);
                        fragmentList.add(loan3);

                        try {
                            viewPagerAdapter = new DeciproAdapter(getChildFragmentManager(), fragmentList, titles);
                            recomendationViewPager.setAdapter(viewPagerAdapter);
                            recomendationTab.setupWithViewPager(recomendationViewPager);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String bestLoan = loanComparisonModel.getData().getBest_ln_array().get(0);
                        if (bestLoan.equalsIgnoreCase("loan1")) {
                            recomendationViewPager.setCurrentItem(0);
                        } else if (bestLoan.equalsIgnoreCase("loan2")) {
                            recomendationViewPager.setCurrentItem(1);
                        } else if (bestLoan.equalsIgnoreCase("loan3")) {
                            recomendationViewPager.setCurrentItem(2);
                        }
                    }

                }
            }

        }catch (Exception e){e.printStackTrace();}
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try{
                    addFragmenttoStack(new LoanComparisonDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:

                try{
                    //  addFragmenttoStack(new PropertyInsuranceChart());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }*/
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

    }
}
