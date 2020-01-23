package com.purplepath.purplepath.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.purplepath.purplepath.assetAnalysisNewPieChart.AssetAnalysisPieSummary;
import com.purplepath.purplepath.cashmanaganalysis.CashmanagementInOutFragment;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.expensesanalysis.ExpanseanaysisMainPageFragment;
import com.purplepath.purplepath.incomechartdetail.IncomeanaysisMainPageFragment;
import com.purplepath.purplepath.liabilitiesanaysis.LiabilitiesMainPageFragment;
import com.purplepath.purplepath.networthanalysis.NetworthSummaryFragment;

/**
 * Created by Pratheep.S on 25-01-2017.
 */

public class FinancialSituationHomePageFragment extends BaseFragment implements View.OnClickListener {

    private CustomTextView mHeading;
    private ImageView mIncomeDetails, mExpenseDetails, mLiabilities, mAssets,mNetworth,mCashMgmt;
    private RelativeLayout relativeLayout;
    final DisplayMetrics dm= new DisplayMetrics();
    private LinearLayout linearLayout;
    private int height,width;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_financial_sit, container, false);
        mHeading = view.findViewById(R.id.tv_heading);
        mHeading.setBold();

        mIncomeDetails = view.findViewById(R.id.iv_income_details);
        mExpenseDetails = view.findViewById(R.id.iv_expenseDetails);
        mLiabilities = view.findViewById(R.id.iv_liabilities);
        mAssets = view.findViewById(R.id.iv_assets);
        mNetworth= view.findViewById(R.id.iv_netWorth);
        mCashMgmt= view.findViewById(R.id.iv_cashManagement);

        linearLayout= view.findViewById(R.id.iv_center_image);

        relativeLayout = view.findViewById(R.id.parent_RelativeLayout);

        Display display=getActivity().getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);

        height= dm.heightPixels;
        width= dm.widthPixels;
        Log.i("spcheck", "height: "+dm.heightPixels+" width: "+dm.widthPixels);

        /*LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
        mCashMgmt.setLayoutParams(layoutParams);
*/
        linearLayout.getLayoutParams().width=(int)(width*.25);
      //  linearLayout.getLayoutParams().height=(int)(width*.25);
        linearLayout.requestLayout();


      /*  mCashMgmt.getLayoutParams().width=(int)(width*.25);
        mCashMgmt.getLayoutParams().height=(int)((width*.25));
        mCashMgmt.requestLayout();

        mNetworth.getLayoutParams().width=(int)(width*.25);
        mNetworth.getLayoutParams().height=(int)((width*.25));
        mNetworth.requestLayout();*/
        //mNetworth.setImageResource(R.drawable.ic_net_worth_big);

        setListenerForAllImageViews(relativeLayout);


        return view;
    }

    private void setListenerForAllImageViews(ViewGroup viewGroup) {
        View view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                setListenerForAllImageViews((ViewGroup) view);
            } else if (view instanceof ImageView) {
                view.setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.iv_liabilities: {
                fragment = new LiabilitiesMainPageFragment();
                addFragmentToActivity(fragment);
               // Toast.makeText(getContext(), "liabilities", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.iv_income_details:
                fragment = new IncomeanaysisMainPageFragment();
                addFragmentToActivity(fragment);
                break;

            case R.id.iv_expenseDetails:
                fragment = new ExpanseanaysisMainPageFragment();
                addFragmentToActivity(fragment);
                break;

            case R.id.iv_assets:
                fragment = new AssetAnalysisPieSummary();
                addFragmentToActivity(fragment);
                break;
            case R.id.iv_netWorth:
              //  fragment = new BarChartActivitySinus();
                fragment=new NetworthSummaryFragment();
                addFragmentToActivity(fragment);
                break;
          //Dinesh code
            /*case R.id.iv_cashManagement:
                fragment = new CashManagemntAnalysis();
                addFragmentToActivity(fragment);
                break;*/
            //Muruga code
                case R.id.iv_cashManagement:
                fragment = new CashmanagementInOutFragment();
                addFragmentToActivity(fragment);
                break;
        }
    }

    public void addFragmentToActivity(Fragment fragment) {
  addFragmenttoStack(fragment);
    }
}
