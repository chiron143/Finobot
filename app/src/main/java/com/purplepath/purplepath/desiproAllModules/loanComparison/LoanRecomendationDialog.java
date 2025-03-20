package com.purplepath.purplepath.desiproAllModules.loanComparison;

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
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pratheep.S on 19-06-2017.
 */

 //this class change LoanRecomendationDialog to LoanComparisonSummary


public class LoanRecomendationDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.recomendationTab)
    TabLayout recomendationTab;

    /*@BindView(R.id.recomendationViewPager1)
    ViewPager recomendationViewPager;*/

    @BindView(R.id.closebtnId)
    ImageView closebtnId;

    ViewPager recomendationViewPager;
    DeciproAdapter viewPagerAdapter;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    ArrayList<String> titles= new ArrayList<>(Arrays.asList("Loan-1","Loan-2","Loan-3"));
    LoanComparisonModel loanComparisonModel;
    LoanRecomendationInnerFragment loan1,loan2,loan3;

    public static LoanRecomendationDialog newInstance(LoanComparisonModel loanComparisonModel) {
        
        Bundle args = new Bundle();
        args.putSerializable("LoanComparisonModel",loanComparisonModel);
        LoanRecomendationDialog fragment = new LoanRecomendationDialog();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_loan_recomendation,container,false);
        ButterKnife.bind(this,view);


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
            closebtnId.setOnClickListener(this);
        }catch (Exception e){e.printStackTrace();}
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
